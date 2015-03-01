package amanuensis;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**class HighScoresMenu
 *
 * Panel for high scores menu in main menu, lists high scores contained in the scorelist.txt
 * @author Hexahedron
 */

public class HighScoresMenu extends JPanel implements Pressable
{
        JPanel container, scores, scoreNumbers;
        
        Amanuensis      amanuensis;
	// LetterBags
	LetterBag	backLabel, title;
        //Listeners
	MyKeyListener   key;
        //Scanner
        Scanner scan;
        
	// Constructor
        public HighScoresMenu(Amanuensis amanuensis) throws FileNotFoundException, FontFormatException, IOException 
        {
            setLayout(new BorderLayout());
            
            // Init Variables
            scan = new Scanner(new File("res/scorelist.txt"));
            this.amanuensis = amanuensis;
            
            //Panels
            title           = new LetterBag("High Scores", false);
            backLabel       = new LetterBag("Back", true);
            container       = new JPanel();
            scores          = new JPanel();
            scoreNumbers    = new JPanel();
            
            //...
            scores.setLayout(new GridLayout(10,1));
            scoreNumbers.setLayout(new GridLayout(10,1));
            container.setLayout(new GridLayout(1,2));
            
            //**Adding the scores
            for(int i = 0; i < 10; i++)
            {
                String s = ""+(i+1)+". ";
                JLabel l = new JLabel(s);
                l.setForeground(Color.black);
                scoreNumbers.add(l);
                JLabel l2 = new JLabel(scan.nextLine());
                l2.setForeground(Color.black);
                scores.add(l2);
            }
            //**
            
            title.setButton();
            
            //Set Opaque
            scoreNumbers.setOpaque(false);
            scores.setOpaque(false);
            container.setOpaque(false);
            
            //Add
            container.add( scoreNumbers);
            container.add( scores);
            add( title, "North");
            add( container, "Center");
            add( backLabel, "South");
            
            // Set panel properties
            setVisible(false);
            setFocusable(true);
            setBackground( new Color(102,0,0) );
        }

        @Override
        public void update(Integer n)
        {
            backLabel.update(n);

            if(backLabel.allCorrect())
            {
                try {
                    amanuensis.mainMenu = new MainMenu(amanuensis);
                    amanuensis.add(amanuensis.mainMenu);
                    amanuensis.mainMenu.setVisible(true);
                    setFocusable(false);
                    setVisible(false);
                    amanuensis.highScoreChosen = false;
                    amanuensis.mainChosen = true;
                    backLabel.reset();
                } catch (        FontFormatException | IOException ex) {
                    Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
}