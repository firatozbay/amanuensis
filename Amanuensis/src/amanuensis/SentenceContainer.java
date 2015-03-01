package amanuensis;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**class SentenceContainer
 * 
 * Panel of the typing game, takes txt file of the sentences list shuffles sentences and adds them to view
 * the first sentence is same it is not included in the shuffle, to provide more fair beginning of the game
 * @author Hexahedron
 */

class SentenceContainer extends JPanel implements Pressable
{
	
	ArrayList<LetterBag> sentences;
	MyKeyListener key;
        Scanner scan;
        
        int lineNumber;
        int wordScore;
        
        int score;
        boolean state;
        
	public SentenceContainer()
	{
            
                setLayout(new GridLayout(4,1,15,15));
                
		sentences = new ArrayList<LetterBag>();
                
                try {
                    scan = new Scanner(new File("res/quotelist.txt"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SentenceContainer.class.getName()).log(Level.SEVERE, null, ex);
                }

                score = 0;
                wordScore = 0;
                
                setBackground(Color.WHITE);
                
		this.setFocusable( true);
                
                setSize(400,300);
                lineNumber = scan.nextInt();
                scan.nextLine();
                
                for(int i = lineNumber; i > 0; i--)
                {
                    sentences.add(new LetterBag(scan.nextLine(), sentences.size()<1));
                }
                shuffle();
                for(int i = 0; i < 4; i++)
                {
                    add(sentences.get(i));
                }
                
                setOpaque(true);
		setVisible(true);
	}
	
	public void addLetterBag(String s)
	{
		sentences.add(new LetterBag(s, sentences.size()<1));
		if(sentences.size()<5)
			add(sentences.get(sentences.size()-1));
	}
        
        public int getScore()
        {
            return score;
        }
	
        @Override
	public void update(Integer n)
	{
            
            state = false;
            
            if( !sentences.isEmpty())
            {
                sentences.get(0).update(n);
                
                
                if(sentences.get(0).allCorrect())
                {
                    score++;
                    
                    wordScore += sentences.get(0).getNoOfWords();
                    
                    remove(sentences.get(0));
                    sentences.remove(sentences.get(0));
                    
                    state = true;
                    
                    if( sentences.isEmpty())
                        addLetterBag("There are no quotes left");
                    else if( sentences.size() < 5 )
                        add( sentences.get(sentences.size()-1));
                    else
                        add( sentences.get(3));
                    
                    sentences.get(0).setActive(true);
                }
                
                if( !sentences.get(0).atZero() && n == sentences.get(0).getPrevious().getCharCode() && n == 32 && !sentences.get(0).getCurrent().getTried())
                {
                    state = true;
                    score++;
                    //System.out.println(score);
                }
            }
	}
        
        public final void shuffle()
        {
            Random gen = new Random();
            //todo
            for(int i = 1; i < sentences.size()-1; i++)
            {
                int rand = gen.nextInt( sentences.size()-1)+ 1;
                LetterBag temp = sentences.get(i);
                sentences.set(i, sentences.get(rand));
                sentences.set(rand, temp);
            }
            
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
        }
        
        public void setState( boolean a) // true if unit spawner false if not
        {
            state = a;
        }
        
        public boolean getState()
        {
            return state;
        }
        
        public void setScore( int a)
        {
            score = a;
        }
        
        public int getWordScore()
        {
            return wordScore;
        }
        
        public void setWordScore() {
            wordScore = 0;
        }
}
