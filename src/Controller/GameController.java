package Controller;

import fruitninja.Factory;
import fruitninja.GameActions;
import fruitninja.GameObject;
import java.util.Random;

public class GameController implements GameActions{
    
    private static GameController instance = null;
    
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    Factory f = new Factory();
    
    @Override
    public GameObject createGameObject() {
        Random r = new Random();
        int m = r.nextInt(7);
        if (m == 0) {
            return f.getGameObject("apple");
        }if (m == 1) {
            return f.getGameObject("banana");
        }if (m == 2) {
            return f.getGameObject("kiwi");
        }if (m == 3) {
            return f.getGameObject("watermelon");
        }if (m == 4) {
            return f.getGameObject("fatalBomb");
        }if (m == 5) {
            return f.getGameObject("dangerousBomb");
        } else {
            return f.getGameObject("orange");
        }
    }

    @Override
    public void updateObjectLocation() {
        
    }

    @Override
    public void sliceObjects() {
        
    }

    @Override
    public void saveGame() {
        
    }

    @Override
    public void loadGame() {
        
    }

    @Override
    public void ResetGame() {
        
    }
    
}
