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
    private boolean gameRunning = true;
    private Player human = new Player(888, 300, 300, false);
    // private Player comp = new Player(debug, 400, 400, true);
    private Vector<Player> netPlayers = new Vector<Player>();
    private NetClient network;
    private boolean networkBusy = false;

    public static void main(String[] args) {// constructs an instance of TagController
        TagController theGame = new TagController();
    }

    public TagController() {

        human = new Player();
        human.setLocal(true);
        timer = new Timer();
        view = new TagView(netPlayers, frameWidth, frameHeight,this);
        timer.schedule(this, 0, timerMS);
        network = new NetClient(netPlayers, human, 2000, this);
        Thread netWorker = new Thread(network);
        netWorker.start();
       

    }
    public void setNetworkBusy(boolean busy){ networkBusy = busy;}
    public boolean getNetworkBusy() { return networkBusy;}

    public void getEvents() {

        int key = view.getLastKey();
        String button = view.getButton();

        if (key == 32) {
            human.doHumanMove(view.getLastX(), view.getLastY());
            
        }
        if (button.equals("Join")) {
            network.join(human);
        }
        if (button.equals("Leave")) {
            //make some thing happen on the server
        }
        if (button.equals("Exit")) {
           System.exit(0);

        }

    }

    public void run() {

        getEvents();
        if (gameRunning) {
            view.drawPlayer(human);
            if(!getNetworkBusy()) for(Player p : netPlayers) view.drawPlayer(p);
            view.repaint();
           
        }

    }
}