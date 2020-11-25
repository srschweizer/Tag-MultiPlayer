import java.awt.*;

public class Player {
    private int xLoc = 0;
    private int yLoc = 0;
    private double heading = 0;
    private boolean it = false;
    private boolean local = false;
    private int id = 0;

    private int speed = 2;
    
    public Player(int id, int initialX, int initialY, boolean initialIt) {

        this.xLoc = initialX;
        this.yLoc = initialY;
        this.id = id;

        setIt(initialIt);

    }
    public Player() {

        this.xLoc = (int)(Math.random()*800);
        this.yLoc = (int)(Math.random()*600);
        this.id = (int)(Math.random()*99999);
        setIt(false);

    }
    public String toString(){
        return "Player:"+id+"@"+xLoc+","+yLoc+":It="+it;

    }
    public int getId() {
        return id;
    }
    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }
    public boolean getLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }
    public boolean getIt() {
        return it;
    }

    public void setIt(Boolean it) {
        this.it = it;
    }

    public double getHeading() {
        //return the insatnce var for heading
        return heading;
    }

    public void setHeading(int x, int y) {

        //find the angle between my location and some other point xy 
        //can be used to follow mouse xy or the xy of another player
        double dx = xLoc - x;  
        double dy = -(yLoc - y);
        double inRads = Math.atan2(dy, dx);
        inRads += Math.PI / 2;
        heading = Math.toDegrees(inRads);
        
    }

    public void moveFwd() {

       //move forward along the line defined by heading
        xLoc = (int) (getX() - (speed * Math.sin(Math.toRadians(getHeading()))));
        yLoc = (int) (getY() - (speed * Math.cos(Math.toRadians(getHeading()))));
    }
    public void doHumanMove(int mx, int my)
    {
        setHeading(mx, my);
        //olny move forward if more than one step away from mouse
        if((Math.abs(xLoc - mx)> speed) || (Math.abs(yLoc - my)> speed)){
            moveFwd();
        }
        
    }  

}
