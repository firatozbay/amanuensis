/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Graphics;

/**abstract class LivingUnit
 *
 * They are basically living units such as archers or swords-mans extended by them
 * @author Hexahedron
 */

public abstract class LivingUnit extends Unit{
        
     final static double DX = 1; 
     final static double RANGE = 1; 
     final static double SIZE = 1; 
     final static int DAMAGE = 1; 
     final static int BASE_HP = 1;
     final static int HIT_B = 1; 
     int hitBreak;
     

    public LivingUnit(Lane lane, boolean isHost, int level) {
         super(lane, isHost, level);
         hitBreak = HIT_B;
         super.hP = BASE_HP;
         super.range = RANGE;
         super.size = SIZE;
         super.state = 0;
         super.damage = DAMAGE;
         super.size_modifier = lane.getMod();
      
        if( isHost)
        {
             setLaneLoc( lane.getStart());
             super.dx = DX*lane.speedModifier;
        }
          
         else
        {
            setLaneLoc( lane.getEnd());
            super.dx = -DX*lane.speedModifier;
        }
        
        y = lane.getY( laneLoc);
    }

    @Override
    public abstract void damagedBy(Unit unit);

    @Override
    public abstract void act();

    @Override
    public abstract void draw(Graphics g); 

    @Override
    public abstract boolean takeTarget(); 
    
    
    
}
