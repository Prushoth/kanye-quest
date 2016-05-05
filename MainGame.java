import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.MouseInfo;

public class MainGame extends JFrame implements ActionListener{
	private javax.swing.Timer gameTimer;
	private KanyePanel game;
	public MainGame(){
		super("Kanye Quest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500,1000);
		gameTimer = new javax.swing.Timer(10, this);
		game = new KanyePanel(this);
		add(game);
		setResizable(false);
        setVisible(true);
	}

	public void start(){
		gameTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.repaint();
		game.move();
	}

	public static void main(String[] args){
		MainGame frame = new MainGame();
	}
}


class KanyePanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener{
	private boolean[] keys;
    private int[] offset; //offset of map for scrolling
	private  ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	private MainGame mainFrame;
	private Kanye player;
	private int mx , my;
	private int bullcounter;
	private double ang;
    private Image back, bulpic, map;

	public KanyePanel(MainGame m){
		mainFrame = m;
		keys = new boolean[KeyEvent.KEY_LAST+1];
		addKeyListener(this);
        addMouseMotionListener(this);
		addMouseListener(this);
		setSize(1500, 1000);
		player = new Kanye();
        back = new ImageIcon("back.png").getImage();
        bulpic = new ImageIcon("bulletpic.png").getImage();
        map = new ImageIcon("map.jpg").getImage();
        offset = new int[]{0, 0};

	}

	public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	public void mouseReleased(MouseEvent e){
		player.setShooting(false);
	}
	public void mousePressed(MouseEvent e){
        System.out.println("clicked");
		player.setShooting(true);
	}
    public void mouseDragged(MouseEvent e){
		mx = e.getX();
		my = e.getY();
		ang = Math.atan2(my - player.getY(), mx - player.getX());
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		ang = Math.atan2(my - player.getY(), mx - player.getX());

	}


    public void move(){
        if (keys[KeyEvent.VK_D]){ //strafe right
            if(player.getX() <= 1400){
                player.move(5, 0);
            }
            else if(offset[0] >= -4000) {
                offset[0] -= 5;
            }
        }
        if (keys[KeyEvent.VK_A]){ //strafe left
            if(player.getX() >= 40){
                player.move(-5, 0);
            }
            else if(offset[0] < 0) {
                offset[0] += 5;
            }
        }
        if (keys[KeyEvent.VK_W]){ //move UP
            if(player.getY() >= 25){
                player.move(0, -5);
            }
            else if(offset[1] < 0) {
                offset[1] += 5;
            }
        }
        if (keys[KeyEvent.VK_S]){ //move DOWN
            if(player.getY() <= 900){
                player.move(0, 5);
            }
            else if(offset[1] >= -4000) {
                offset[1] -= 5;
            }
        }

    	/* MOVE RELATIVE TO THE MOUSE POSITION
    	if (keys[KeyEvent.VK_D]){ //strafe right
			player.move((5*(Math.cos(ang + Math.toRadians(90)))),(5*(Math.sin(ang + Math.toRadians(90))))); //direction of movement calculated by cos and sin of angle to mouse
    	}
    	if (keys[KeyEvent.VK_A]){ //strafe left
			player.move(((5*(Math.cos(ang - Math.toRadians(90))))),(5*(Math.sin(ang - Math.toRadians(90)))));
    	}
    	if (keys[KeyEvent.VK_W]){ //move forward
    		player.move((5*Math.cos(ang)),(5*Math.sin(ang)));
    	}
    	if (keys[KeyEvent.VK_S]){ //move backwards
			player.move((-5*Math.cos(ang)), (-5*Math.sin(ang)));
    	}*/
		updateGame();
    }

	public void updateGame() {
		if(player.isShooting()){
			if(bullcounter >= 25) {
				bullets.add(new Bullet(player.getX() + 10, player.getY() + 40, ang));
				bullcounter = 0;
			}
			
		}

		Iterator<Bullet> buliter = bullets.iterator();
		while(buliter.hasNext()) {
			Bullet b = buliter.next();
			b.move();
			if (b.getX() > 1500 || b.getX() < 0 || b.getY() > 1000 || b.getY() < 0) {
				buliter.remove();
			}
		}
		bullcounter ++;
	}
    public void paintComponent(Graphics g){
        g.drawImage(map, 0 + offset[0], 0 + offset[1], this);
    	g.setColor(Color.green);
		g.fillOval((int)Math.round(player.getX()), (int)Math.round(player.getY()), 80, 80);
		g.drawLine((int)Math.round(player.getX()) + 10, (int)Math.round(player.getY()) + 40, (int)Math.round(player.getX()) + 40 + (int)(100 * Math.cos(ang)) , (int)Math.round(player.getY()) + 40 + (int)(100.0*Math.sin(ang)) );

        g.setColor(Color.red);
        for(Bullet b : bullets){
			g.drawImage(bulpic, (int)Math.round(b.getX()), (int)Math.round(b.getY()), this);
		}
	}

}