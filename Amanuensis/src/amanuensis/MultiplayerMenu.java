package amanuensis;

import javax.swing.*;
import java.awt.*;

/**class MultiplayerMenu
 * 
 * Multiplayer with network functionalities planning to be added this summer 
 * @author Hexahedron
 */


public class MultiplayerMenu extends JPanel
{
   	// Variables
	MainMenu 	mainMenu;
	
	// Labels
	JLabel		backLabel;
	
	// Constructor
    public MultiplayerMenu() 
    {
    	// Init Variables
    	mainMenu = null;
    	
    	// Set Labels
    	backLabel = new JLabel("Back"); 	
    	//...
    	add(backLabel);
    	
    	// Set panel properties
    	    	setSize( new Dimension(200,200) );
		setVisible(false);
		setFocusable(true);
		setBackground( Color.WHITE );
    }
    
    // To control main menu's focusing
    public void addMainMenu( MainMenu mainMenu )
    {
    	this.mainMenu = mainMenu;
    }
    
}