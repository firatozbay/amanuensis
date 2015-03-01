/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;


/**class FieldModel 
 *
 * Contains Field properties, model for the units game, which is the upper part of the user interface
 * @author Hexahedron
 */
public class FieldModel {
    
    static final int LEVEL_TIMER_LIMIT = 500;
    
    public Lane topLane;
    public Lane botLane;
    public Lane midLane;
    public Lane activeLane;
    
    double modifier;
    double speedModifier;
    boolean isGameOver;
    boolean winner;
    
    public int screenW;
    public int screenH;
    
    private RandomAI RA;
    private ArrayList < GameStatusListener> gSs;
    private ArrayList < LevelListener> lS;
    
    int wordCount;
    int WPM;
    
    int seconds;
    Timer timer;
    Timer secondsTimer;
    Building main;
    TextGamePane tG;
    int level;
    int levelTimer;
    int AIlevel ;
    int AIlevelTimer ;
    boolean realWinner;
    //TextGame tG;

    public FieldModel( int screenW, int screenH, double speedModifier)
    {
        gSs = new ArrayList < GameStatusListener> ();
        lS = new ArrayList < LevelListener> ();
        
        this.screenW = screenW;
        this.screenH = screenH;
        this.speedModifier = speedModifier;
        
        level = 0;
        levelTimer = 0;
        seconds = 0;
        
        AIlevel = 0;
        AIlevelTimer = 0;
        main = null;
        
        modifier = (double)(screenW )/topLane.TOTAL_L;
        topLane = new Lane( modifier, speedModifier, (double)screenH, 1 , this);
        botLane = new Lane( modifier, speedModifier, (double)screenH, 2 , this);
        midLane = new Lane( modifier, speedModifier, (double)screenH, 0 , this);
        activeLane = midLane;
        midLane.setActive(true);
        
        RA = new RandomAI( 100, this);
        
        timer = new Timer( 20, new TimerListener());
        secondsTimer = new Timer( 1000, new SecondsListener());
        
        realWinner = false;
        
        timer.start();
        secondsTimer.start();
    }

    FieldModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void process()
    {
        topLane.process();
        botLane.process();
        midLane.process();
        RA.process();
    }
    
    public void addUnit( LivingUnit a, boolean isHost)
    {
        Lane current = a .getLane();
        current.addUnit(a, isHost);
    }
    
    public class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {  
            levelTimer --;
            
            for( int i = 0; i < lS.size(); i++)
            {
                lS.get(i).levelTimer( levelTimer, LEVEL_TIMER_LIMIT);
            }

            if( levelTimer <= 0)
            {
                level = 0;
                for( int i = 0; i < lS.size(); i++)
                {
                    lS.get(i).levelIsChanged( level);
                }
            }
            process();
        }     
    }
    
    public Lane getTopLane()
    {
        return topLane;
    }
    
    public Lane getMidLane()
    {
        return midLane;
    }
    
    public Lane getBotLane()
    {
        return botLane;
    }
    
    public int getWPM()
    {
        return WPM;
    }
    
    public int getWordCount()
    {
        return wordCount;
    }
    
    public int getSeconds()
    {
        return seconds;
    }
      
    public boolean addMain( Building b)
    {
        main = b;
        return true;
    }
    
    public void addTextGame( TextGamePane tGM)
    {
        tG = tGM;
    }
    
    public void setActiveLane( Lane l)
    {
        activeLane = l;
    }
    
    public void createAUnit( int type, boolean user)
    {
        // at activeLane
        // with current upgrade level
        // for user
        // depending on type
    }
    
    public void notifyTextGameAboutTheDireSituation( Lane l)
    {
        if( l == midLane)
            tG.falls(2);
        else if ( l == botLane)
         
            tG.falls(1);
        else if ( l == topLane)
        {
            tG.falls(3);
        }
    }
    
    public void notifyBuilding(double buildingToGo, int activeTab) // 0 - main, 1- bot, 2- mid, 3-top
    {
        Building a;
        Lane l = null;
        
        if( activeTab == 0)
            l = midLane;
        if( activeTab == 1)
            l = botLane;
        if( activeTab == 2)
            l = midLane;
        if( activeTab == 3)
            l = topLane;
        
        if(  activeTab == 0 && (midLane.getMain( true) == null))
        {
            if( buildingToGo == 0)
                addMain( new Barracks( this, true, 0));
            else if ( buildingToGo == 1)
                addMain( new ArcheryRange( this, true, 0));
            else if ( buildingToGo == 2)
                addMain( new Stable( this, true, 0));
            else if ( buildingToGo == 3)
                addMain( new PikeTrainingGrounds( this, true, 0));
            else if ( buildingToGo == 4)
                addMain( new Armory( this, true, 0));
            else if ( buildingToGo == 5)
                addMain( new SiegeWorkshop( this, true, 0));
            else if ( buildingToGo == 6)
                addMain( new Wall( this, true, 0));
            else if ( buildingToGo == 7)
                addMain( new Keep( this, true, 0));
            else if ( buildingToGo == 8)
                addMain( new ExplosivesWorkshop( this, true, 0));
        }
        else if ( l.getNormal( true) == null && activeTab != 0)
        {
            
            if( buildingToGo == 0)
                l.addBuilding(new Barracks( l, true, 0));
            else if ( buildingToGo == 1)
                l.addBuilding( new ArcheryRange( l, true, 0));
            else if ( buildingToGo == 2)
                l.addBuilding( new Stable( l, true, 0));
            else if ( buildingToGo == 3)
                l.addBuilding( new PikeTrainingGrounds( l, true, 0));
            else if ( buildingToGo == 4)
                l.addBuilding( new Armory( l, true, 0));
            else if ( buildingToGo == 5)
                l.addBuilding( new SiegeWorkshop( l, true, 0));
            else if ( buildingToGo == 6)
                l.addBuilding( new Wall( l, true, 0));
            else if ( buildingToGo == 7)
                l.addBuilding( new Keep( l, true, 0));
            else if ( buildingToGo == 8)
                l.addBuilding( new ExplosivesWorkshop( l, true, 0));
                        //remove later  
        }
        
    }
    
    public void notifyUnit(int unitToGo)
    {
      if( unitToGo == 0)
      {
          activeLane.addUnit( new Swordsman( activeLane, true, level), true);
      }
      else if( unitToGo == 1)
      {
          activeLane.addUnit( new Archer( activeLane, true, level), true);
      }
      else if( unitToGo == 2)
      {
          activeLane.addUnit( new Cavalry( activeLane, true, level), true);
      }
      else if( unitToGo == 3)
      {
          activeLane.addUnit( new Pikeman( activeLane, true, level), true);
      }
      else if( unitToGo == 4)
      {
         level  ++;
         for( int i = 0; i < lS.size(); i++)
         {
            lS.get(i).levelIsChanged( level);
         }
          if( level == 1 )
          {
              levelTimer = (int)(LEVEL_TIMER_LIMIT);
              for( int i = 0; i < lS.size(); i++)
              {
                    lS.get(i).levelTimer( levelTimer, LEVEL_TIMER_LIMIT);
              }
          }
        }
       else if( unitToGo == 5)
          activeLane.addUnit( new Treb( activeLane, true, level), true);
       else if( unitToGo == 6)
       {}
       else if( unitToGo == 7)
       {}
       else if( unitToGo == 8)
        {
          activeLane.addUnit( new Saboteur( activeLane, true, level), true);   
        }
    }
    
    public void notifyLane(float laneNo)
    {
        if( laneNo == 0)
        {
            activeLane.setActive(false);
            activeLane = botLane;
            activeLane.setActive(true);
        }
        if( laneNo == 1)
        {
            activeLane.setActive(false);
            activeLane = midLane;
            activeLane.setActive(true);

        }
            
        if( laneNo == 2)
        {
            activeLane.setActive(false);
            activeLane = topLane;
            activeLane.setActive(true);
        }
    }
    
    public void gameIsOver( boolean a)
    {
        winner = a;
        isGameOver = true;
        timer.stop();
        secondsTimer.stop();

        //AI stop
        tG.gameIsOver();
        wordCount = tG.getAllScores();
        WPM = wordCount*60/seconds;
                
        for( int i = 0; i < gSs.size(); i++)
            gSs.get(i).gameIsFinished();
    }
    
    public void addGameStatusListener( GameStatusListener a)
    {
        gSs.add(a);
    }
    
    public void addLevelListener( LevelListener l)
    {
        lS.add(l);
    }
     
    
    //INSERT AI DOWN HERE
    //Ultra Easy AI
    //Random Spawns
    // extra idiot
    
    
    public class RandomAI
    {
        static final int AVARAGE_WORD_COUNT = 5;
        int avarageWordCount;
        int ticks; // AI decission ticks normally ticks 50 times in a  second so given the WPM is 20, in a min it should tick aroun 4 -5 times so 60*50/5 = 600, an act for one in 600 ticks 
        
        boolean goOn; // if players main is Exist AI can countinue
        boolean isGameOver;
        LivingUnit storedUnit;
        int WPM;
        
        Lane critical;
        ArrayList<Integer> types; // array of types
        
        FieldModel fM;
        
        public RandomAI( int WPM, FieldModel fM)
        {
            avarageWordCount = AVARAGE_WORD_COUNT;
            ticks = 60*50*avarageWordCount/WPM;
            goOn = false;
            isGameOver = false;
            types = new ArrayList<Integer>();
            types.add(0);
            types.add(0);
            types.add(0);
            types.add(0);
            this.WPM = WPM;
            critical = null;
            
            this.fM = fM;
        }
        
        public void storeAUnit( )
        {
            types = calculateTypes();
            int randomNumber;
            int randomType;
            critical = botLane;
            if( midLane.getUnits(true).size() - midLane.getUnits(false).size() 
                    > critical.getUnits(true).size() - critical.getUnits(false).size())
                critical = midLane;
            if( topLane.getUnits(true).size() - midLane.getUnits(false).size() 
                    > critical.getUnits(true).size() - critical.getUnits(false).size())
                critical = topLane;
            randomNumber =( int )(Math.random()*types.size());
            randomType = types.get(randomNumber);
            if( randomType == 4 || randomType == 6 || randomType == 7 )
            {
                if( randomType == 4)
                {
                    
                   // AIlevel ++;
                    if( AIlevelTimer <= 0 && AIlevel == 1)
                    {
                        AIlevelTimer = (int)(LEVEL_TIMER_LIMIT); 
                    }                
                    //ticks = 60*50*avarageWordCount/WPM;
                }
                else
                    randomType = types.get(( int )(types.get(((int)Math.random()*types.size()))));
            }

            else if ( randomType == 0)
                storedUnit = new Swordsman( critical, false, AIlevel);
            else if( randomType == 1)
                storedUnit = new Archer( critical, false, AIlevel);
            else if ( randomType == 2)
                storedUnit = new Cavalry( critical, false, AIlevel);
            else if ( randomType == 3)
                storedUnit = new Pikeman( critical, false, AIlevel);
            else if ( randomType == 5)
                storedUnit = new Treb( critical, false, AIlevel);
            else if ( randomType == 8)
                storedUnit = new Saboteur( critical, false, AIlevel);  
        }
        
        public void build( Lane lane)
        {
            int randomNumber;
            randomNumber =( int )(Math.random()*9);
            while (buildingExist( randomNumber))
            {
                randomNumber ++;
                if( randomNumber >= 9)
                    randomNumber = 0;
            }
            if ( randomNumber == 0 && !buildingExist(0))
            {
                lane.addBuilding( new Barracks( lane, false, 0));
            }
            else if ( randomNumber == 1 && !buildingExist( 1))
            {
                lane.addBuilding( new ArcheryRange( lane, false, 0));
            }
            else if ( randomNumber == 2 && !buildingExist( 2))
            {
                lane.addBuilding( new Stable( lane, false, 0));
            }
            else if ( randomNumber == 3 && !buildingExist( 3))
            {
                lane.addBuilding( new PikeTrainingGrounds( lane, false, 0));
            }
            else if ( randomNumber == 4 && !buildingExist( 4))
            {
                lane.addBuilding( new Armory( lane, false, 0));
            }
            else if ( randomNumber == 5 && !buildingExist( 5))
            {
                lane.addBuilding( new SiegeWorkshop( lane, false, 0));
            }
            else if ( randomNumber == 6 && !buildingExist( 6))
            {
                lane.addBuilding( new Wall( lane, false, 0));
            }
            else if ( randomNumber == 7 && !buildingExist( 7))
            {
                lane.addBuilding( new Keep( lane, false, 0));
            }
            else if ( randomNumber == 8 && !buildingExist( 8))
            {
                lane.addBuilding( new ExplosivesWorkshop( lane, false, 0));
            }
           // types.add( randomNumber);
        }
        
        public boolean buildingExist( int a)
        {
            for( int i = 0; i < types.size(); i++ )
            {
                if( types.get(i).equals(a))
                {
                    return true;
                }
            }
            return false;
        }
             
        public boolean process()
        {
            AIlevelTimer = AIlevelTimer --;
            if( AIlevelTimer <= 0)
                AIlevel = 0;
            ticks --;
            
            if( types.size() > 0)
                storeAUnit();
            
            if( isGameOver)
                return true;
            
            if( midLane.getMain(true) == null) // if teher is no main buiding on the suers side
                {
                goOn = false;
                return true; 
                } 
            if( noOfBuildings(false) < 4 && ticks <= 0)
            {
                if( midLane.getMain(false) == null)
                    midLane.addMainBuilding(new Barracks( fM, false, 0));
                else if( midLane.getNormal( false) == null)
                    build( midLane);
                else if( topLane.getNormal( false) == null)
                    build( topLane);
                else if( botLane.getNormal( false) == null)
                    build( botLane);   
                ticks = 60*50*avarageWordCount/WPM;            
            }
            
            if( storedUnit != null && ticks <= 0)
            {
                addUnit( storedUnit, false);
                storedUnit = null;
                ticks = 60*50*avarageWordCount/WPM;
                return true;
            }  
            return false;
        }
        
        public ArrayList<Integer> calculateTypes()
        {
            ArrayList<Integer> aa = new ArrayList<Integer>();
            
            if( midLane.getMain(false) != null)
            {
                if( midLane.getMain(false).getClass() == Barracks.class )
                    aa.add(0);
                else if( midLane.getMain(false).getClass() == ArcheryRange.class )
                    aa.add(1);
                else if( midLane.getMain(false).getClass() == Stable.class )
                    aa.add(2);
                else if( midLane.getMain(false).getClass() == PikeTrainingGrounds.class )
                    aa.add(3);
                else if( midLane.getMain(false).getClass() == Armory.class )
                    aa.add(4);
                else if( midLane.getMain(false).getClass() == SiegeWorkshop.class )
                    aa.add(5);
                else if( midLane.getMain(false).getClass() == Wall.class )
                    aa.add(6);
                else if( midLane.getMain(false).getClass() == Keep.class )
                    aa.add(7);  
                else if( midLane.getMain(false).getClass() == ExplosivesWorkshop.class )
                    aa.add(8);                
            }
             if( midLane.getNormal( false) != null)
            {
                 if( midLane.getNormal(false).getClass() == Barracks.class )
                    aa.add(0);
                else if( midLane.getNormal(false).getClass() == ArcheryRange.class )
                    aa.add(1);
                else if( midLane.getNormal(false).getClass() == Stable.class )
                    aa.add(2);
                else if( midLane.getNormal(false).getClass() == PikeTrainingGrounds.class )
                    aa.add(3);
                else if( midLane.getNormal(false).getClass() == Armory.class )
                    aa.add(4);
                else if( midLane.getNormal(false).getClass() == SiegeWorkshop.class )
                    aa.add(5);
                else if(midLane.getNormal(false).getClass() == Wall.class )
                    aa.add(6);
                else if( midLane.getNormal(false).getClass() == Keep.class )
                    aa.add(7);  
                else if( midLane.getNormal(false).getClass() == ExplosivesWorkshop.class )
                    aa.add(8);   
            }
            if( topLane.getNormal( false) != null)
            {
                 if( topLane.getNormal(false).getClass() == Barracks.class )
                    aa.add(0);
                else if( topLane.getNormal(false).getClass() == ArcheryRange.class )
                    aa.add(1);
                else if( topLane.getNormal(false).getClass() == Stable.class )
                    aa.add(2);
                else if( topLane.getNormal(false).getClass() == PikeTrainingGrounds.class )
                    aa.add(3);
                else if( topLane.getNormal(false).getClass() == Armory.class )
                    aa.add(4);
                else if( topLane.getNormal(false).getClass() == SiegeWorkshop.class )
                    aa.add(5);
                else if(topLane.getNormal(false).getClass() == Wall.class )
                    aa.add(6);
                else if( topLane.getNormal(false).getClass() == Keep.class )
                    aa.add(7);  
                else if( topLane.getNormal(false).getClass() == ExplosivesWorkshop.class )
                    aa.add(8);   
            }
            if( botLane.getNormal( false) != null)
            {
                 if( botLane.getNormal(false).getClass() == Barracks.class )
                    aa.add(0);
                else if( botLane.getNormal(false).getClass() == ArcheryRange.class )
                    aa.add(1);
                else if( botLane.getNormal(false).getClass() == Stable.class )
                    aa.add(2);
                else if( botLane.getNormal(false).getClass() == PikeTrainingGrounds.class )
                    aa.add(3);
                else if( botLane.getNormal(false).getClass() == Armory.class )
                    aa.add(4);
                else if( botLane.getNormal(false).getClass() == SiegeWorkshop.class )
                    aa.add(5);
                else if(botLane.getNormal(false).getClass() == Wall.class )
                    aa.add(6);
                else if( botLane.getNormal(false).getClass() == Keep.class )
                    aa.add(7);  
                else if( botLane.getNormal(false).getClass() == ExplosivesWorkshop.class )
                    aa.add(8);   
            }
           if( !aa.isEmpty())
                return aa;
           else
               return types;
        }
        
        
        public boolean playerHasBuilding( Class b)
        {
            if( midLane.getMain( true).getClass() == b)
                return true;
            
            else if
            ( midLane.getNormal(true).getClass() == b)
                return true;
            
            else if
            ( botLane.getNormal(true).getClass() == b)
                return true;
            
            else if
            ( topLane.getNormal(true).getClass() == b)
                return true;
            else
                return false;      
        }
        
        public boolean AIHasBuilding( Class b)
        {
           if( midLane.getMain( false).getClass() == b)
                return true;
            
            else if
            ( midLane.getNormal(false).getClass() == b)
                return true;
            
            else if
            ( botLane.getNormal(false).getClass() == b)
                return true;
            
            else if
            ( topLane.getNormal(false).getClass() == b)
                return true;
            else
                return false; 
        }
        
        public int noOfBuildings( boolean isHost)
        {
            int count;
            count = 0;
            
            if( midLane.getMain( isHost) != null)
                count ++;
            if( midLane.getNormal( isHost) != null)
                count ++;
            if( botLane.getNormal( isHost) != null)
                count ++;
            if( topLane.getNormal(isHost) != null)
                count ++;
            
            return count;                   
        }
        
        public int unitNumber( Lane lane, Class a, boolean isHost)
        {
            int count = 0;
            ArrayList<LivingUnit> kappa = new ArrayList<LivingUnit>();
            kappa = lane.getUnits( isHost);
            
            for( int i = 0; i < kappa.size(); i++)
            {
                if( kappa.get(i).getClass() == a)
                {
                    count ++;
                }
            }
            return count;
        } 
        
    }
    
    public class SecondsListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++;
            
        }
        
    }
    
    
    
  
}

