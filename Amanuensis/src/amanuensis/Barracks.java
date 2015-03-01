/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**class Barracks 
 *
 * Contains Barracks buildings properties and functions, where we create swordsman
 * @author Hexahedron
 */
public class Barracks extends Building{
    
       final static int HIT_P = 3000;
            static BufferedImage imgU;
     static BufferedImage imgA;
     static ImageLoader loader;
     static ImageModificator mod;


    public Barracks(Lane lane, boolean isHost, int level) {
        super(lane, isHost, level);
        canAttack = false;
        hP = HIT_P;
                mod = new ImageModificator();
        loader = new ImageLoader("res/swrdRED.png");
        imgU = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
        loader = new ImageLoader("res/swrdBLUE.png");
        imgA = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
    }
    public Barracks(FieldModel fM, boolean isHost, int level)
    {
        super( fM, isHost, level);
         canAttack = false;
        hP = HIT_P;
                        mod = new ImageModificator();
        loader = new ImageLoader("res/swrdRED.png");
        imgU = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
        loader = new ImageLoader("res/swrdBLUE.png");
        imgA = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
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
