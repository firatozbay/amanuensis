/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**class MainPane
 *
 * Main panel view to hold everything in the game part
 * @author Hexahedron
 */

public class MainPane extends JPanel implements Pressable, GameStatusListener{
    
    Amanuensis amanuensis;
    
    private FieldModel fM;
    private BottomPane bP;
    private FieldCanvas fC;
    
    int screenH;
    int screenW;
    String username;
    
    private double speedMod;
    private MyKeyListener key;
    
    Scanner scanScores, scanName;
            
    private boolean gameIsOver;
    
    BufferedImage background;
    ImageLoader load;
    ImageModificator modder;
    
    JPanel gameOverTab;
    LetterBag returnToMenu;
    
    public MainPane( int screenW, int screenH, double speedMod) throws IOException
    {
        this.screenH = screenH;
        this.screenW = screenW;
        this.speedMod = speedMod;
        
        load = new ImageLoader("res/background.png");
        background = load.getIMG();
        
        modder = new ImageModificator();
        double backW = background.getWidth();
        double backH = background.getHeight();
        
        background = modder.resize( background, (screenW)/backW, (screenH)/backH);
        
        key = new MyKeyListener(this);
        scanScores = new Scanner(new File("res/scorelist.txt"));
        scanName = new Scanner( new File("res/username.txt"));
        
        username = scanName.nextLine();
        
        setLayout( null);
        
        fM = new FieldModel( screenW, screenH*4/8, speedMod);
        bP = new BottomPane( fM, screenW, screenH*4/8);
        fC = new FieldCanvas( fM);
        fC.setBounds(0,0,screenW,screenH*4/8 );
        bP.setBounds(0,screenH*4/8,screenW,screenH );
        
        //Game Over**
        gameOverTab = new JPanel();
        
        gameOverTab.setLayout(new GridLayout(3,1));
	returnToMenu = new LetterBag("Returning to Main Menu", true);
        
        gameOverTab.setBackground(new Color(226, 165, 123));
        //System.out.println(fM.realWinner);

        gameOverTab.add(returnToMenu);
        //**
        addKeyListener(key);
        
        add( fC );
        add( bP);

        fM.addGameStatusListener(this);
        fM.addTextGame(bP.getTextGame());
        fM.addLevelListener( bP.getLevelDisplay());
        
        setVisible(false);
        setFocusable( true);
        
        setPreferredSize( new Dimension(screenW, screenH));
    }        

    @Override
    public void update(Integer n) {
        bP.update(n);
    }

    @Override
    public void gameIsFinished() {
        
        gameIsOver = true;
        //System.out.println( fM.getSeconds());
        //System.out.println( fM.getWPM());
        //System.out.println( fM.getWordCount());
        
        //popup here
        
        //High Score Addition**
        int[] scores = new int[10];
        String[] names = new String[10];
        for(int i = 0; i < 10; i++)
        {
            scores[i] = scanScores.nextInt();
            names[i] = scanScores.nextLine();
        }
        
        int WPM = fM.WPM;
        
        for( int i = 0; i< 10; i++)
        {
            if(WPM > scores [i])
            {
                for(int k = 9; k > i; k--)
                {
                    scores[k]   = scores[k-1];
                    names[k]    = names[k-1];
                }
                scores[i] = WPM;
                names[i] = " "+ username;
                i = 11;
            }
        }
        
        try (PrintWriter writer = new PrintWriter("res/scorelist.txt", "UTF-8")) 
        {
            for(int i = 0; i< 10; i++)
            {
                writer.println(( scores[i] + names[i]));
            }
        } catch ( FileNotFoundException | UnsupportedEncodingException ex) 
        {
              Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        //**
//        if(fM.realWinner)
//            gameOverTab.add(new JLabel("YOU WIN"));
//        else
//            gameOverTab.add(new JLabel("YOU LOST"));
        gameOverTab.add(new JLabel("GAME IS FINISHED"));
        gameOverTab.add(new JLabel("WPM: "+ WPM));
        bP.text.addTab("GAME OVER",gameOverTab);
        bP.text.activeTab = 4;
        bP.text.setSelectedIndex(4);
        
    }
    
    @Override
    public void paintComponent(Graphics g)
        {
           
            g.drawImage( background,0, 0, null);
            
        }

    void setAmanuensis(Amanuensis aThis) {
        amanuensis = aThis;
    }
    
    public class BottomPane extends JPanel implements Pressable
    {
        //props
        TextGamePane text;
        LevelDisplay levelDisplay;
        WestFiller westFiller;
        Border border;
        BottomCover bC;
        int screenW;
        int screenH;
        
        public BottomPane( FieldModel fM,int scrW, int scrH) throws IOException
        {
            //setPreferredSize( new Dimension( scrW, scrH*9/10));
            setLayout( new FlowLayout( scrW / 3, 0, 0));
            westFiller = new WestFiller( scrW *24/100 , scrH*19/20);
            text = new TextGamePane( fM, scrW/2, scrH);
            levelDisplay = new LevelDisplay( scrW*24/100, scrH*19/20);
            //setBackground( new Color( 70, 0, 0));
            text.setPreferredSize( new Dimension(scrW/2, scrH*19/20));
            //border = new Border( scrW, scrH/20);
            bC = new BottomCover( scrW, scrH/2);
            screenW = scrW;
            screenH = scrH;
            //add( border);
            add(westFiller);
            add( text);
            add( levelDisplay); 
            add(bC);
            setOpaque(false);
        }

        @Override
        public void update(Integer n) {
            text.update(n);
            if(gameIsOver)
            {
                returnToMenu.update(n);
                if(returnToMenu.allCorrect());
                try {
                    amanuensis.mainMenu = new MainMenu(amanuensis);
                    amanuensis.add(amanuensis.mainMenu);
                    amanuensis.mainMenu.setVisible(true);
                    MainPane.this.setFocusable(false);
                    MainPane.this.setVisible(false);
                    
                    amanuensis.fieldChosen = false;
                    amanuensis.mainChosen = true;
                    returnToMenu.reset();
                } catch (        FontFormatException | IOException ex) {
                    Logger.getLogger(OptionsMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        /**
         *
         * @param a
         */
        
        public LevelDisplay getLevelDisplay()
        {
            return levelDisplay;
        }
        
        public TextGamePane getTextGame()
        {
            return text;
        }
        
        @Override
        public Dimension getMinimumSize() 
        {
            return new Dimension(screenW, screenH);
        }

        @Override
        public Dimension getPreferredSize() 
        {
            return new Dimension(screenW, screenH);
        }
    }
    
    public class LevelDisplay extends JPanel implements LevelListener
    {
        //props
        BufferedImage img;
        int screenH;
        int screenW;
        int level;
        int levelTimer;
        int maxTime;
        ImageLoader loader;
        ImageModificator mod;
        
        public LevelDisplay( int sW, int sH)
        {
            setPreferredSize( new Dimension( sW, sH));
            screenH = sH;
            screenW = sW;
            loader = new ImageLoader("res/orb8.png");
            img = loader.getIMG();
            mod = new ImageModificator();
            double scrW = screenW;
            double scrH = screenH;
            double imgW = img.getWidth();
            double imgH = img.getHeight();
            
            img = mod.resize( img, (scrW*3/5)/imgW, (scrH*3/5)/imgH);
            levelTimer = 0;
            level = 0;
            maxTime = 1;
            setOpaque(false);
        }
        
        @Override
         public Dimension getMinimumSize() {
            return new Dimension(screenW, screenH);
        }

        @Override
        public Dimension getPreferredSize() {
             return new Dimension(screenW, screenH);
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            g.drawImage( img, screenW/14 + screenW/3, 2*screenH/75 +screenH/3, null);
            
            if( level > 0)
            {
                int levelC = level;
                if( 30*levelC > 240)
                      levelC = 6;
                g.setColor( new Color( 30*levelC, 0 , 255));
                //System.out.println(((screenH*5/16)*(levelTimer/maxTime)));
                //System.out.println( "xallign :" + (screenW/2 - (screenW*(5/16))) + " yaliihne: " + (screenH/2 -(screenH*(5/16)*(levelTimer/maxTime))) + " size W: " + ((screenW*5/16)*levelTimer/maxTime));
                
            g.fillOval((screenW/14 + screenW/3) + img.getWidth()/2 -  ((((screenW - (screenW/14 + screenW/3)*3/5)*3*levelTimer)/(16*maxTime))), 
                        (2*screenH/75 +screenH/3) + img.getHeight()/2 - ((((screenH - (2*screenH/75 + screenH/3)*3/5)*3*levelTimer)/(16*maxTime))), 
                        (((2*(screenW - (screenW/14 + screenW/3)*3/5)*3*levelTimer)/(16*maxTime))), 
                        (((2*(screenH - (2*screenH/75 + screenH/3)*3/5)*3*levelTimer)/(16*maxTime))));   
            }
        }

        @Override
        public void levelIsChanged(int level) {
            this.level = level;
            repaint();
        }

        @Override
        public void levelTimer(int time, int maxTime) {
           this.levelTimer = time;
           if( levelTimer <  0)
               levelTimer =0;
           this.maxTime = maxTime;
           repaint(); 
        }
    }
    
    public class WestFiller extends JPanel
    {
        public WestFiller( int sW, int sH)
        {
            setPreferredSize( new Dimension( sW, sH));
            setOpaque( false);
        }
    }
    
    public class Border extends JPanel
    {
        public Border( int sW, int sH)
        {
            setPreferredSize( new Dimension( sW, sH));
            setBackground( new Color( 40,0,0));
            setOpaque( true);
        }
    }
    
    public class BottomCover extends JPanel
    {
        public BottomCover( int sW, int sH)
        {
            setPreferredSize( new Dimension( sW, sH));
            setBackground( new Color( 40,0,0));
            setOpaque( true);  
        }
    }
}
