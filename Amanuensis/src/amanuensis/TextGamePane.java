/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;

/** class TextGamePane
 *  
 * contains all of the panels in the Typing game
 * has 4 tabs during the gameplay
 * all 4 tabs are for seperate buildings and can be chosen with key inputs 1, 2, 3, 4
 * @author Hexahedron
 */

public class TextGamePane extends JTabbedPane implements Pressable{
   
    CardLayout c;
    
    //ArrayList<JLabel> l;
    
    ArrayList<TabContent> contents;
    int screenH;
    int screenW;
    
    float laneNo;
    int activeTab;
    int allScores;
    MyKeyListener key;
    
    FieldModel fm;
    boolean isGameOver; 
    
    public TextGamePane(FieldModel fm, int screenW, int screenH) throws IOException
    {
        this.fm     = fm;
        
        contents    = new ArrayList<TabContent>();
        
        key         = new MyKeyListener(this);
        
        activeTab   = 0;
        laneNo      = 0;
        
        for(int i = 0; i < 4; i++)
        {
            contents.add(new TabContent());
            addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Empty</body></html>", contents.get(i));
            setBackgroundAt(i, new Color(226, 165, 123));
        }
        
        setSize(screenW,screenH/2);
        updateUI();
        
        addKeyListener(key);
        
        this.setFocusable( true);
    }
    
    @Override
    public void update(Integer n)
    {
        if(activeTab < 0)
        {
            activeTab = 0;
        }
        if( isGameOver)
        {
            for(int i = 0; i < 4; i++)
            {
                contents.get(i).s = new SentenceContainer();
                contents.get(i).s.addLetterBag("GAME OVER");
            }
        }
        else
        {
            if( contents.get(0).buildingToGo != -1 && contents.get(0).isBuilt())
            {
                boolean weAreFinishedHere = false;

                if(n == 38)
                {
                    laneNo++;
                    laneNo = (laneNo + 3)%3;
                    notifyField();
                    weAreFinishedHere = true;
                }

                else if (n == 40)
                {
                    laneNo--;
                    laneNo = (laneNo + 3)%3;
                    notifyField();
                    weAreFinishedHere = true;
                }

                //System.out.println(n);
                if( n <= 52 && n >= 47 && !weAreFinishedHere)
                    activeTab = n - 49;
                else if(!weAreFinishedHere)
                {
                    contents.get(activeTab).update(n);
                }

                setActive();

                if( !weAreFinishedHere && contents.get(activeTab).b.selected() >= 0){


                    setTitleAt( activeTab, "<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>"+contents.get(activeTab).b.bags.get(contents.get(activeTab).b.selected()).getString() +"</body></html>");//
                    try {   
                        contents.get(activeTab).building();
                    } catch (IOException ex) {
                        Logger.getLogger(TextGamePane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    notifyField();

                    if ( contents.get(activeTab).isBuilt())
                    {
                        notifyField();
                    }
                }
           }
           else
            {
                activeTab = 0;
                contents.get(activeTab).update(n);
                setActive();
                if(  contents.get(activeTab).b.selected() >= 0){

                    setTitleAt( activeTab, "<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>"+contents.get(activeTab).b.bags.get(contents.get(activeTab).b.selected()).getString() +"</body></html>");//
                        try {
                            contents.get(activeTab).building();
                        } catch (IOException ex) {
                            Logger.getLogger(TextGamePane.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        notifyField();

                    if ( contents.get(activeTab).isBuilt())
                    {
                            notifyField();
                    }
                }

            }
            
            allScores = contents.get(0).getScoreCount() + contents.get(1).getScoreCount() + contents.get(2).getScoreCount() + contents.get(3).getScoreCount();
            
            // System.out.println(allScores);
            repaint();
        }
    }
    
    public void setActive()
    {
        if (activeTab != -2)
        setSelectedIndex(activeTab);
    }
    
    public void notifyField()
    {
        if(contents.get(activeTab).getUnitToGo() >= 0)
            fm.notifyUnit( contents.get(activeTab).unitToGo);
        contents.get(activeTab).unitToGo = -1;
        if(contents.get(activeTab).isBuilt())
            fm.notifyBuilding(contents.get(activeTab).buildingToGo, activeTab);
        contents.get(activeTab).isBuilt = false;
        fm.notifyLane(laneNo);
    }
    
    public void falls( int activeT)
    {
       // activeTab = activeT;
        contents.get( activeT).destroy();
        updateUI();
        repaint();
    }
    
    public void gameIsOver()
    {
        isGameOver = true;
    }
    
    public int getAllScores()
    {
        return allScores;
    }
    
}