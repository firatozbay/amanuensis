/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JPanel;

/** class TabContent
 *  
 * panel to hold building list and sentence container then it is going to be stored in the JTabbedPane of the TextGamePane
 * has some of the rules for the typing game
 * @author Hexahedron
 */
public class TabContent extends JPanel{
    
    BuildingList        b;
    SentenceContainer   s;
    
    boolean             state; 
    int                 unitToGo;
    public double       buildingToGo;   //4 elemanli 10 farkli int degeri
    boolean             isBuilt;
    int                 count;
    boolean             unitReady;
    int                 scoreCount;
    

    
    public TabContent()
    {
        setLayout( new GridLayout());
        
        
        
        b = new BuildingList();
        s = new SentenceContainer();
        
        state = false;
        
        count  = 0;
        unitToGo = -1;
        buildingToGo = b.selected();
        
        isBuilt = false;
        add(b);
        count++;
        
        setVisible(true);
        unitReady = s.getState();
                
    }
    
    public void update(Integer n)
    {
        //unitToGo = -1;
        //buildingToGo = -1;
        //System.out.println( s.getScore());
        if(state){
            s.update(n);
            unitReady = s.getState();
            isBuilt = s.getScore()>=4;
        } 
        else
        {
            b.update(n);
            buildingToGo = b.selected();
        }
        if(unitReady)
        {
            scoreCount += s.getWordScore();
            s.setWordScore();
            
            if(isBuilt)
            {
                unitToGo = (int) buildingToGo;
                s.setState(false);
            }
        }
    }
    
    public int getUnitToGo()
    {
        return unitToGo;
    }
    
    public double getBuildingToGo()
    {
        return buildingToGo;
    }
    
    public int getScoreCount(){
        return scoreCount;
    }
    
   public void building() throws IOException{
        
        state = true;
        
        buildingToGo = b.selected();
        
        if(count==1){
            s = new SentenceContainer();
            add(s);
            remove(b);
            count++;
        }
        
        repaint();
    }
    
    public boolean isBuilt(){
        
        if(s.getScore() >= 4)
        {
            isBuilt = true;
            state = true;
        }
        
        repaint();
        
        return isBuilt;
    }
    
    public void destroy(){
        
      //setLayout( new GridLayout());
        
        //remove(s);
        //remove(b);
        
       // b = new BuildingList();
        //s = new SentenceContainer();
        //state = false;
        //isBuilt = false;
       // unitReady = false;
        //count = 0;
        
        //add(b);
        //s.setScore(0);
        
        //buildingToGo = -1;
        reInit();
        
        updateUI();
        repaint();
    }
    
    public void reInit()
    {
        setLayout( new GridLayout());
        
        remove(s);
        remove(b);
        
        b = new BuildingList();
        s = new SentenceContainer();
        
        
        state = false;
        
        count  = 0;
        unitToGo = -1;
        buildingToGo = b.selected();
        
        isBuilt = false;
        add(b);
        count++;
        
        setVisible(true);
        unitReady = s.getState();
    }
}
