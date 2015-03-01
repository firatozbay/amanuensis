/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**class Lane 
 *
 * Contains Lane properties and functions, the road people choose with arrow keys
 * @author Hexahedron
 */

public class Lane {
    
    public static final double Y_LENGHT = 550;
    public static final double LENGHT_MAIN = 2000;
    public static final double GAP = 300;
    public static final double DEFORMATION_LOC = 200;
    public static final double TOTAL_L = LENGHT_MAIN + 2*GAP;
    public static final double Y_AL = 50;
    
    public static final int FORM_0 = 0; // main lane
    public static final int FORM_1 = 1; // top lane
    public static final int FORM_2 = 2; // bottom lane
    
    ArrayList< LivingUnit> livingUnitsOfPlayer1;
    ArrayList< Projectile> projectilesOfPlayer1;
    ArrayList< LivingUnit> livingUnitsOfPlayer2;
    ArrayList< Projectile> projectilesOfPlayer2;
    ArrayList< Explosion> explosions1;
    ArrayList< Explosion> explosions2;
    
    Building p1Main;
    Building p2Main;
    Building p1Normal;
    Building p2Normal;
   
    public double yModifier;
    public double yValue; 
    double lenght;
    public double modifier;
    public double speedModifier;
    public int form;
    public double screenH;
    private int luck;
    private FieldModel fM;
    boolean isActive;
    
    public Lane( double modifier, double speedModifier, double screenH, int form, FieldModel ff)
    {      
        explosions1 = new ArrayList < Explosion> ();
        explosions2 = new ArrayList < Explosion> ();
        livingUnitsOfPlayer1 = new ArrayList < LivingUnit> ();
        livingUnitsOfPlayer2 = new ArrayList < LivingUnit> ();
        projectilesOfPlayer2 = new ArrayList < Projectile> ();
        projectilesOfPlayer1 = new ArrayList < Projectile> ();
        
        this.speedModifier = speedModifier;
        this.screenH = Y_LENGHT;
        yModifier = screenH/Y_LENGHT;
        if( form == FORM_0)
            yValue = this.screenH/2;
        if( form == FORM_1)
            yValue = this.screenH/6;
        if( form == FORM_2)
            yValue = this.screenH*5/6;          
        this.modifier = modifier;
        lenght = LENGHT_MAIN + 2*GAP;
        this.form = form;
        luck = 0;
        fM = ff;
        isActive = false;
    }
    
    
    
    public void addUnit( LivingUnit b, boolean a)
    {
        if( a)
        {
            livingUnitsOfPlayer1.add( b);
        }
        else
        {
            livingUnitsOfPlayer2.add( b);
        }
    }
    
    public void addUnit( Projectile a)
    {
        if( a.isHost())       
            projectilesOfPlayer1.add( a);     
        else
            projectilesOfPlayer2.add( a);         
    }
    
    public void addUnit( Explosion e)
    {
        if( e.isHost())       
            explosions1.add( e);     
        else
            explosions2.add( e); 
    }
    
    public void addBuilding( Building a)
    {
        if( a.isHost()) 
            p1Normal = a;    
        else
            p2Normal = a;
    }
    public void addMainBuilding( Building a)
    {
        if( a.isHost()) 
            p1Main = a;
        else
            p2Main = a; 
    }
    
    public Building getMain( boolean isHost)
    {
        if( isHost)
            return p1Main;
        else
            return p2Main;
    }
    
    public Building getNormal( boolean isHost)
    {
        if( isHost)
            return p1Normal;
        else
            return p2Normal;
    }
    
    public void process()
    {
        if( p1Main != null)
            p1Main.act();
        int max = livingUnitsOfPlayer1.size();
        if( max < livingUnitsOfPlayer2.size())
            max = livingUnitsOfPlayer2.size();       
        int nax = projectilesOfPlayer1.size();
        if( nax  < projectilesOfPlayer2.size())
            nax = projectilesOfPlayer2.size();
        if( max < nax)
            max = nax;
        int eex = explosions1.size();
        if( eex < explosions2.size())
            eex = explosions2.size();
        if( max < eex)
            max = eex;
        if ( luck == 0)
        {
            for( int i = 0; i < max; i++ )
            {
                if( !(livingUnitsOfPlayer1.size() <= i ))
                    livingUnitsOfPlayer1.get(i).act();
                if( !(livingUnitsOfPlayer2.size() <= i ))
                    livingUnitsOfPlayer2.get(i).act();
                if( !(projectilesOfPlayer2.size() <= i ))
                    projectilesOfPlayer2.get(i).act();
                if( !(projectilesOfPlayer1.size() <= i ))
                    projectilesOfPlayer1.get(i).act();
                if( !(explosions1.size() <= i ))
                    explosions1.get(i).act();
                if( !(explosions2.size() <= i ))
                    explosions2.get(i).act();   
                if( !(p1Normal == null))
                    p1Normal.act();
                if( !(p2Normal == null))
                    p2Normal.act();
            } 
            luck ++;
        }
        else
        {
            for( int i = 0; i < max; i++ )
            {
                if( !(livingUnitsOfPlayer2.size() <= i ))
                    livingUnitsOfPlayer2.get(i).act();
                if( !(livingUnitsOfPlayer1.size() <= i ))
                    livingUnitsOfPlayer1.get(i).act();
                if( !(projectilesOfPlayer1.size() <= i ))
                    projectilesOfPlayer1.get(i).act();
                if( !(projectilesOfPlayer2.size() <= i ))
                    projectilesOfPlayer2.get(i).act();
                if( !(explosions2.size() <= i ))
                    explosions2.get(i).act(); 
                if( !(explosions1.size() <= i ))
                    explosions1.get(i).act();
                if( !(p2Normal == null))
                    p2Normal.act();
                if( !(p1Normal == null))
                    p1Normal.act();
            }
            luck = 0; 
        }
    }
    
    public void draw( Graphics g)
    {
        
        if( isActive)
        {
            g.setColor(new Color( 255, 255, 204));
            g.fillRect((int)((GAP + DEFORMATION_LOC)*getMod()),(int)((getY(GAP + DEFORMATION_LOC) - 70)*getYMod()), (int)((LENGHT_MAIN - DEFORMATION_LOC*2)*getMod()), (int)(140*getYMod() ));
        }
        g.setColor( new Color( 255, 102, 102));
        g.drawLine( (int)((GAP + DEFORMATION_LOC)*getMod()), (int)((getY(GAP + DEFORMATION_LOC) + Y_AL)*getYMod()), (int)((GAP  + LENGHT_MAIN - DEFORMATION_LOC)*getMod()), (int)((getY(GAP + DEFORMATION_LOC) + Y_AL )*getYMod() ));

        if( p1Main != null && form == 0)
            p1Main.draw(g);
        if( p2Main != null && form == 0)
            p2Main.draw(g);
        if( p1Normal != null)
        {
            p1Normal.draw(g);
        }
        if( p2Normal != null)
            p2Normal.draw(g);
        for ( int a = 0; a < livingUnitsOfPlayer1.size(); a++)
        {
            livingUnitsOfPlayer1.get(a).draw(g);
        }
        
        for( int a = 0; a < livingUnitsOfPlayer2.size(); a++)
        {
            livingUnitsOfPlayer2.get(a).draw(g);
        }
                  
        for ( int a = 0; a < projectilesOfPlayer1.size(); a++)
        {
            projectilesOfPlayer1.get(a).draw(g);
        }
        
        for ( int a = 0; a < projectilesOfPlayer2.size(); a++)
        {
            projectilesOfPlayer2.get(a).draw(g);
        }
        for ( int a = 0; a < explosions2.size(); a++)
        {
            explosions2.get(a).draw(g);
        }
        for ( int a = 0; a < explosions1.size(); a++)
        {
            explosions1.get(a).draw(g);
        }

    }
    
    public double getMod()
    {
        return modifier;
    }
    
    public double getYMod()
    {
        return yModifier;
    }
    
    public double getSpM()
    {
        return speedModifier;
    }
    
    public double getLenght()
    {
        return lenght;
    }
    
    public void removeUnit( LivingUnit unit)
    {
        if( unit.isHost())
        {
            livingUnitsOfPlayer1.remove( unit);
        }
        else
            livingUnitsOfPlayer2.remove( unit);
    }
    
    public void removeUnit( Projectile p)
    {
        if( p.isHost())
        {
            projectilesOfPlayer1.remove( p);
        }
        else
            projectilesOfPlayer2.remove( p);
    }
    
    public void removeUnit( Explosion e)
    {
        if( e.isHost())
        {
            explosions1.remove( e);
        }
        else
            explosions2.remove( e);
    }
    
    public void removeUnit( Building b)
    {
        if( b == p1Main) 
        {
            p1Main = null;
            fM.gameIsOver( false);
        }
            
        if( b == p2Main)
        {
            p2Main = null; 
            fM.gameIsOver(true);
        }
        if( b == p1Normal)
        {
            p1Normal = null;
            fM.notifyTextGameAboutTheDireSituation(this);
        }
        if( b == p2Normal)
            p2Normal = null;
    }
    
    public ArrayList< Unit> targetsFor( Unit unit)
    {
        ArrayList< Unit> targets = new ArrayList< Unit>();
        if( unit.isHost())
        {
            for( int i = 0; i < livingUnitsOfPlayer2.size(); i++ )
            {
                if( unit.getRange() + livingUnitsOfPlayer2.get(i).getSize() > findDistance( unit, livingUnitsOfPlayer2.get(i)))
                {
                    targets.add( livingUnitsOfPlayer2.get(i));
                }
            }
            if( p2Main != null
                    && unit.getRange() + p2Main.getSize() > findDistance( unit, p2Main))
                targets.add( p2Main);
            if( p2Normal != null
                    && unit.getRange() + p2Normal.getSize() > findDistance( unit, p2Normal))
                targets.add( p2Normal);
        }
       else
        {
            for( int i = 0; i < livingUnitsOfPlayer1.size(); i++ )
            {
                if( unit.getRange() + livingUnitsOfPlayer1.get(i).getSize() > findDistance( unit, livingUnitsOfPlayer1.get(i)))
                {
                    targets.add( livingUnitsOfPlayer1.get(i));
                }
            }
            if( p1Main != null
                    && unit.getRange() + p1Main.getSize() > findDistance( unit, p1Main))
                targets.add( p1Main);
            if( p1Normal != null
                    && unit.getRange() + p1Normal.getSize() > findDistance( unit, p1Normal))
                targets.add( p1Normal);
        }
        return targets;
    }
    
    public int requestSize( boolean a)
    {
        if(a)
          return livingUnitsOfPlayer1.size();
        else
          return livingUnitsOfPlayer2.size();
    }
    
    public double getY( double x)
    {
        if( form == FORM_0)
            return yValue;
        if( form == FORM_1)
        {
            if( x <= DEFORMATION_LOC + GAP )
            {
                return yValue + (screenH/3)*( 1 - ( x - GAP )/DEFORMATION_LOC);
            }
            else if( x >= lenght - DEFORMATION_LOC  - GAP)
            {
                x = x -lenght + GAP + DEFORMATION_LOC;
                return yValue + (screenH/3)*( 1 - ( DEFORMATION_LOC - x)/ DEFORMATION_LOC) ;
            }
            else
            {
                return yValue;
            }
        }
        if( form == FORM_2)
        {
            if( x <= DEFORMATION_LOC + GAP)
            {
                 return yValue - (screenH/3)*( 1 - ( x - GAP )/DEFORMATION_LOC);
            }
            else if( x >= lenght - DEFORMATION_LOC - GAP)
            {
                x = x -lenght + GAP + DEFORMATION_LOC;
                return yValue - (screenH/3)*( 1 - ( DEFORMATION_LOC - x)/ DEFORMATION_LOC) ;
            }
            else
            {
                return yValue;
            }
        }
        return -100;
    }
    public ArrayList<LivingUnit> getUnits( boolean user)
    {
        if( ! user)
            return livingUnitsOfPlayer2;
       else
            return livingUnitsOfPlayer1;
    }
    
    public int targetsAround( Unit unit, double r)
    {
        int count;
        count = 0;
        if( unit.isHost())
        {
            for( int i = 0; i < livingUnitsOfPlayer1.size(); i++)
            {
                if( Math.abs(livingUnitsOfPlayer1.get(i).getLaneLoc() - unit.getLaneLoc()) <= r)
                    count ++;
            }
        }
        else
            {
            for( int i = 0; i < livingUnitsOfPlayer2.size(); i++)
            {
                if( Math.abs(livingUnitsOfPlayer2.get(i).getLaneLoc() - unit.getLaneLoc()) <= r)
                    count ++;
            }
        }
        return count;
    }
    
    public double getStart()
    {
        return GAP;
    }
    
    public double getEnd()
    {
        return lenght - GAP;
    }
    
    public double getSecondaryStart()
    {
        return GAP + DEFORMATION_LOC;
    }
    
    public double getSecondaryEnd()
    {
        return lenght - GAP - DEFORMATION_LOC;
    }
    
    @Override
    public String toString()
    {
      if( form == 0)  
         return "middle";
      if( form == 1)
          return "other";
      else 
          return " other 2";
    }
    
    public double getUpperBound()
    {
        return screenH/6;
    }
    
    public double getLowerBound()
    {
        return (screenH*5)/6;
    }
    
    public double findDistance( double x, double x1)
    {
        double xdif = Math.abs(x - x1);
        double ydif = Math.abs(getY(x) - getY(x1));
        return Math.sqrt( xdif*xdif + ydif*ydif);
    }
    
    public double findDistance( Unit u, Unit o)
    {
        return findDistance( u.getLaneLoc(), o.getLaneLoc());
    }
    
    public double getYofLane()
    {
        return yValue;
    }
    
    public double getCos( double x)
    {
       if( getY(x) != yValue)
       {
            double h = Math.abs(yValue - getY( GAP));
            double w = DEFORMATION_LOC;
            double z = Math.sqrt( h*h + w*w); 
            return w/z;
       }
       else 
           return 1;

    }
    
    public Unit getNearestFrom( double x, boolean user)
    {
        if(!user)
        {
           Unit current = null;
            for( int i = 0; i < livingUnitsOfPlayer1.size(); i++ )
            {
                if( current == null)
                {
                    current = livingUnitsOfPlayer1.get(i);
                }
                else if
                     ( findDistance( x, current.getLaneLoc()) 
                        > findDistance( x, livingUnitsOfPlayer1.get(i).getLaneLoc()))
                {
                    current = livingUnitsOfPlayer1.get(i);
                }
            }
            return current;
        }
        else
        {
           Unit current = null;
            for( int i = 0; i < livingUnitsOfPlayer2.size(); i++ )
            {
                if( current == null)
                {
                    current = livingUnitsOfPlayer2.get(i);
                }
                else if
                     ( findDistance( x, current.getLaneLoc()) 
                        > findDistance( x, livingUnitsOfPlayer2.get(i).getLaneLoc()))
                {
                    current = livingUnitsOfPlayer2.get(i);
                }
            }
            return current;
        }       
    }
    
    public Unit getNearestToMain( boolean user)
    {
        if( ! user)
        {
            return getNearestFrom( getEnd(), user);
        }
        else
        {
            return getNearestFrom( getStart(), user);
        }
    }
    
    public void demolishBuilding( boolean user)
    {
        if( ! user)
            p2Normal = null;
        else
            p1Normal = null; 
    }
    
    public void setActive( boolean a) // just for drawing
    {
        isActive = a;
    }

    
    
}

