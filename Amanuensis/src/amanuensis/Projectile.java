/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**class Projectile
 * 
 * Projectiles for attacking 
 * @author Hexahedron
 */

public class Projectile extends Unit{
    
     final static double RANGE = 2000;
     final static double SIZE = 0;
     final static int DAMAGE = 0;
     final static int BASE_HP = 200;
     final static int HIT_B = 0;
     
     boolean canHit;
     double startingX;
     double startingY;
     double targetX;
     double targetY;
     boolean isExplosive;
     boolean butSpeedy;
     boolean isDoT;
     double speedDenom;
     
     static BufferedImage img;
     static BufferedImage img1;
     static BufferedImage img2;
     static ImageLoader loader;
     static ImageModificator mod;
     
     

    public Projectile(Lane lane, boolean isHost, int level) {
        super(lane, isHost, level);
    }
    
    public Projectile( Archer unit)
    {
       super( unit.getLane(), unit.isHost(), unit.getLevel());
        super.hP = BASE_HP;
        super.state = 0;
        super.size_modifier = lane.getMod();
        
        target = unit.getTarget();
        setLaneLoc( unit.getLaneLoc());
        y = unit.getY();
        startingX = unit.getLaneLoc();
        startingY =  y  =  unit.getY();
        targetX = unit.getTarget().getLaneLoc();
        targetY = target.getY();
        damage = unit.getDamage();
        
        isExplosive = false;
        range = 30;
        size = 100;
        speedDenom = 20;
        butSpeedy = false;
        
        mod = new ImageModificator();
        loader = new ImageLoader("res/arrow.png");
        img1 = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());

            img = mod.rotate( img , (targetX - startingX)*lane.getMod(), (targetY - startingY)*lane.getYMod());
        
    }
    
    public Projectile( Treb unit)
    {
        super( unit.getLane(), unit.isHost(), unit.getLevel());
        super.hP = BASE_HP;
        super.state = 0;
        super.size_modifier = lane.getMod();
        
        target = unit.getTarget();
        setLaneLoc( unit.getLaneLoc());
        y = unit.getY() - unit.size;
        startingX = unit.getLaneLoc();
        startingY =  y ;
        targetX = unit.getTarget().getLaneLoc();
        targetY = target.getY();
        damage = unit.getDamage();
        
        
        isExplosive = true;
        range = 30;
        size = 30;
        speedDenom = 60;
        
        mod = new ImageModificator();
        loader = new ImageLoader("res/darock.png");
        img2 = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
    }
    
    public Projectile ( Building unit, Unit target)
    {
        super( target.getLane(), unit.isHost(), unit.getLevel());
        hP = BASE_HP;
        state = 0;
        size_modifier = lane.getMod();
        this.target = target;
        lane = target.getLane();
        setLaneLoc( unit.getLaneLoc());
        y = unit.getY();
        startingX = unit.getLaneLoc();
        startingY = unit.getY();
        targetX = target.getLaneLoc();
        targetY = target.getY();
        damage = unit.getDamage();
        
        isExplosive = true;
        range = 20;
        size = 20;
        speedDenom = 20;
        butSpeedy = true;
        mod = new ImageModificator();
        loader = new ImageLoader("res/arrow.png");
        img = mod.resize(loader.getIMG(), 6*size*size_modifier/loader.getIMG().getWidth());
        if( !isHost)
            img = mod.flip(img);
            img = mod.rotate( img , (targetX - startingX)*lane.getMod(), (targetY - startingY)*lane.getYMod());
        
    }

    @Override
    public void damagedBy( Unit unit) {
        
        if( canHit)
            unit.damagedBy( this);
        hP = 0;
        if( hP <= 0)
        {
            setState( 2);
            lane.removeUnit( this) ;
        }
    }

    @Override
    public void act() {
        if ( state == 0)
        {
            move();
        }
        if( state == 1 && !isExplosive)
        {
          damagedBy(target);  
        }
        if( state == 1 && isExplosive)
        {
            lane.addUnit( new Explosion( this));
            lane.removeUnit(this);
        }
    }

    @Override
    public void draw(Graphics g) {
        if( !isHost && img != null ) 
            img = mod.flip(img);
        if( !isHost && img1 != null )
            img1 = mod.flip(img1);
        if( !isHost && img2 != null )
            img2 = mod.flip(img2);
       // if( state == 0)
        //{
          //  g.setColor(Color.red);
           // g.fillOval(((int)(laneLoc*size_modifier - size*size_modifier) ),(int) (y*lane.getYMod() - size*size_modifier), 2*(int)(size*size_modifier ), 2*(int)(size*size_modifier ));
        //}
       // if( state == 1)
       // {
        //    g.setColor(Color.red);
         //   g.fillOval(((int)(laneLoc*size_modifier - size*size_modifier) ),(int) (y*lane.getYMod() - size*size_modifier), 2*(int)(size*size_modifier ), 2*(int)(size*size_modifier ));
          //  g.setColor(Color.black);
        //}
        if( isExplosive && butSpeedy)
            g.drawImage( img, ((int)(laneLoc*size_modifier - img.getWidth()/2) ),(int) (y*lane.getYMod() - img.getHeight()/2), null);
        else if( !isExplosive && !butSpeedy )
            g.drawImage( img1, ((int)(laneLoc*size_modifier - img1.getWidth()/2) ),(int) (y*lane.getYMod() - img1.getHeight()/2), null);
        else
            g.drawImage( img2, ((int)(laneLoc*size_modifier - img2.getWidth()/2) ),(int) (y*lane.getYMod() - img2.getHeight()/2), null);
    }

    @Override
    public boolean takeTarget() {
        return true;
    }
    
    @Override
    public double move()
    {
        if( lane.targetsFor(this).contains( target) 
                && range >= Math.abs(target.getY() - y) 
                && (!isExplosive || butSpeedy ))
        {
            state = 1;
            canHit = true;
        }
        else 
            canHit = false;

        laneLoc = (targetX - startingX)*lane.getSpM()/ speedDenom + laneLoc;
        y = y + ( targetY - startingY)*lane.getSpM() /speedDenom ;
        if( range > Math.abs(laneLoc - targetX))
            state = 1;
       // if( y < lane.getUpperBound() + lane.Y_AL || y > lane.getLowerBound() + lane.Y_AL)
       //     state = 1;
        return y;
    }  
    
    public boolean isDoT()
    {
        return isDoT;
    }
    
    public boolean isExplosive()
    {
        return isExplosive;
    }
}
