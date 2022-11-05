package GUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameoverPanel extends JPanel implements ActionListener{

    Graphics g;

      public GameoverPanel() {
       
    }
      protected void paintComponent(Graphics g) {
               

        super.paintComponent(g);
        ImageIcon im = null;
         im = new ImageIcon("game over.png");
          g.drawImage(im.getImage(), 0, 0, this.getSize().width, this.getSize().height, this);
      }
    @Override
    public void actionPerformed(ActionEvent ae) {
    }
   
}
