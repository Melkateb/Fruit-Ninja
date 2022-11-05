package fruitninja;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class FatalBomb implements GameObject {
    
    int xloc, yloc;
    boolean sliced;
    boolean movingup = true;

    @Override
    public int getXlocation() {
        return xloc;
    }

    @Override
    public int getYlocation() {
        return yloc;
    }

    @Override
    public void setXLocation(int x) {
        this.xloc = x;
    }

    @Override
    public void setYLocation(int y) {
        this.yloc = y;
    }

    @Override
    public boolean isMovingUp() {
        return movingup;
    }

    @Override
    public void setMovingup(boolean movingUp) {
        this.movingup = movingUp;
    }

    @Override
    public int getMaxHeight() {
        return 100;
    }

    @Override
    public int getInitialVelocity() {
        return 22;
    }

    @Override
    public int getFallingVelocity() {
        return 42;
    }

    @Override
    public boolean isSliced() {
        return sliced;
    }

    @Override
    public boolean hasMovedOffScreen() {
        return false;
    }

    @Override
    public void slice() {
        sliced = true;
    }

    @Override
    public void move(double time) {
        
    }

    @Override
    public BufferedImage[] getBufferedImage() {
        BufferedImage b[] = new BufferedImage[3];
        try {
            b[0] = ImageIO.read(new File("fatal bomb_burned.png"));
            b[1] = ImageIO.read(new File("crash2_burned.png"));
            b[2] = ImageIO.read(new File("crash2_burned.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    /*@Override
    public ImageIcon getObjectImage() {
        ImageIcon i = new ImageIcon("apple3.png");
        return i;
    }*/

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHiegth() {
        return 100;
    }
    
     @Override 
   public String name(){
       return "fatal";
   }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void playMusic(String filepath) {
 InputStream music;
      try{
          music=new FileInputStream(new File(filepath));
          AudioStream audios = new AudioStream(music);
          AudioPlayer.player.start(audios);
      }catch(Exception e){}      }
}
