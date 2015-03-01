package amanuensis;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**class MainMenu
 *
 * Main menu panel view can go to Skirmish, high scores list, options or just quit the program
 * @author Hexahedron
 */

public class MainMenu extends JPanel implements Pressable
{
        
	// Variables
	OptionsMenu 		optionsMenu;
	HighScoresMenu  	highScoresMenu;
	//MultiplayerMenu	multiplayerMenu;
	MainPane                fM;
        JPanel                  fieldPane;
        
	// Labels
        LetterBag       logo;
	LetterBag	optionsButton;
	LetterBag       highScoresButton;
	LetterBag       skirmishButton;
	//LetterBag     tutorialLabel;
	//LetterBag     multiplayerLabel;
	LetterBag       exitButton;
	//Panel
        JPanel east;
        JPanel south;
        
        
	
        // Constructor
	public MainMenu( Amanuensis amn/*OptionsMenu optionsMenu, HighScoresMenu highScoresMenu, MultiplayerMenu multiplayerMenu, MainPane fM*/ ) throws FontFormatException, IOException
	{
            
            setLayout(new BorderLayout());
            
            // Get access on other panels
            this.optionsMenu                = new OptionsMenu(amn);
            this.highScoresMenu             = new HighScoresMenu(amn);
            //this.multiplayerMenu            = new MultiplayerMenu();
            
            //Panel
            east = new JPanel();
            east.setLayout(new GridLayout(4,1));
            east.setOpaque(false);
            
            south   = new JPanel();
            south.setLayout(new GridLayout(1,2));
            south.setOpaque(false);
            
            // Set controllable labels
            logo                        = new LetterBag( "Amanuensis", false );		
            skirmishButton		= new LetterBag( "Skirmish", true );
            //tutorialLabel		= new LetterBag( "Tutorial", true );
            //multiplayerLabel	= new LetterBag( "Multiplayer", true );
            optionsButton 		= new LetterBag( "Options" , true ); 		
            highScoresButton            = new LetterBag( "High Scores" , true ); 	
            exitButton                  = new LetterBag( "Exit" , true );	
            
            logo.setLogo();
            skirmishButton.setButton();
            optionsButton.setButton();
            highScoresButton.setButton();
            exitButton.setButton();
            // Add labels
            //east.add(tutorialLabel);
            east.add(skirmishButton);
            //east.add(multiplayerLabel);
            east.add(highScoresButton);

            south.add(optionsButton);
            south.add(exitButton);


            add(logo, "Center");
            add(east, "East");
            add(south, "South");



            // Set Panel properties
            setSize( new Dimension (amn.screenW, amn.screenH) );
            setBackground( new Color(102,0,0) );
            this.setFocusable(true);
            setVisible(true);
	}
        
        public void loading()
        {
            skirmishButton.setText("Loading...");
        }
        
        @Override
        public void update(Integer n)
        {
            logo.update(n);
            optionsButton.update(n);
            highScoresButton.update(n);
            skirmishButton.update(n);
            //tutorialLabel.update(n);
            //multiplayerLabel.update(n);
            exitButton.update(n);
            
        }
}
