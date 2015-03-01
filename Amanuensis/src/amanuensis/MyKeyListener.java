/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amanuensis;

import java.awt.event.*;
import java.util.ArrayList;

/**class MyKeyListener
 * 
 * the only key listener of the game, has anti ghosting support, gives key codes
 * @author Hexahedron
 */

public class MyKeyListener implements KeyListener {
    
    ArrayList<Integer> keysDown;
    
    Pressable p;
    
    public MyKeyListener(Pressable p)
    {
        this.p = p;
        
        keysDown = new ArrayList<Integer>();
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(!keysDown.contains(e.getKeyCode()) && e.getKeyCode() != 47)
            keysDown.add(new Integer(e.getKeyCode()));
        
        //System.out.println(keysDown.get(keysDown.size()-1));
        
        p.update(keysDown.get(keysDown.size()-1));   
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() != 47)
            keysDown.remove(new Integer(e.getKeyCode()));
        
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    
}
