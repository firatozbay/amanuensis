/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** class Wall
 *  
 * Building Wall, has high hit points and regenation rate and acts as a blockade
 * 
 * @author Hexahedron
 */
public class Wall extends Building{
    
    final static int HIT_P = 6000;
    final static int IDLE_START_COUNT = 500;
    final static int HEALING_MAX_INTERVAL = 200;
    
    private int idleCount;
    private int idleStartCurrent;
    private int healingInterval;
    private int healingCounter;
         static BufferedImage imgU;
     static BufferedImage imgA;
     static ImageLoader loader;
     static ImageModificator mod;

    
    public Wall(Lane lane, boolean isHost, int level) {
        super(lane, isHost, level);
        canAttack = false;
        hP = HIT_P;
        idleStartCurrent = (int)(IDLE_START_COUNT/lane.getSpM());
        idleCount = idleStartCurrent;
        healingInterval = (int)(HEALING_MAX_INTERVAL/lane.getSpM());
        healingCounter = healingInterval;
        mod = new ImageModificator();
        loader = new ImageLoader("res/WallRED.png");
        imgU = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
        loader = new ImageLoader("res/WallBLUE.png");
        imgA = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
    }
    public Wall(FieldModel fM, boolean isHost, int level)
    {
        super( fM, isHost, level);
        canAttack = false;
        hP = HIT_P;
        idleStartCurrent = (int)(IDLE_START_COUNT*lane.getSpM());
        idleCount = idleStartCurrent; 
        healingInterval = (int)(HEALING_MAX_INTERVAL*lane.getSpM());
        healingCounter = healingInterval;
        mod = new ImageModificator();
        loader = new ImageLoader("res/WallRED.png");
        imgU = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
        loader = new ImageLoader("res/WallBLUE.png");
        imgA = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
    }
    @Override
    public void damagedBy( Unit unit)
    {
        idleCount = 0;
        hP = hP - unit.getDamage();       
        if( hP <= 0)
        {
            setState( 2);
            lane.removeUnit( this) ;
            if( botLane != null)
                botLane.removeUnit( this);
            if( topLane != null)
               topLane.removeUnit( this);
        }
    }
    
    @Override
    public void act()
    {
        idleCount ++;
        if( idleCount >= idleStartCurrent
                && healingCounter >= healingInterval
                && hP < HIT_P)
        {
            hP = hP + hP/20;
            idleCount = idleStartCurrent;
            healingCounter = 0;
        }
        healingCounter++;
    }
        @Override
    public void draw(Graphics g) {
        g.setColor(new Color( 200, 200, 200));
        if( isHost)
            g.drawImage( imgU, ((int)(laneLoc*size_modifier - imgU.getWidth()/2) ),(int) (y*lane.getYMod() - imgU.getHeight()/2), null);
        else
            g.drawImage( imgA, ((int)(laneLoc*size_modifier - imgA.getWidth()/2) ),(int) (y*lane.getYMod() - imgA.getHeight()/2), null);
     if( !isHost)
            {
                int levelC = level;
                if( 30*levelC > 240)
                    levelC = 6;
                g.setColor( new Color( 255, 30*levelC, 0));
            }
            else
            {
                int levelC = level;
                if( 30*levelC > 240)
                    levelC = 6;
                g.setColor( new Color( 30*level, 0 , 255));
            }
            
            g.fillOval((int)((laneLoc -30)*size_modifier),(int) (((y*lane.getYMod())+ size*size_modifier) +5*size_modifier ),(int)( size_modifier*60*hP/HIT_P), (int)( size_modifier*60*hP/HIT_P));
    }

}
