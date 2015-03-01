/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**class Archer 
 *
 * Contains Archer units properties and functions
 * @author Hexahedron
 */
public class Archer extends LivingUnit{
    
    /* 0 move
       1 attack
       2 die   
    */
    
     final static double DX = 3;
     final static double RANGE = 600;
     final static double SIZE = 35;
     final static int DAMAGE = 30;
     final static int BASE_HP = 200;
     final static int HIT_B = 30;
     
     // image stuff here
     static SpriteSheet uS; // users sprtes
     static SpriteSheet aS; // ais sprites
     static ArrayList<BufferedImage> move1;
     static ArrayList<BufferedImage> attack1;
     static ArrayList<BufferedImage> move2;
     static ArrayList<BufferedImage> attack2;     
     static BufferedImage img;
     static ImageLoader loader;
     static ImageModificator mod;
     int imgC;
     int imgM;
     // iamage stuff ends
     
     // priority ( higher is better) 
     final static int LOW_HP = 4; // if hp is lower than or equal to 50
     final static int IS_PIKEMAN = 3; // if it is pikeman
     final static int IS_SABOTEUR = 100; // never attack one wont do any good
     final static int IS_CAVALRY = 2; // Should eliminate  before the next charge
     final static int IS_TREB = 0; // cant force-cancel attacks so meaningless, and dmg wont be enough 
     final static int IS_ARCHER = 0;
     final static int IS_SWORDSMAN = 0;
     final static int IS_KEEP = 0;
     final static int IS_WALL = 0;
     final static int IS_BUILDING = 0;
     final static int IS_NEAR = 3;
     
    int priorityCount;

    public Archer(Lane lane, boolean isHost, int level) {
         super(lane, isHost, level);
         setLevel( level);
         hitBreak = HIT_B;
         super.hP = BASE_HP;
         super.range = RANGE;
         super.size = SIZE;
         super.state = 0;
         super.damage = DAMAGE + level*10;
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
        y = lane.getY( laneLoc) + lane.Y_AL - size;
        
        imgC = (int)(HIT_B*lane.speedModifier);
        imgM = imgC;
        mod = new ImageModificator();
        attack1 = new ArrayList<BufferedImage> (3);
        move1 = new ArrayList<BufferedImage> (3);
        attack2 = new ArrayList<BufferedImage> (3);
        move2 = new ArrayList<BufferedImage> (3);
        loader = new ImageLoader("res/redarcher.png");
        uS = new SpriteSheet(loader.getIMG());
        loader = new ImageLoader("res/bluearcher.png");
        //System.out.println("w :" + loader.getIMG().getWidth() + "h : " + loader.getIMG().getHeight());
        aS = new SpriteSheet(loader.getIMG());
        
        move1.add( uS.crop( 0 , 0 , 300 , 300));
        move1.add( uS.crop( 1 , 0 , 300 , 300));
        move1.add( uS.crop( 2, 0 , 300 , 300));
        attack1.add(uS.crop( 0, 1 , 300 , 300));
        attack1.add(uS.crop( 1, 1 , 300 , 300));
        attack1.add(uS.crop( 2, 1 , 300 , 300));
        
        move1.set(0, mod.resize(move1.get(0), 6*size*size_modifier/move1.get(0).getWidth()));
        move1.set(1,mod.resize(move1.get(1), 6*size*size_modifier/move1.get(1).getWidth()));
        move1.set(2,mod.resize(move1.get(2), 6*size*size_modifier/move1.get(2).getWidth()));
        attack1.set(0, mod.resize(attack1.get(0), 6*size*size_modifier/attack1.get(0).getWidth()));
        attack1.set(1, mod.resize(attack1.get(1), 6*size*size_modifier/attack1.get(1).getWidth()));
        attack1.set(2, mod.resize(attack1.get(2), 6*size*size_modifier/attack1.get(2).getWidth()));
        
        move2.add( aS.crop( 0 , 0 , 300 , 300));
        move2.add( aS.crop( 1 , 0 , 300 , 300));
        move2.add( aS.crop( 2, 0 , 300 , 300));
        attack2.add(aS.crop( 0, 1 , 300 , 300));
        attack2.add(aS.crop( 1, 1 , 300 , 300));
        attack2.add(aS.crop( 2, 1 , 300 , 300));
        
        move2.set(0, mod.resize(move2.get(0), 6*size*size_modifier/move2.get(0).getWidth()));
        move2.set(1,mod.resize(move2.get(1), 6*size*size_modifier/move2.get(1).getWidth()));
        move2.set(2,mod.resize(move2.get(2), 6*size*size_modifier/move2.get(2).getWidth()));
        attack2.set(0, mod.resize(attack2.get(0), 6*size*size_modifier/attack2.get(0).getWidth()));
        attack2.set(1, mod.resize(attack2.get(1), 6*size*size_modifier/attack2.get(1).getWidth()));
        attack2.set(2, mod.resize(attack2.get(2), 6*size*size_modifier/attack2.get(2).getWidth()));
    }

    @Override
    public void damagedBy(Unit unit) {
        
        hP = hP - unit.getDamage();
        if( hP <= 0)
        {
            setState( 2);
            lane.removeUnit( this) ;
        }   
    }

    @Override
    public void act() {
         takeTarget(); 
        if( state == 0)
            move();
        if( target != null && state == 1)
        {
            if( hitBreak >= HIT_B*lane.speedModifier)
            {
                lane.addUnit( new Projectile( this));
                hitBreak = 0;
            }
            else
                hitBreak ++;         
            if(  target.getHP() <= 0)
            {
                target = null;
                setState( 0);
            }
        }
        if( target !=null && target.getHP() <= 0)
        {
            target = null;
            setState( 0);
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage img1 =null;
        if( isHost)
        {
            if( target == null)
            {
                if( imgC <= imgM / 3)
                    img1 = move1.get(0);
                else if( imgC <= imgM*2/3)
                    img1 = move1.get(1);
                else if( imgC <= imgM)
                    img1 = move1.get(2);
                imgC ++;
                if( imgC >= imgM)
                    imgC = 0;
            }
            else 
            {
                if( hitBreak <= (int)(HIT_B*lane.speedModifier)* 2/ 6)
                    img1 = attack1.get(0);
                else if( hitBreak <= (int)(HIT_B*lane.speedModifier)* 4/ 6)
                    img1 = attack1.get(1);
                else if( hitBreak <= (int)(HIT_B*lane.speedModifier)* 7/ 6)
                    img1 = attack1.get(2);
            }
        }
        else
        {
            if( target == null)
            {
                if( imgC <= imgM / 3)
                    img1 = move2.get(0);
                else if( imgC <= imgM*2/3)
                    img1 = move2.get(1);
                else if( imgC <= imgM)
                    img1 = move2.get(2);
                imgC ++;
                if( imgC >= imgM)
                    imgC = 0;
            }
            else 
            {
                if( hitBreak <= (int)(HIT_B*lane.speedModifier)* 2/ 6)
                    img1 = attack2.get(0);
                else if( hitBreak <= (int)(HIT_B*lane.speedModifier)* 4/ 6)
                    img1 = attack2.get(1);
                else if( hitBreak <= (int)(HIT_B*lane.speedModifier)* 7/ 6)
                    img1 = attack2.get(2);
            }
        }

        
        //if( y != lane.getYofLane() && laneLoc < lane.DEFORMATION_LOC + lane.GAP)
        //    img1 = mod.rotate( img, (lane.GAP + lane.DEFORMATION_LOC - laneLoc)*size_modifier , (lane.getYofLane() - lane.getY(laneLoc))*lane.getYMod());
        //if( y != lane.getYofLane() && laneLoc > lane.TOTAL_L -  lane.DEFORMATION_LOC - lane.GAP)
        //    img1 = mod.rotate( img,(+laneLoc -lane.TOTAL_L + lane.GAP +lane.DEFORMATION_LOC)*size_modifier, ((-lane.getYofLane() + lane.getY(laneLoc))*lane.getYMod()));
            
            g.setColor(Color.black);
            g.drawImage( img1, ((int)(laneLoc*size_modifier - img1.getWidth()/2) ),(int) (y*lane.getYMod() - img1.getHeight()/2), null);
           
            g.setColor(Color.blue);
            //g.fillOval(((int)(laneLoc*size_modifier - size*size_modifier) ),(int) (y*lane.getYMod() - size*size_modifier), 2*(int)(size*size_modifier ), 2*(int)(size*size_modifier ));
            g.setColor(Color.black);
            
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
            
            g.fillOval((int)((laneLoc -20)*size_modifier),(int) (((y*lane.getYMod())+ size*size_modifier) +5*size_modifier ),(int)( size_modifier*40*hP/BASE_HP), (int)( size_modifier*40*hP/BASE_HP));
    }

   @Override
    public boolean takeTarget() {
       priorityCount = 0;
       int priority = 0;
       ArrayList < Unit> targets = lane.targetsFor(this);
       if( targets.isEmpty())
       {
           state = 0;
           return true;  
       }
       Unit targetC = null;
       for( int i = 0; i < targets.size(); i++)
       {
           priority = 0;
           if( targets.get(i).getHP() <= damage)
               priority += LOW_HP;
           if( targets.get(i).getClass() == Pikeman.class)
               priority += IS_PIKEMAN;
           if ( targets.get(i).getClass() == Saboteur.class)
               priority += IS_SABOTEUR;
           if ( targets.get(i).getClass() == Cavalry.class)
               priority += IS_CAVALRY;
           if ( targets.get(i).getClass() == Treb.class)
               priority += IS_TREB;
           if ( targets.get(i).getClass() == Swordsman.class)
               priority += IS_SWORDSMAN;
           if ( targets.get(i).getClass() == Keep.class)
               priority += IS_KEEP;
           if ( targets.get(i).getClass() == Wall.class)
               priority += IS_WALL;
           if ( targets.get(i).getClass() == Building.class)
               priority += IS_BUILDING;
           if ( targets.get(i).getClass() == Archer.class)
               priority += IS_ARCHER;
           if( targetC != null && Math.abs(targets.get(i).getLaneLoc() - laneLoc) < Math.abs(targetC.getLaneLoc() - laneLoc))
               priority = IS_NEAR;
           
           if( targetC == null || priority > priorityCount)
           {
               if( targets.get(i).getHP() > 0)
               {
                 targetC = targets.get(i);
                 state = 1;
               }
           }
           priority = 0;
       }
       target = targetC;
       return true;
    }
    
}
