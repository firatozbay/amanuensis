package amanuensis;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**class LetterBag
 *
 * Contains LetterBag properties and functions, Has letters in it, they are generally sentences but they are used as menu input views to 
 * They can react to function as many useful things
 * @author Hexahedron
 */
public class LetterBag extends JPanel implements Pressable{
    
    Letter[] letters;
    int index;
    Color bordercolor;
    boolean active;
    int wrongTries;
    String str;
    
    public LetterBag(String str, boolean active)
    {
        setLayout(new FlowLayout());
        
        letters = new Letter[str.length()];
        this.active = active;
        this.str = str;
        
        index = 0;
        wrongTries = 0;
        
        bordercolor = Color.BLACK;
                
        for(int i = 0; i < str.length(); i++)
        {
            letters[i] = new Letter(str.charAt(i));
            add(letters[i]);
        }
        
        if(active)
            setBorder (BorderFactory.createLineBorder (bordercolor, 3));
        
        setOpaque(false);
        setVisible(true);
    }

    public int atLetter()
    {
        return index;
    }
    
    public void setActive(boolean setActive)
    {
        this.active = setActive;
        
        if(this.active)
            setBorder (BorderFactory.createLineBorder (bordercolor, 3));
    }
    
    @Override
    public void update( Integer n)
    {
        //for(int i = 0; i < letters.length; i++)
        //System.out.println(letters[i].getCharCode());
        //System.out.print("letterbag" + n + "current" + getCurrent().getCharCode());
        if(!allCorrect()&& getCurrent() != null)
        {
            if( n == getCurrent().getCharCode() )
            {
                try {
                    nextLetter();
                } catch (        FontFormatException | IOException ex) {
                    Logger.getLogger(LetterBag.class.getName()).log(Level.SEVERE, null, ex);
                }
                wrongTries--;
            }
            else if(getCurrent()== null)
            {
                //isOver
            }
            else
            {
                wrongTries++;
                
                letters[index].setColor(false);
                
                if(wrongTries >= 5)
                    penalty();
            }
        }
        this.updateUI();
    }
    
    public void nextLetter() throws FontFormatException, IOException
    {
        letters[index].setColor(true);
        
        index++;
        if(allCorrect() && active)
        {
            bordercolor = Color.green;
            setBorder(BorderFactory.createLineBorder(bordercolor, 3));
            
        }
        else if(allCorrect())
        {
            reset();
            setLogo();
        }
        
    }
    public void setLogo() throws FontFormatException, IOException{
        
        for(int i = 0; i < str.length(); i++)
        {
            letters[i].setLogoFont();
        }
    }
    public void setButton() throws FontFormatException, IOException{
        
        for(int i = 0; i < str.length(); i++)
        {
            letters[i].setButtonFont();
            
        }
    }
    public void setText(String s)
    {
        
        for(int i = 0; i < str.length(); i++)
        {
            remove(letters[i]);
            letters[i] = new Letter(str.charAt(i));
            add(letters[i]);
        }
        
        //System.out.println(s);
        letters = new Letter[s.length()];
        
        for(int i = 0; i < s.length(); i++)
        {
            letters[i] = new Letter(s.charAt(i));
            this.add(letters[i]);
        }
        repaint();
    }
    public void reset()
    {
        for(int i = 0; i < str.length(); i++)
            {
                remove(letters[i]);
                letters[i] = new Letter(str.charAt(i));
                add(letters[i]);
            }
        if(active)
            setBorder (BorderFactory.createLineBorder (Color.black, 3));
        index = 0;
    }

    public void penalty()
    {
            if( index > 5)
            {
                for( int i = 0; i < 5; i++)
                    letters[index-5+i].setColor(false);
                index = index -5;
            }
            else
            {
                for( int i = 0; i < index; i++)
                    letters[i].setColor(false);
                index = 0;
            }
            
            wrongTries = 0;
            
    }
    
    public int getNoOfWords()
    {

        int wordCount = 1;

        for (int i = 0; i < str.length(); i++) 
        {
            if(str.charAt(i) == ' ')
            {
                wordCount++;
            }
        }
        return wordCount;
    }
    
    public String getString()
    {
        return str;
    }
    
    public boolean allCorrect()
    {
        return letters[letters.length-1].getColor();
    }
    
    public boolean atZero()
    {
        return index == 0;
    }
    
    public Letter getCurrent()
    {
        if(allCorrect())
            return null;
        return letters[index];
    }
    
    public Letter getPrevious()
    {
        if(allCorrect() || index == 0)
            return null;
        return letters[index-1];
    }
    
    @Override
    public String toString()
    {
        String s = "";
        for( int i = 0; i < letters.length; i++ )
        {
            s += letters[i];
        }
        return s;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

}
