/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**class Building 
 *
 * Contains Buildings properties and functions, later to be extended by other other building classes
 * @author Hexahedron
 */
public class Building extends Unit{
    
    final static int HIT_P = 3000;
    final static int HIT_B = 120;
    final static int DAMAGE = 50;
    final static int SIZE = 60;
    final static int RANGE = 600;
    final static int IDLE_START_COUNT = 5000;
    final static int HEALING_MAX_INTERVAL = 400;
    
    
    
    
    boolean isMain;
    FieldModel fM;
    Lane topLane;
    Lane botLane;
    boolean canAttack;
    Lane activeLane;
    int hitBreak;
    private int idleCount;
    private int idleStartCurrent;
    private int healingInterval;
    private int healingCounter;
    static BufferedImage img;
    

    public Building(Lane lane, boolean isHost, int level) {
        super(lane, isHost, level);
        this.isHost = isHost;
        this.level = level;
        hitBreak = HIT_B;
        damage = DAMAGE;
        this.lane = lane;
        size = SIZE;
        size_modifier = lane.getMod();
        isMain = false;
        range = RANGE;
        idleStartCurrent = (int)(IDLE_START_COUNT/lane.getSpM());
        idleCount = idleStartCurrent;
        healingInterval = (int)(HEALING_MAX_INTERVAL/lane.getSpM());
        healingCounter = healingInterval;
        
         if( isHost)
        {
            setLaneLoc( lane.getSecondaryStart());
        }
        else
        {
            setLaneLoc( lane.getSecondaryEnd());
        }
        y = lane.getY( laneLoc) + lane.Y_AL - size;
    }
    
    public Building(FieldModel fM,  boolean isHost, int level)
    {
        super( fM.getMidLane(), isHost, level);
        this.isHost = isHost;
        this.fM = fM;
        this.level = level;
        topLane = fM.getTopLane();
        botLane = fM.getBotLane();
        size = SIZE;
        size_modifier = lane.getMod();
        isMain = true;
        hitBreak = HIT_B;
        damage = DAMAGE;
        range = RANGE;
        idleStartCurrent = (int)(IDLE_START_COUNT/lane.getSpM());
        idleCount = idleStartCurrent;
        healingInterval = (int)(HEALING_MAX_INTERVAL/lane.getSpM());
        healingCounter = healingInterval;
        
        if( isHost)
        {
            setLaneLoc( fM.getMidLane().getStart());
        }
        else
        {
            setLaneLoc( fM.getMidLane().getEnd());
        }
        
        y = lane.getY( laneLoc) + lane.Y_AL - size;
        
        lane.addMainBuilding( this);
        botLane.addMainBuilding( this);
        topLane.addMainBuilding( this);       
    }
            

    @Override
    public void damagedBy(Unit unit) {
        idleCount = 0;
        hP = hP - unit.getDamage();       
        if( hP <= 0)
        {
            setState( 2);
            lane.removeUnit( this);
            if( botLane != null)
                botLane.removeUnit( this);
            if( topLane != null)
               topLane.removeUnit( this);
            if(isMain)
            {
                fM.realWinner = this.isHost;
            }
                
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color( 200, 200, 200));
        g.fillRect(((int)(laneLoc*size_modifier - size*size_modifier) ),(int) (y*lane.getYMod() - size*size_modifier), 2*(int)(size*size_modifier ), 2*(int)(size*size_modifier ));
    }

    @Override
    public void act() {
        if( canAttack)
        {
            takeTarget(); 
            if( target != null)
            {
                if( hitBreak >= HIT_B/lane.speedModifier)
                {
                    attack();
                }
                else
                    hitBreak ++;         
                if(  target.getHP() <= 0)
                {
                    target = null;
                    setState( 0);
                }
            }    
        } 
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
    public boolean takeTarget() {
        if( isMain)
        {
           ArrayList < Unit> targets = lane.targetsFor(this);
           targets.addAll( botLane.targetsFor(this));
           targets.addAll( topLane.targetsFor(this));
           
           if( targets.isEmpty())
           {
               state = 0;
               return true;  
           }
           
           Unit targetC = null;
           for( int i = 0; i < targets.size(); i++)
           {
               if( targetC == null || targetC.getHP() >= targets.get(i).getHP())
               {
                   if( targets.get(i).getClass() == Saboteur.class)
                   {
                       target = targets.get(i);
                       state = 1;
                       return true;
                   }
                   if( targets.get(i).getHP() > 0)
                   {
                     targetC = targets.get(i);
                     state = 1;
                   }
               }
           }
           target = targetC;
           return true;
        }
        else
        {
            ArrayList < Unit> targets = lane.targetsFor(this);
            if( targets.isEmpty())
            {
                state = 0;
                return true;  
            }
            Unit targetC = null;
            for( int i = 0; i < targets.size(); i++)
            {
                if( targetC == null || targetC.getHP() >= targets.get(i).getHP())
                {
                    if( targets.get(i).getHP() > 0)
                    {
                      targetC = targets.get(i);
                      state = 1;
                    }
                }
             }
       target = targetC;
       return true;     
        }
    }
    
    public void canAttack( boolean a)
    {
        canAttack = a;
    }
    
    public void attack()
    {
        target.getLane().addUnit( new Projectile( this , target));
        hitBreak = 0;
    }

}
