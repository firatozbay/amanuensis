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

/**class Explosion 
 *
 * Contains explosion properties and functions, explosive damage
 * @author Hexahedron
 */

public class Explosion extends Unit{
     final static double RANGE = 200;
     final static double SIZE = 0;
     final static int DAMAGE = 0;
     final static int BASE_HP = 200;
     final static int HIT_B = 0;
     
     boolean flameEffect;
     boolean isDoT;
     int boomCount;
     int maxGraphicBoomCount;
     int graphicBoomCount;
     int boomInterval;
     int intervalCount;
     /*
        0 alive!
        1 dead...
     */
     static BufferedImage imgA;
     static BufferedImage imgB;
     static BufferedImage img1;
     static BufferedImage img2;
     static ImageLoader loader;
     static ImageModificator mod;
     static SpriteSheet exp1;
     static SpriteSheet expA;
     
     int imageBoomCount;
     

    public Explosion(Lane lane, boolean isHost, int level) {
        super(lane, isHost, level);
    }
    
    public Explosion( Projectile unit)
    {
        super( unit.getLane(), unit.isHost(), unit.getLevel());
        super.state = 0;
        super.size_modifier = lane.getMod();       
        setLaneLoc( unit.getLaneLoc());
        y = unit.getY();
        damage = unit.getDamage();
        boomInterval = 1;
        intervalCount = boomInterval;
        imageBoomCount = 0;
        
        if( unit.isExplosive())
        {
            boomCount = 1;
            size = 100;
            range = 100;
            flameEffect = false;
            graphicBoomCount = 1;
            maxGraphicBoomCount = 1;
        }
        else
        {
            boomCount = 1;
            size = 50;
            range = 50;
            flameEffect = false;
            graphicBoomCount = 1;
            maxGraphicBoomCount = 1;
        
        }
        mod = new ImageModificator();
        loader = new ImageLoader("res/redEXP.png");
        exp1 = new SpriteSheet(loader.getIMG());
        img1 = exp1.crop(0, 0, 300, 300);
        img2 = exp1.crop(1, 0, 300, 300);
        img1 = mod.resize(img1, 6*size*size_modifier/img1.getWidth());
        img1 = mod.resize(img2, 6*size*size_modifier/img2.getWidth());
        
        loader = new ImageLoader("res/blueEXP.png");
        expA = new SpriteSheet(loader.getIMG());
        imgA = expA.crop(0, 0, 300, 300);
        imgB = expA.crop(1, 0, 300, 300);
        imgA = mod.resize(imgA, 6*size*size_modifier/imgA.getWidth());
        imgB = mod.resize(imgB, 6*size*size_modifier/imgB.getWidth());
        
        //if( isHost)
        
    }
    public Explosion ( Saboteur unit)
    {
        super( unit.getLane(), unit.isHost(), unit.getLevel());
        super.state = 0;
        super.size_modifier = lane.getMod();       
        setLaneLoc( unit.getLaneLoc());
        y = unit.getY();
        damage = unit.getDamage();
        boomInterval = (int)(2 / lane.getSpM());
        intervalCount = boomInterval;
        imageBoomCount = 0;
        
        if( !unit.isDoT())
        {
            boomCount = 1;
            size = 120;
            range = 100;
            flameEffect = false;
            graphicBoomCount = 1;
            maxGraphicBoomCount = 1;
        }
        else
        {
            boomCount = 15;
            size = 170;
            range = 150;
            flameEffect = true;  
            graphicBoomCount = 2;
            maxGraphicBoomCount = 2;
        }
                mod = new ImageModificator();
        loader = new ImageLoader("res/redEXP.png");
        exp1 = new SpriteSheet(loader.getIMG());
        img1 = exp1.crop(0, 0, 300, 300);
        img2 = exp1.crop(1, 0, 300, 300);
        img1 = mod.resize(img1, 6*size*size_modifier/img1.getWidth());
        img1 = mod.resize(img2, 6*size*size_modifier/img2.getWidth());
        
        loader = new ImageLoader("res/blueEXP.png");
        expA = new SpriteSheet(loader.getIMG());
        imgA = expA.crop(0, 0, 300, 300);
        imgB = expA.crop(1, 0, 300, 300);
        imgA = mod.resize(imgA, 6*size*size_modifier/imgA.getWidth());
        imgB = mod.resize(imgB, 6*size*size_modifier/imgB.getWidth());
        
    }

    @Override
    public void damagedBy(Unit unit) {
    }

    @Override
    public void act() {
        if( state == 1 )
        {
          lane.removeUnit( this);  
        }
        if ( state == 0 
                && boomInterval == intervalCount)
        {
            ArrayList< Unit> targets = lane.targetsFor(this);
            for( int i = 0; i < targets.size(); i++)
            {
                targets.get(i).damagedBy(this);
            }
            boomCount --;
            intervalCount = 0;
            if( boomCount <= 0)
            {
                state = 1;
            }
        }
        intervalCount ++;
    }

    @Override
    public void draw(Graphics g) {
        Color eColor = new Color( 123, 132, 212); 
        
        if( graphicBoomCount >=  maxGraphicBoomCount )
        {
            if( isHost)
            {
                if( imageBoomCount % 2 == 0)
                  g.drawImage( img1, ((int)(laneLoc*size_modifier - img1.getWidth()/2) ),(int) (y*lane.getYMod() - img1.getHeight()/2), null);
                else
                  g.drawImage( img2, ((int)(laneLoc*size_modifier - img2.getWidth()/2) ),(int) (y*lane.getYMod() - img2.getHeight()/2), null);  
            }
            else
            {
                if( imageBoomCount % 2 == 0)
                  g.drawImage( imgA, ((int)(laneLoc*size_modifier - imgA.getWidth()/2) ),(int) (y*lane.getYMod() - imgA.getHeight()/2), null);
                else
                  g.drawImage( imgB, ((int)(laneLoc*size_modifier - imgB.getWidth()/2) ),(int) (y*lane.getYMod() - imgB.getHeight()/2), null);  
            }
            g.setColor(eColor);
            //g.fillOval(((int)(laneLoc*size_modifier - size*size_modifier) ),(int) (y*lane.getYMod() - size*size_modifier), 2*(int)(size*size_modifier ), 2*(int)(size*size_modifier ));
            g.setColor(Color.black);
            graphicBoomCount = 0;
            imageBoomCount ++;
        }
        graphicBoomCount ++;
        
        
    }

    @Override
    public boolean takeTarget() {
        return true;
    }
    
    public boolean flameEffect()
    {
        return flameEffect;
    }
}
