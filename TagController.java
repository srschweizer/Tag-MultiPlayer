import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class TagController extends TimerTask {

    private boolean debug = true;// prints debug info when true
    private TagView view;
    private Timer timer;
    private final int timerMS = 32;
    private final int frameWidth = 900;
    private final int frameHeight = 600;
    private final int frameWallOffset = 50;
    private boolean gameRunning = false;
    private Player human = new Player(888, 300, 300, false);
    // private Player comp = new Player(debug, 400, 400, true);
    private Vector<Player> netPlayers = new Vector<Player>();
    private NetClient network;

    public static void main(String[] args) {// constructs an instance of TagController
        TagController theGame = new TagController();
    }

    public TagController() {

        timer = new Timer();
        view = new TagView(debug, frameWidth, frameHeight);
        timer.schedule(this, 0, timerMS);
        network = new NetClient(netPlayers, human, 2000, view);
        Thread netWorker = new Thread(network);
        netWorker.start();

    }

    public void getEvents() {

        int key = view.getLastKey();
        String button = view.getButton();

        if (key == 32) {
            human.doHumanMove(view.getLastX(), view.getLastY());
            
        }
        if (button.equals("Start")) {
            gameRunning = true;
            network.join(human);
        }
        if (button.equals("Stop")) {
            gameRunning = false;
        }
        if (button.equals("Reset")) {
           

        }

    }

    /*
     * public void checkTag() { if (Math.abs(human.getX() - comp.getX()) < 10) if
     * (Math.abs(human.getY() - comp.getY()) < 10) { if (human.getIt()) {
     * human.setIt(false); comp.setIt(true); } else { human.setIt(true);
     * comp.setIt(false); } } }
     */
    public void run() {// called every tick

        getEvents();
        if (gameRunning) {

            view.repaint();
           
        }

    }
}