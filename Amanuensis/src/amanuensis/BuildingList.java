package amanuensis;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

/**class BuildingList 
 *
 * Panel of input list of buildings, chooses which building to build
 * @author Hexahedron
 */

class BuildingList extends JPanel implements Pressable
{
	ArrayList<LetterBag> bags;
	MyKeyListener key;
        int screenW;
        int screenH;
        
	public BuildingList()
	{
		setLayout(new GridLayout(3,3,5,5));
		
		bags = new ArrayList<LetterBag>();
                
		key = new MyKeyListener(this);
                
		addLetterBag(new LetterBag("Barracks",true));
		addLetterBag(new LetterBag("Archery Range",true));
		addLetterBag(new LetterBag("Stable",true));
		addLetterBag(new LetterBag("Pikeman Academy",true));
		addLetterBag(new LetterBag("Armory",true));
		addLetterBag(new LetterBag("Siege Workshop",true));
		addLetterBag(new LetterBag("Wall",true));
		addLetterBag(new LetterBag("Keep",true));
		addLetterBag(new LetterBag("Restricted Access",true));
		
		setBackground(Color.white);
		
                addKeyListener(key);
		this.setFocusable( true);
 	
		//setSize(300,300);
		setVisible(true);
		
	}	
        
	public BuildingList(int p)
	{
		setLayout(new GridLayout(3,3,5,5));
		
		bags = new ArrayList<LetterBag>();
                bags.add(new LetterBag("Brchery",true));
                
                addLetterBag(new LetterBag("Archery",true));
                add(bags.get(0));
                
		key = new MyKeyListener(this);
                bags.get(0).requestFocus(true);
                bags.get(0).addKeyListener(key);
		setBackground(Color.white);
                
                addKeyListener(key);
		this.setFocusable( true);
 	
		setVisible(true);
                
        }
        
	public void update(Integer n)
        {
            for( int i = 0; i < bags.size(); i++)
                bags.get(i).update(n);
            selected();
        }
        
        public int selected()
        {
            for(int i = 0; i < bags.size(); i++)
                if(bags.get(i).allCorrect())
                    return i;
            return -1;
        }
        
        private void addLetterBag( LetterBag lb)
        {
            if( /* azsa hala arraylist max tan*/ true)
            {
                bags.add(lb);
                add(lb);
                bags.get(bags.size()-1).requestFocus( true);
            }
        }
      
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
        }
	
}
