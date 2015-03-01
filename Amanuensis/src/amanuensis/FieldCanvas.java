/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**class FieldCanvas 
 *
 * Contains FieldCanvas properties, panel for the units game, which is the upper part of the user interface
 * @author Hexahedron
 */
public class FieldCanvas extends JPanel {
    
    FieldModel fM;
    Timer timer;
    
    public FieldCanvas( FieldModel fM)
    {        
        this.fM = fM;
        setPreferredSize(new Dimension(fM.screenW, fM.screenH));
        timer = new Timer( 20, new Painter());
        timer.start();
        setOpaque(false);
    }
    
    @Override
    public void paintComponent( Graphics g)
    {
        super.paintComponent(g);
//        g.setColor( Color.black);
//        g.fillRect(0, 0, fskiM.screenW, fM.screenH);
//        g.setColor( new Color( 204, 255, 50));
//        g.fillRect(0, 0, fM.screenW, fM.screenH);
        fM.midLane.draw(g);
        fM.topLane.draw(g);
        fM.botLane.draw(g);
        
    }
    
    private class Painter implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             repaint();
        }  
    }

}
