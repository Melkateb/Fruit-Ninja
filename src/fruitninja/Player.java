package fruitninja;

import GUI.Gameover;
import java.util.ArrayList;

public class Player {
    private String highestscore;

    public String getHighestscore() {
        return highestscore;
    }

    public void setHighestscore(String highestscore) {
        this.highestscore = highestscore;
    }
    
       private static Player p = null;
    private int Score;
    private int Live = 3;
    private int Time = 0;
    
    ArrayList<Observer> observers = new ArrayList<Observer>();

    public int getScore() {
        if(Score<0)
            return 0;
        return Score;
    }

    public int getLive(){
        if(Live <0)
            return 0;
        return Live;
    }
    
    public int getTime() {
     //   if(Time<0)
       //     return 0;
        return Time;
    }
    public void attach(Observer o) {
      //  System.out.println("inside add");
        observers.add(o);
           //     System.out.println(observers.size());

        
    }

    public void notifyAllObservers() {
      //  System.out.println(observers.size());
        for (int i = 0; i < observers.size(); i++) {
      //      System.out.println("inside observers");
            observers.get(i).Update();
        }
    }

    public void setScore(int Score) {
        this.Score = this.Score+Score;
        notifyAllObservers();
    }

    public void setLive(int Live){
        this.Live =this.Live - Live;
        notifyAllObservers();
        if(Live <= 0){
            Gameover over=new Gameover();
             over.setVisible(true);
        }
        if(Live>3)
        {
            this.Live=3;
        }
    }
    
    public void setTime(int time) {
        this.Time =time;
        notifyAllObservers();
    }
  
    
    private Player() {
        
    }

    public static Player getInstance() {
        if (p == null) {
            p= new Player();
            return p;
        }
        return p;
    }

    
}
