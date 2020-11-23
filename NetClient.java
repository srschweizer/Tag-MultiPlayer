import java.util.*;
import java.io.*;
import java.net.*;

public class NetClient implements Runnable {

    private int sleepTime = 0;
    private Vector<Player> netPlayers;
    private int myId;
    private Player human;
    TagView view;

    public NetClient(Vector<Player> netPlayers, Player human, int sleepTime, TagView view) {
        this.sleepTime = sleepTime;
        this.netPlayers = netPlayers;
        this.human = human;
        myId = (int) (Math.random() * 10000);
        netPlayers.add(human);
        this.view = view;
    }

    public void run() {
        try {
            while (true) {
                updateNetPlayers();
                Thread.sleep(sleepTime);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ;
        }
    }

    public void updateNetPlayers() {

        move(human.getX(), human.getY());
        System.out.println(requestId(myId));
        netPlayers.clear();
        for (int count = 0; count < getNetPlayerCount(); count++) {

            view.drawPlayer(requestId((getIdAtIndex(count))));

        }

    }

    public int getNetPlayerCount() {
        int ret = 0;

        String cmd = "idcount";
        String result = sendRecieve(cmd);
        try {
            StringTokenizer st = new StringTokenizer(result, ":");
            if (st.countTokens() == 2) {
                String ack = st.nextToken();
                ret = Integer.valueOf(st.nextToken());

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ret;
    }

    public int getIdAtIndex(int index) {
        int ret = 0;

        String cmd = "index:" + index;
        String result = sendRecieve(cmd);
        try {
            StringTokenizer st = new StringTokenizer(result, ":");
            if (st.countTokens() == 2) {
                String ack = st.nextToken();
                ret = Integer.valueOf(st.nextToken());

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ret;
    }

    public Player requestId(int id) {
        Player ret = null;

        String cmd = "request:" + id;
        String result = sendRecieve(cmd);
        try {
            StringTokenizer st = new StringTokenizer(result, ":");
            if (st.countTokens() == 5) {
                String ack = st.nextToken();
                int remoteid = Integer.valueOf(st.nextToken());
                int x = Integer.valueOf(st.nextToken());
                int y = Integer.valueOf(st.nextToken());
                boolean it = Boolean.valueOf(st.nextToken());
                ret = new Player(id, x, y, it);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ret;
    }

    public boolean join(Player p) {
        boolean ret = false;

        String cmd = "join:" + myId + ":" + p.getX() + ":" + p.getY();
        if (sendRecieve(cmd).equals("ok")) {
            ret = true;
        } else
            ret = false;

        return ret;

    }

    public boolean move(int x, int y) {
        boolean ret = false;
        String cmd = "move:" + myId + ":" + x + ":" + y;
        if (sendRecieve(cmd).equals("ok"))
            ret = true;
        return ret;
    }

    public String sendRecieve(String send) {
        String ret = "notok";
        try {

            System.out.println("Client: " + send);
            Socket s = new Socket("67.135.220.132", 80);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            out.write(send + "\n");
            out.flush();
            String received = in.readLine();
            System.out.println("Server: " + received);
            ret = received;
            s.close();

        } catch (Exception e) {
            System.err.println(e);
        }
        return ret;
    }
}