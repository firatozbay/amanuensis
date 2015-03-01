package amanuensis;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
//Rules changed: 5 max incorrect tries then reduce 10 letters from completion

/**class Letter 
 *
 * Contains Letter properties and functions, turns green as a label and takes input as integer and checks the keycode
 * @author Hexahedron
 */

public class Letter extends JLabel{
        
        public final static int FONT_BASE = 1;
        public static int FONT_SIZE = 1;
	
	final String allLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";	
        final String allNumbers = "0123456789";
        
        boolean color, tried;
	
	char c;
	String s;
        
        Font f;
        
	public Letter(char c)
	{
            this.c = c;
            color = false;
            this.s = ""+ c;
            setForeground(Color.black);
            this.setText(s);
            updateUI();
            setFont(getFont().deriveFont(14.0f));
	}
	
	public void setColor(boolean color)
	{
            this.color = color;
            tried = true;
            if(color){
                setForeground(Color.green);
        }
            else if(tried)
                    setForeground(Color.red);
            else
                    setForeground(Color.black);
            this.updateUI();
            this.setText(s);
	}
        
        public boolean getTried()
        {
            return tried;
        }
	
	public boolean getColor()
	{
		return color;
	}
        
	public void setLogoFont() throws FontFormatException, IOException
        {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("res/staccato-222-bt.ttf"));
            this.setFont(f);
            this.setFont(this.getFont().deriveFont(144.0f));
            this.setForeground(Color.WHITE);
        }
        
        public void setButtonFont() throws FontFormatException, IOException
        {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("res/staccato-222-bt.ttf"));
            this.setFont(f);
            this.setFont(this.getFont().deriveFont(92.0f));
        }
        
	public boolean isTrue(char c)
	{
		return c == this.c;
	}
        
	public int getCharCode()
	{
            if(c == ' ')
                return 32;
            if(c == ',')
                return 44;
            if(c == '-')
                return 45;
            if(c == '.')
                return 46;
            if(c == '\'')
                return 222;
            if(allLetters.indexOf(c) == -1)
                return allNumbers.indexOf(c) + 48;
            return allLetters.indexOf(c)%26 + 65;
	}
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
        }
}
