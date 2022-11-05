package GUI;

import Controller.GameController;
import fruitninja.DangerousBomb;
import fruitninja.FatalBomb;
import fruitninja.GameObject;
import fruitninja.Player;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
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

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    GameController controller = GameController.getInstance();
    String score;
    int check;
    int a = 0;
    String highestscore;
    ArrayList<GameObject> arr = new ArrayList<>();
    Timer t = new Timer(5, this);
    boolean view = true;
    int time = 0;
    int live = 3;

    int rn = 3;
    int b = Player.getInstance().getScore();

    public GamePanel() {
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
        if (live > 0) {
            int X = 200;
            int starty = 600;
            Random r = new Random();
            int c = r.nextInt(3);
            if (Player.getInstance().getScore() < 50) {
                rn = 3;
            }
            if (Player.getInstance().getScore() >= 50 && Player.getInstance().getScore() <= 100) {
                rn = 7;
            }
            if (Player.getInstance().getScore() >= 100) {

                rn = 10;
            }
            int m = c + rn;
            //System.out.println("rn = "+rn+" Random number = "+c+" Total = "+m);
            for (int i = 0; i < m; i++) {

                GameObject go = controller.createGameObject();
                go.setXLocation((int) X);
                go.setYLocation(starty);
                X = (int) (Math.random() * 500);
                arr.add(go);
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (live > 0) {
            time++;
           /* if (time % 2 == 0) {
                repaint();
            }*/
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

                    arr.get(i).setXLocation(x);
                    arr.get(i).setYLocation(y);

                    if (y < arr.get(i).getMaxHeight()) {
                        arr.get(i).setMovingup(false);
                    }

                } else if (!arr.get(i).isMovingUp()) {
                    int y = arr.get(i).getYlocation();
                    int x = arr.get(i).getXlocation();

                    if (arr.get(i).isSliced()) {
                        y = y /*+ 300*/ + arr.get(i).getFallingVelocity();
                    } else {
                        y += arr.get(i).getFallingVelocity();
                    }
                    x += 10;
                    arr.get(i).setYLocation(y);
                    arr.get(i).setXLocation(x);
                    GameObject gc = arr.get(i);

                    if (arr.get(i).getYlocation() == 700 || arr.get(i).getYlocation() == 710 || arr.get(i).getYlocation() == 720) {
                        if (!arr.get(i).isSliced() && live > 0) {
                            live--;
                            Player.getInstance().setLive(1);
                            gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\notslice.wav");

                        }
                    }
                }
               // repaint();
            }
            if (live == 0) {
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

        if (time % 30 == 0) {
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
                    if (!gc.isSliced()) {
                        gc.slice();
                        gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Critical.wav");
                        Player.getInstance().setScore(gc.getScore());

                        if (gc.name().equals("fatal") || live == 0) {
                            if (gc.name().equals("fatal")) {
                                gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Bomb.wav");
                            }
                            if (highestscore != null) {
                                if (Player.getInstance().getScore() > Integer.parseInt(highestscore)) {
                                    save();
                                }
                                load();
                                // live=0;
                            }
                            live = 0;
                            Gameover over = new Gameover();
                            over.setVisible(true);
                            Player.getInstance().setScore(0);
                            Player.getInstance().setTime(time);
                        }
                    }

                    // Player.getInstance().setScore(10);
                    if (gc.isSliced() && gc.name().equals("bomb")) {
                        Player.getInstance().setLive(1);
                        live--;
                        gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Bomb.wav");

                        if (live == 0) {
                            Gameover over = new Gameover();
                            over.setVisible(true);
                            Player.getInstance().setScore(0);
                            Player.getInstance().setTime(time);
                        }
                    }
                }
            }
            //System.out.println("hi there");
        }
        //  save();
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
                    if (!gc.isSliced()) {
                        gc.slice();
                        gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Critical.wav");
                        Player.getInstance().setScore(gc.getScore());

                        if (gc.name().equals("fatal") || live == 0) {
                            if (gc.name().equals("fatal")) {
                                gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Bomb.wav");
                            }
                            if (highestscore != null) {
                                if (Player.getInstance().getScore() > Integer.parseInt(highestscore)) {
                                    save();
                                }
                                load();
                                // live=0;
                            }
                            live = 0;
                            Gameover over = new Gameover();
                            over.setVisible(true);
                            Player.getInstance().setScore(0);
                            Player.getInstance().setTime(time);
                        }
                    }

                    // Player.getInstance().setScore(10);
                    if (gc.isSliced() && gc.name().equals("bomb")) {
                        Player.getInstance().setLive(1);
                        live--;
                        gc.playMusic("C:\\Users\\mariam\\Desktop\\FruitNinja Last\\FruitNinja (3)\\FruitNinja\\Bomb.wav");

                        if (live == 0) {
                            Gameover over = new Gameover();
                            over.setVisible(true);
                            Player.getInstance().setScore(0);
                            Player.getInstance().setTime(time);
                        }
                    }
                }
            }
            //System.out.println("hi there");
        }
        //  save();
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
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    public int getTime() {
        return time;
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
            String x = getscore();
            Score.appendChild(doc.createTextNode(x));

            Element cname = doc.createElement("score");

            String move = getscore();
            cname.appendChild(doc.createTextNode(move));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("FruitNinja.xml"));
            transformer.transform(source, result);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setscore(String score) {
        this.score = score;
    }

    public String getscore() {
        return score;
    }

    public void load() {

        try {
            File inputFile = new File("FruitNinja.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //     System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList score = doc.getElementsByTagName("score");
            //       System.out.println("----------------------------");
            Node nNode1 = score.item(0);
            //        System.out.println("ANAA SCORE : " +nNode1.getTextContent());
            highestscore = nNode1.getTextContent();
            Player.getInstance().setHighestscore(highestscore);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setcheck(int check) {
        this.check = check;
    }

    public int edit() {
        if (highestscore != null) {
            if (Player.getInstance().getScore() > Integer.parseInt(highestscore)) {
                return Player.getInstance().getScore();
            }
        }
        return 0;
    }

    public String gethighestscore() {
        //  if(Player.getInstance().getScore()>Integer.parseInt(highestscore))
        //    highestscore=""+Player.getInstance().getScore();
        return highestscore;
    }

    public void Reset() {
        this.time = 0;
        arr.clear();
        fillArray();
    }
}
