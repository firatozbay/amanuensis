/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;
import java.awt.Graphics;

/** abstract class Unit
 *  
 * extended by every unit in the game
 * @author Hexahedron
 */
 
 public abstract class Unit
 {
        double x;
        double y;
 	Unit target;// targeted unit
 	Lane lane;
 	double laneLoc;// location in the lane
 	int hP;
 	double size;//radius of the unit
 	double range;// units attack range
 	boolean isRanged;
 	double dx;
 	double dy;
 	int state;// 0,1,2,3,4... for various situations
 	int damage;
 	int level;
 	double speed_modifier;// changes dx, dy
 	double size_modifier;// changes range, size, dx, dy, etc.
 	boolean isHost; // true if its players/hosts unit
 	
 	public Unit( Lane lane, boolean isHost, int level)
 	{	
            this.lane = lane;
            this.isHost = isHost;
            y = lane.getY( laneLoc) + lane.Y_AL - size;
            this.level = level;
 	}
 	
 	public void setSize( double a)
 	{
 	size = a;
 	}
 	
 	public void setLane( Lane lane)
 	{
 		this.lane = lane;
 	}
 	
 	public void setHP( int a)
 	{
 		this.hP = a;
 	}
 	
 	public void setLevel( int a)
 	{
 		level = a;
 	}
 	
 	public void setState( int a)
 	{
 		if ( a <= 4 && a >= 0)
 			state = a;
 		state = 0;
 	}
 	
 	public void setDX( double a)
 	{
 		dx = a;		
 	}
 	
 	public void setDY( double a)
 	{
 		dy = a;
 	}
 	
 	public void setRange( double a)
 	{
 		range = a;
 	}
 	
 	public void setLaneLoc( double a)
 	{
 		laneLoc = a;
 	}
        public void setTarget( Unit unitH)
        {
            target = unitH;
        }
        public void setDamage( int d)
        {
            damage = d;
        }
 	
 	// getter methods
 	public double getSize()
 	{
 		return size;
 	}
 	
 	public Lane getLane()
 	{
 		return lane;
 	}
 	
 	public int getHP()
 	{
 		return hP;
 	}
 	
 	public int getLevel()
 	{
 		return level;
 	}
 	
 	public int getState()
 	{
            return state;
 	}
 	
 	public double getDX()
 	{
 		return dx;		
 	}
 	
 	public double getDY()
 	{
 		return dy;
 	}
 	
 	public double getRange()
 	{
 		return range;
 	}
 	
 	public double getLaneLoc()
 	{
 		return laneLoc;
 	}
        public int getDamage()
        {
            return damage;
        }
        
        public boolean isHost()
        {
            return isHost;
        }
 	
 	// other utility methods
 	public abstract void damagedBy( Unit unit); 	

	public abstract void act();
	
 	
 	public abstract void draw( Graphics g);
 	/* public abstract void draw( Graphics g)
 	 *{
 	 *	with sprite sheet
 	 *}*/
 	public abstract boolean takeTarget();
        
        public double move()
        {  

               laneLoc = laneLoc + dx*lane.getCos( laneLoc);
               y = lane.getY( laneLoc) + lane.Y_AL - size;
               return y;
        }
        
        public Unit getTarget()
        {
          return target;  
        }
        
        public double getY()
        {
            return y;
        }
          
 }
