package GUI;

import Controller.GameController;
import fruitninja.GameObject;
import fruitninja.Player;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ArcadePanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    GameController controller = GameController.getInstance();
    String highestscore;
    ArrayList<GameObject> arr = new ArrayList<>();
    Timer t = new Timer(100, this);
    boolean view = true;
    int time = 60;

    public ArcadePanel() {
        fillArray();
        addMouseMotionListener(this);
        t.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage pic[] = new BufferedImage[3];
        ImageIcon bk = null;

        if (view) {
            bk = new ImageIcon("bk.jpg");
        } else {
            bk = new ImageIcon("");
        }

        g.drawImage(bk.getImage(), 0, 0, this.getSize().width, this.getSize().height, this);
        for (int i = 0; i < arr.size(); i++) {
            if (!arr.get(i).isSliced()) {
                pic = arr.get(i).getBufferedImage();
                Image image = pic[0];
                g.drawImage(image, arr.get(i).getXlocation(), arr.get(i).getYlocation(), arr.get(i).getWidth(), arr.get(i).getHiegth(), this);
            } else {
                if (arr.get(i).name().equals("fatal") || arr.get(i).name().equals("bomb")) {
                    pic = arr.get(i).getBufferedImage();
                    Image image = pic[1];
                    g.drawImage(image, arr.get(i).getXlocation() - 50, arr.get(i).getYlocation(), arr.get(i).getWidth(), arr.get(i).getHiegth(), this);

                } else {
                    pic = arr.get(i).getBufferedImage();
                    Image image = pic[1];
                    g.drawImage(image, arr.get(i).getXlocation() - 50, arr.get(i).getYlocation(), arr.get(i).getWidth(), arr.get(i).getHiegth(), this);

                    pic = arr.get(i).getBufferedImage();
                    Image image1 = pic[2];
                    g.drawImage(image1, arr.get(i).getXlocation() + 50, arr.get(i).getYlocation(), arr.get(i).getWidth(), arr.get(i).getHiegth(), this);
                }
            }
        }
    }

    public void fillArray() {
        if (time > 0) {
            int X = 200;
            int starty = 600;
            Random r = new Random();
            int m = r.nextInt(5) + 1;
            for (int i = 0; i < m; i++) {
                GameObject go = controller.createGameObject();
                go.setXLocation((int) X);
                go.setYLocation(starty);
                X = (int) (Math.random() * 1000);
                arr.add(go);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (time > 0) {
            time--;
            Player.getInstance().setTime(time);
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).isMovingUp()) {
                    int y = arr.get(i).getYlocation();
                    int x = arr.get(i).getXlocation();
                    if (!arr.get(i).isSliced()) {
                        y -= arr.get(i).getInitialVelocity();
                    } else {
                        y = y /*+ 300*/ + arr.get(i).getFallingVelocity();
                    }
                    x += 10;
                    /* y -= arr.get(i).getInitialVelocity();
                arr.get(i).setYLocation(y);
                x += 10;*/
                    arr.get(i).setXLocation(x);
                    arr.get(i).setYLocation(y);
                    /*  if(x<500){
                x += 10;
                arr.get(i).setXLocation(x);}
                else{
                    x -= 10;
                    arr.get(i).setXLocation(x);
                }*/
                    // System.out.println("Y location is "+y);
                    if (y < arr.get(i).getMaxHeight()) {
                        arr.get(i).setMovingup(false);
                    }
                } else if (!arr.get(i).isMovingUp()) {
                    int y = arr.get(i).getYlocation();
                    int x = arr.get(i).getXlocation();
                    /*y += arr.get(i).getFallingVelocity();
                arr.get(i).setYLocation(y);
                x += 10;*/
                    if (arr.get(i).isSliced()) {
                        y = y /*+ 300*/ + arr.get(i).getFallingVelocity();
                    } else {
                        y += arr.get(i).getFallingVelocity();
                    }
                    x += 10;
                    arr.get(i).setYLocation(y);
                    arr.get(i).setXLocation(x);
                    /*  if(x<500) {
                x += 10;
                arr.get(i).setXLocation(x);}
                else{
                    x-=10;
                    arr.get(i).setXLocation(x);*/
 /*if(arr.get(i).getYlocation()==700 || arr.get(i).getYlocation()==710 || arr.get(i).getYlocation()==720){
               if(!arr.get(i).isSliced() && live>0)
                  {
                      live--;
                      Player.getInstance().setLive(1);
                      // System.out.println("The lives lost due to non sliced fruit "+live+" isSliced "+arr.get(i).isSliced());
                  }
              }*/
                }
            }
            if (time == 0) {
                if (highestscore != null) {
                    if (Player.getInstance().getScore() > Integer.parseInt(highestscore)) {
                        save();
                    }
                    load();
                }
                Gameover over = new Gameover();
                over.setVisible(true);
                Player.getInstance().setTime(time);
                Player.getInstance().setScore(0);

            }
        }

        if (time % 20 == 0) {
            fillArray();
            repaint();
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
       int x = me.getX();
        int y = me.getY();
        for (int i = 0; i < arr.size(); i++) {
            GameObject gc = arr.get(i);
            int xg = gc.getXlocation();
            int yg = gc.getYlocation();
            if (x >= xg && x <= xg + gc.getWidth()) {
                if (y >= yg && y <= yg + gc.getHiegth()) {
                    if (!gc.isSliced() /*|| gc.name().equals("fatal") || gc.name().equals("bomb")*/) {
                        gc.slice();
                        gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Critical.wav");
                        Player.getInstance().setScore(gc.getScore());
                        if (gc.name().equals("fatal") || gc.name().equals("bomb")) {
                            Player.getInstance().setScore(-10);
                          gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Bomb-explode.wav");

                        }
                    }
                   
                    // Player.getInstance().setScore(10);
                    if (gc.isSliced()/*&& gc.name().equals("bomb")*/) {
                        //Player.getInstance().setLive(1);
                        // live --;
                        if (time == 0) {
                           // save();
                           Gameover over = new Gameover();
                            over.setVisible(true);
                            //  Player.getInstance().setScore(0);
                            //  Player.getInstance().setTime(time);
                        }
                    }
                }
            }
        }
         load();
        repaint(); 
    }

    @Override
    public void mouseMoved(MouseEvent e) {
 int x = e.getX();
        int y = e.getY();
        for (int i = 0; i < arr.size(); i++) {
            GameObject gc = arr.get(i);
            int xg = gc.getXlocation();
            int yg = gc.getYlocation();
            if (x >= xg && x <= xg + gc.getWidth()) {
                if (y >= yg && y <= yg + gc.getHiegth()) {
                    if (!gc.isSliced() /*|| gc.name().equals("fatal") || gc.name().equals("bomb")*/) {
                        gc.slice();
                        gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Critical.wav");
                        Player.getInstance().setScore(gc.getScore());
                        if (gc.name().equals("fatal") || gc.name().equals("bomb")) {
                            Player.getInstance().setScore(-10);
                            gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Bomb-explode.wav");

                        }
                    }

                    // Player.getInstance().setScore(10);
                    if (gc.isSliced()/*&& gc.name().equals("bomb")*/) {
                        //Player.getInstance().setLive(1);
                        // live --;
                        if (time == 0) {
                            // save();
                            Gameover over = new Gameover();
                            over.setVisible(true);
                            //  Player.getInstance().setScore(0);
                            //  Player.getInstance().setTime(time);
                        }
                    }
                }
            }
        }
        load();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    public String gethighestscore() {
        //  if(Player.getInstance().getScore()>Integer.parseInt(highestscore))
        //    highestscore=""+Player.getInstance().getScore();
        return highestscore;
    }

    public void save() {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();
            Element root = doc.createElement("Fruits");
            doc.appendChild(root);
            Attr attrType = doc.createAttribute("type");

            Element Score = doc.createElement("score");
            root.appendChild(Score);
            String x = Integer.toString(Player.getInstance().getScore());
            Score.appendChild(doc.createTextNode(x));

            Element cname = doc.createElement("score");

            String move = Integer.toString(Player.getInstance().getScore());
            cname.appendChild(doc.createTextNode(move));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Arcade.xml"));
            transformer.transform(source, result);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void load() {

        try {
            File inputFile = new File("Arcade.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList score = doc.getElementsByTagName("score");
            System.out.println("----------------------------");
            Node nNode1 = score.item(0);
            System.out.println("ANAA SCORE : " + nNode1.getTextContent());
            highestscore = nNode1.getTextContent();
            Player.getInstance().setHighestscore(highestscore);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getbest() {
        return highestscore;
    }

    public void Reset() {
        this.time = 60;
        arr.clear();
        fillArray();
    }

}
