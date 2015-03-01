/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
/**class Amanuensis 
 *
 * Contains main method, frame, main panel
 * @author Hexahedron
 */

public class Amanuensis extends JPanel implements Pressable{

    /**
     * @param args the command line arguments
     */
    Scanner                             xScan, yScan, speedScan;
    MyKeyListener                       key;
    
    boolean                             optionChosen, mainChosen, highScoreChosen, fieldChosen;
    
    MainPane                            fM;
    JFrame 				frame;
    MainMenu                            mainMenu;
    OptionsMenu 			optionsMenu;
    HighScoresMenu                      highScoresMenu;
    MultiplayerMenu                     multiplayerMenu;
    
    int                                 screenW;
    int                                 screenH;
    double                              gameSpeed;        
    
    public Amanuensis() throws IOException, FontFormatException  
    {
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        // Init
    	
    	highScoresMenu                  = new HighScoresMenu(this);
//    	multiplayerMenu                 = new MultiplayerMenu();
    	mainMenu 			= new MainMenu(this);
        xScan                           = new Scanner(new File("res/pixelX.txt"));
        yScan                           = new Scanner(new File("res/pixelY.txt"));
        speedScan                       = new Scanner(new File("res/speed.txt"));
        
        //KeyListener
        key                             = new MyKeyListener(this);
        
        //Boolean indicates Main Menu to be active
        mainChosen                      = true;
        //Game Options properties
        screenW = xScan.nextInt();
        screenH = yScan.nextInt();
        gameSpeed = speedScan.nextDouble();
        
        //Add Main Menu
//        multiplayerMenu.addMainMenu(mainMenu);
//        highScoresMenu.addMainMenu(mainMenu);
        
    	add(mainMenu);
        
        //KeyListener
        this.setFocusable( true);
        this.addKeyListener(key);
        
        this.setVisible(true);
        this.requestFocus(true);
        this.setSize( new Dimension( screenW, screenH));
        //this.pack();
    }
    @Override
    public void update(Integer n) 
    {
        if(highScoreChosen)
        {
            highScoresMenu.update(n);
        }
        else if(optionChosen)
        {
            optionsMenu.update(n);
        }
        else if(mainChosen)
        {
            mainMenu.update(n);
            
            if(mainMenu.optionsButton.allCorrect())
            {   
                try {
                    optionsMenu = new OptionsMenu(this);
                    add(optionsMenu);
                    optionsMenu.setVisible(true);
                    mainMenu.setFocusable(false); // Remove focus from main menu so user can't call other panels before exiting current panel
                    mainMenu.setVisible(false);
                    // Remove focus from main menu so user can't call other panels before exiting current panel
                    optionsMenu.addKeyListener(key);
                    optionChosen = true;
                    mainChosen = false;
                } catch (        FileNotFoundException | UnsupportedEncodingException ex) {
                    Logger.getLogger(Amanuensis.class.getName()).log(Level.SEVERE, null, ex);
                } catch (        FontFormatException | IOException ex) {
                    Logger.getLogger(Amanuensis.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            else if(mainMenu.highScoresButton.allCorrect())
            {
                try {
                    highScoresMenu = new HighScoresMenu(this);
                    add(highScoresMenu);
                    highScoresMenu.setVisible(true);
                    mainMenu.setFocusable(false);
                    mainMenu.setVisible(false);
                    // Remove focus from main menu so user can't call other panels before exiting current panel
                    highScoresMenu.addKeyListener(key);
                    highScoreChosen = true;
                    mainChosen = false;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Amanuensis.class.getName()).log(Level.SEVERE, null, ex);
                } catch (        FontFormatException | IOException ex) {
                    Logger.getLogger(Amanuensis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            else if(mainMenu.skirmishButton.allCorrect())
            {
                //mainMenu.loading();
                try 
                {
                    fM = new MainPane( screenW, screenH, gameSpeed);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                fM.setAmanuensis(this);

                mainMenu.setVisible(false);
                mainMenu.requestFocus(false);
                add(fM);
                mainMenu = null;
                mainChosen = false;
                fieldChosen = true;
                
                fM.setVisible(true);
                fM.addKeyListener(key);
                fM.requestFocus(true);
            }

//            else if(mainMenu.multiplayerLabel.allCorrect())
//            {
//                add(multiplayerMenu);
//                multiplayerMenu.setVisible(true);
//                mainMenu.setFocusable(false);
//                mainMenu.setVisible(false);
//            }

            else if (mainMenu.exitButton.allCorrect())
            {
                System.exit(0);
            }
        }
    }
    
    public static void main(String[] args) throws IOException, FontFormatException
    {
        
        Amanuensis amanuensis   = new Amanuensis();
        JFrame frame            = new JFrame( "Amanuensis" );


        
        frame.setUndecorated(true);
        frame.add(amanuensis);
        //frame.pack();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize( new Dimension( amanuensis.screenW, amanuensis.screenH));
        frame.setSize( Toolkit.getDefaultToolkit().getScreenSize());
        //frame.setResizable( false );
        frame.setVisible(true);
    }
}
