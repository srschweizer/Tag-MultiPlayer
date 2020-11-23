import java.awt.*;

public class Player {
    private int xLoc = 0;
    private int yLoc = 0;
    private double heading = 0;
    private boolean it = false;
    private boolean comp = false;
    private int id = 0;

    private int speed = 2;
    
    public Player(int id, int initialX, int initialY, boolean initialIt) {

        this.xLoc = initialX;
        this.yLoc = initialY;
        this.id = id;

        setIt(initialIt);

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

    public boolean getIt() {
        return it;
    }

    public boolean setIt(Boolean it) {
        this.it = it;
        if (it)
            speed = 10;
        else
            speed = 30;
        return it;
    }

    public void reset() {
        xLoc = (int) (Math.random() * 400);
        yLoc = (int) (Math.random() * 400);
        System.out.println(" new Xloc = " + xLoc);
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

    public boolean getComp() {
        return comp;
    }
    public void doHumanMove(int mx, int my)
    {
        setHeading(mx, my);
        //olny move forward if more than one step away from mouse
        if((Math.abs(xLoc - mx)> speed) || (Math.abs(yLoc - my)> speed)){
            moveFwd();
        }
        
    }

    public void doComputerMove(Player otherPlayer) {
        comp = true;
        if (it)
            runTo(otherPlayer);
        else
            runAwayFrom(otherPlayer);
    }

    public void runAwayFrom(Player p) {

         //if close to wall add random ammount to heading
         //if not close to player add random amount to heading
         //if close to player go directly away
        if((Math.abs(xLoc - p.getX())> speed) || (Math.abs(yLoc - p.getY())> speed)){
            setHeading(p.getX(), p.getY());
            heading *= -1; //make the hrading run away
           //ystem.out.println(xLoc + " to " +p.getX() );


            moveFwd();
        }
    }

    public void runTo(Player p) {
        if((Math.abs(xLoc - p.getX())> speed) || (Math.abs(yLoc - p.getY())> speed)){
            setHeading(p.getX(), p.getY());
           // System.out.println(xLoc + " to " +p.getX() );
            moveFwd();
        }

    }
    public void checkBounds(int minX, int minY,int maxX, int maxY)
    {
        if(xLoc > maxX) xLoc = maxX;
        if(yLoc > maxY) yLoc = maxY;
        if(xLoc < minX) xLoc = minX;
        if(yLoc < minY) yLoc = minY;
    }
    
    

}
