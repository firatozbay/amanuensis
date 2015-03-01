package amanuensis;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**class OptionsMenu
 * 
 * panel for options saves text field inputs to use them later such as changing the resolution choosing the game speed or entering username
 * @author Hexahedron
 */

public class OptionsMenu extends JPanel implements Pressable
{
    
	// Variables
        Amanuensis      amanuensis;
        boolean         textSelect;
	// Labels
	LetterBag	backLabel, title;
        
        JTextField      userNameField, speedField, pixelXField, pixelYField;
        JPanel          textFieldHolder, titleHolder;
        JLabel          userInfo, speedInfo, pixelXInfo, pixelYInfo, description;
	MyKeyListener   key;
        
	// Constructor
        public OptionsMenu(Amanuensis amanuensis) throws FileNotFoundException, UnsupportedEncodingException, FontFormatException, IOException 
        {
                setLayout(new BorderLayout());
                // Init Variables
                this.amanuensis = amanuensis;
                
                textSelect              = false;
                
                // Init 
                title                   = new LetterBag("Options", false); 	
                backLabel               = new LetterBag("Back", true); 	
                textFieldHolder         = new JPanel();
                titleHolder             = new JPanel();
                userNameField           = new JTextField("John Doe");
                speedField              = new JTextField("0,8");
                pixelXField             = new JTextField("1400");
                pixelYField             = new JTextField("750");

                //Labels init
                description             = new JLabel("(You must restart the application for the changes to take effect)");
                userInfo                = new JLabel("Enter your username:");
                speedInfo               = new JLabel("Enter the game speed you desire:");
                pixelXInfo              = new JLabel("Pixels in X:");
                pixelYInfo              = new JLabel("Pixels in Y:");
                //Set Font Size
                userInfo.setFont(this.getFont().deriveFont(35.0f));
                speedInfo.setFont(this.getFont().deriveFont(35.0f));
                pixelXInfo.setFont(this.getFont().deriveFont(35.0f));
                pixelYInfo.setFont(this.getFont().deriveFont(35.0f));
                description.setFont(this.getFont().deriveFont(24.0f));
                
                userInfo.setForeground(Color.black);
                speedInfo.setForeground(Color.black);
                pixelXInfo.setForeground(Color.black);
                pixelYInfo.setForeground(Color.black);
                description.setForeground(Color.black);
                
                textFieldHolder.setLayout(new GridLayout(4,2));

                textFieldHolder.add(userInfo);
                textFieldHolder.add(userNameField);
                textFieldHolder.add(speedInfo);
                textFieldHolder.add(speedField);
                textFieldHolder.add(pixelXInfo);
                textFieldHolder.add(pixelXField);
                textFieldHolder.add(pixelYInfo);
                textFieldHolder.add(pixelYField);
                
                textFieldHolder.setOpaque(false);
                titleHolder.setOpaque(false);
                
                title.setButton();
                
                titleHolder.add(title);
                titleHolder.add(description);
                add(titleHolder, "North");
                add(textFieldHolder, "Center");
                add(backLabel, "South");
                // Set panel properties

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        textSelect = !textSelect;
                        userNameField.setFocusable(textSelect);
                        speedField.setFocusable(textSelect);
                        pixelXField.setFocusable(textSelect);
                        pixelYField.setFocusable(textSelect);
                        if(!userNameField.getText().equals("")){
                            try (PrintWriter writer = new PrintWriter("res/username.txt", "UTF-8")) {
                                writer.println(userNameField.getText());
                            } catch (            FileNotFoundException | UnsupportedEncodingException ex) {
                                Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        if(!speedField.getText().equals("")){
                            try (PrintWriter writer = new PrintWriter("res/speed.txt", "UTF-8")) {
                                writer.println(speedField.getText());
                            } catch (            FileNotFoundException | UnsupportedEncodingException ex) {
                                Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        if(!pixelXField.getText().equals("")){
                            try (PrintWriter writer = new PrintWriter("res/pixelX.txt", "UTF-8")) {
                                writer.println(pixelXField.getText());
                            } catch (            FileNotFoundException | UnsupportedEncodingException ex) {
                                Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(!pixelYField.getText().equals("")){
                            try (PrintWriter writer = new PrintWriter("res/pixelY.txt", "UTF-8")) {
                                writer.println(pixelYField.getText());
                            } catch (            FileNotFoundException | UnsupportedEncodingException ex) {
                                Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    }
                });

                setVisible(true);
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
                    amanuensis.optionChosen = false;
                    amanuensis.mainChosen = true;
                    backLabel.reset();
                } catch (        FontFormatException | IOException ex) {
                    Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            if(n == enter)
//            {
//                
//            }
        }
}