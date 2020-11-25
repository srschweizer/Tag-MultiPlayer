import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class TagView extends JPanel implements KeyListener, MouseMotionListener, ActionListener {// we get keypresses,
                                                                                                 // mouse positions, and
                                                                                                 // button clicks

    private boolean debug = false;
    private int lastKey = 0;
    private Graphics osg;
    private Image osi;
    private String lastButton = "";
    private int lastmx = 0;
    private int lastmy = 0;
    JButton stop;
    private Vector<Player> netPlayers;
    TagController control;


    // make the frame
    public TagView(Vector<Player> netPlayers, final int windowWidth, final int windowHeight,TagController control) {
        this.control = control;
        this.netPlayers = netPlayers;
        final JFrame window = new JFrame();
        window.setSize(new Dimension(windowWidth, windowHeight));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.requestFocus();
        window.setLayout(null);
        window.setResizable(false);
        // add all the event listeners
        window.addKeyListener(this);
        this.setSize(new Dimension(windowWidth, windowHeight - 100));
        window.getContentPane().add(this);
        this.addMouseMotionListener(this);

        // make the start button
        final JButton start = new JButton("Join");
        final Rectangle rstart = new Rectangle(windowWidth / 4 * 1, windowHeight - 90, 80, 40);
        start.setBounds(rstart);
        start.addActionListener(this);
        window.getContentPane().add(start);
        start.setFocusable(false);

        // make the stop button
        stop = new JButton("Leave");
        final Rectangle rstop = new Rectangle((windowWidth / 4) * 2, windowHeight - 90, 80, 40);
        stop.setBounds(rstop);
        stop.addActionListener(this);
        window.getContentPane().add(stop);
        stop.setFocusable(false);

        // make the reset button
        final JButton reset = new JButton("Exit");
        final Rectangle rreset = new Rectangle((windowWidth / 4) * 3, windowHeight - 90, 80, 40);
        reset.setBounds(rreset);
        reset.addActionListener(this);
        window.getContentPane().add(reset);
        reset.setFocusable(false);

        osi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        osg = osi.getGraphics();
        window.revalidate();

    }

    public int getLastKey() {
        return lastKey;
    }

    public int getLastX() {
        return lastmx;
    }

    public int getLastY() {
        return lastmy;
    }

    protected void paintComponent(Graphics g) {
     
                super.paintComponent(g);
                g.drawImage(osi, 0, 0, null);
                if(osg != null) osg.clearRect(0, 0, getWidth(), getHeight());

    }

    public void drawPlayer(Player p) {
        if (p.getIt() == true)
            osg.setColor(Color.red);
        else
            osg.setColor(Color.blue);

        osg.fillRect(p.getX(), p.getY(), 5, 5);
        if(p.getLocal())
        {
            int lineEndX = (int) (p.getX() - (10 * Math.sin(Math.toRadians(p.getHeading()))));
            int lineEndY = (int) (p.getY() - (10 * Math.cos(Math.toRadians(p.getHeading()))));
            osg.drawLine(p.getX(), p.getY(), lineEndX, lineEndY);
            osg.drawString("Heading: " + p.getHeading(), 10, 10);
            osg.drawString("Sin Heading: " + Math.sin(Math.toRadians(p.getHeading())), 10, 25);
            osg.drawString("Cos Heading: " + Math.cos(Math.toRadians(p.getHeading())), 10, 35);
        }

    }

    @Override
    public void mouseDragged(final MouseEvent e) {// called when the mouse position changes while click is held
     
    }
    @Override
    public void mouseMoved(final MouseEvent e) {// called when the mouse position changes without clicking
   
        lastmx = e.getX();
        lastmy = e.getY();

    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void keyPressed(final KeyEvent e) {// called when a key is pressed
        
            lastKey = e.getKeyCode();

    }

    @Override
    public void keyReleased(final KeyEvent e) {// take a guess
            lastKey = 0;

    }

    @Override
    public void actionPerformed(final ActionEvent e) {// gets called when an onscreen button is clicked

        if (e.getSource().toString().contains("Join"))
            lastButton = "Join";
        if (e.getSource().toString().contains("Leave"))
            lastButton = "Leave";
        if (e.getActionCommand().equals("Exit"))
            lastButton = "Exit";

    }

    public String getButton() {
        String ret = lastButton;
        lastButton = "";
        return ret;
    }

}
