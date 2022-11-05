package fruitninja;

public class Factory {
    
    public GameObject getGameObject(String oname)
    {
        if(oname.equals("apple"))
            return new Apple();
        if(oname.equals("orange"))
            return new Orange();
        if(oname.equals("banana"))
            return new Banana();
        if(oname.equals("watermelon"))
            return new Watermelon();
        if(oname.equals("kiwi"))
            return new Kiwi();
        if(oname.equals("fatalBomb"))
            return new FatalBomb();
        if(oname.equals("dangerousBomb"))
            return new DangerousBomb();
        return null;
    }
}
