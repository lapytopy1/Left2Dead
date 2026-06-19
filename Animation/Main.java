package Animation;

import game.Board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

import loader.LoadGame;

/**
 * Animation class for  the character
 * @author ross and friends
 *
 */
//U wot mate? stfu richard by djp
@SuppressWarnings("serial")
public class Main extends JFrame implements KeyListener{
	public static Random r = new Random();
	private ArrayList<Demon>spawnDemon= new ArrayList<Demon>();
	private Hero char1;
	private Board b = new Board(1,1);
	private MedDemon richard = new MedDemon(r.nextInt(500),r.nextInt(500));
	private BoardCanvas bc = new BoardCanvas();
	private static String currentDirection;
	private Color color = Color.white;
	//	private WeakDemon w = new WeakDemon(r.nextInt(500),r.nextInt(500));
	//	private MedDemon m = new MedDemon(r.nextInt(500),r.nextInt(500));
//	private BossDemon d = new BossDemon(r.nextInt(500),r.nextInt(500));


	/**
	 * The frame
	 */
	public Main() {
		add(bc);
		b.characters().add(char1);
		bc.repaint();
		char1 = LoadGame.createCharacter("mclovin",2,0,0);
		//char1.getImage("Down2");
		//MedDemon two = new MedDemon(100,100);
		//char1.setX(0);
		//char1.setY(0);
		b.characters().add(richard);
	//	LoadGame.createDemon("H", richard);
		//richard.getImage("Down2");
		spawnDemon.add(richard);
//		LoadGame.createDemon("M", two);
//		two.getImage("Down2");
//		spawnDemon.add(two);
//		LoadGame.createDemon("H", d);
//		d.getImage("Down2");
//		spawnDemon.add(d);
		//		LoadGame.createDemon("E", w);
		//		w.getImage("Down2");
		//		LoadGame.createDemon("M", m);
		//		m.getImage("Down2");
		//		LoadGame.createDemon("H", d);
		//		d.getImage("Down2");
		//		spawnDemon.add(w);
		//		spawnDemon.add(m);
		currentDirection = "down";
		addKeyListener(this);
		setSize(700, 700);
		setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
//		int key = e.getKeyCode();
//
//		if(key == KeyEvent.VK_LEFT){
//			currentDirection = "left";
//			if(!char1.isRed()){
//				if(char1.getPosition().equals("Left2")){
//					char1.getImage("Left1");
//					char1.setPosition("Left1"); //remember step before going stationary so hero can walk with left3
//					bc.repaint();
//				} else if(char1.getPosition().equals("Left1")){
//					char1.getImage("Left3");
//					char1.setPosition("Left3");
//					bc.repaint();
//				} else {
//					char1.getImage("Left2");
//					char1.setPosition("Left2");
//					bc.repaint();
//
//				}
//			} else{
//				if(char1.getPosition().equals("left2Hit")){
//					char1.getImage("left1Hit");
//					char1.setPosition("left1Hit"); //remember step before going stationary so hero can walk with left3
//					bc.repaint();
//				} else if(char1.getPosition().equals("left1Hit")){
//					char1.getImage("left3Hit");
//					char1.setPosition("left3Hit");
//					bc.repaint();
//				} else {
//					char1.getImage("left2Hit");
//					char1.setPosition("left2Hit");
//					bc.repaint();
//				}
//			}
//			char1.setX(char1.getX()-10);
//		}
//
//		if(key == KeyEvent.VK_UP){
//			currentDirection = "up";
//			if(!char1.isRed()){
//				if(char1.getPosition().equals("Up2")){
//					char1.getImage("Up1");
//					char1.setPosition("Up1");
//					bc.repaint();
//				} else if(char1.getPosition().equals("Up1")){
//					char1.getImage("Up3");
//					char1.setPosition("Up3");
//					bc.repaint();
//				} else {
//					char1.getImage("Up2");
//					char1.setPosition("Up2");
//					bc.repaint();
//
//				}
//			} else{
//				if(char1.getPosition().equals("up2Hit")){
//					char1.getImage("up1Hit");
//					char1.setPosition("up1Hit");
//					bc.repaint();
//				} else if(char1.getPosition().equals("up1Hit")){
//					char1.getImage("up3Hit");
//					char1.setPosition("up3Hit");
//					bc.repaint();
//				} else {
//					char1.getImage("up2Hit");
//					char1.setPosition("up2Hit");
//					bc.repaint();
//				}
//			}
//			char1.setY(char1.getY()-10);
//		}
//
//		if(key == KeyEvent.VK_RIGHT){
//			currentDirection = "right";
//			if(!char1.isRed()){
//				if(char1.getPosition().equals("Right2")){
//					char1.getImage("Right1");
//					char1.setPosition("Right1");
//					bc.repaint();
//				} else if(char1.getPosition().equals("Right1")){
//					char1.getImage("Right3");
//					char1.setPosition("Right3");
//					bc.repaint();
//				} else {
//					char1.getImage("Right2");
//					char1.setPosition("Right2");
//					bc.repaint();
//
//				}
//			} else{
//				if(char1.getPosition().equals("right2Hit")){
//					char1.getImage("right1Hit");
//					char1.setPosition("right1Hit");
//					bc.repaint();
//				} else if(char1.getPosition().equals("right1Hit")){
//					char1.getImage("right3Hit");
//					char1.setPosition("right3Hit");
//					bc.repaint();
//				} else {
//					char1.getImage("right2Hit");
//					char1.setPosition("right2Hit");
//					bc.repaint();
//				}
//			}
//			char1.setX(char1.getX()+10);
//		}
//
//		if(key == KeyEvent.VK_DOWN){
//			currentDirection = "down";
//			if(!char1.isRed()){
//				if(char1.getPosition().equals("Down2")){
//					char1.getImage("Down1");
//					char1.setPosition("Down1");
//					bc.repaint();
//				} else if(char1.getPosition().equals("Down1")){
//					char1.getImage("Down3");
//					char1.setPosition("Down3");
//					bc.repaint();
//				} else {
//					char1.getImage("Down2");
//					char1.setPosition("Down2");
//					bc.repaint();
//
//				}
//			} else{
//				if(char1.getPosition().equals("down2Hit")){
//					char1.getImage("down1Hit");
//					char1.setPosition("down1Hit");
//					bc.repaint();
//				} else if(char1.getPosition().equals("down1Hit")){
//					char1.getImage("down3Hit");
//					char1.setPosition("down3Hit");
//					bc.repaint();
//				} else {
//					char1.getImage("down2Hit");
//					char1.setPosition("down2Hit");
//					bc.repaint();
//				}
//			}
//			char1.setY(char1.getY()+10);
//		}
//		if(key == KeyEvent.VK_A){ //attack
//			if(char1.isRed()){
//				if(currentDirection.equals("left")){
//					char1.getImage("left2AHit");
//					bc.repaint();
//				} else if(currentDirection.equals("right")){
//					char1.getImage("right2AHit");
//					bc.repaint();
//				} else if(currentDirection.equals("up")){
//					char1.getImage("up2AHit");
//					bc.repaint();
//				} else if(currentDirection.equals("down")){
//					char1.getImage("down2AHit");
//					bc.repaint();
//				}
//			}
//			else{
//				if(currentDirection.equals("left")){
//					char1.getImage("left2Att");
//				} else if(currentDirection.equals("right")){
//					char1.getImage("right2Att");
//				} else if(currentDirection.equals("up")){
//					char1.getImage("up2Att");
//				} else if(currentDirection.equals("down")){
//					char1.getImage("down2Att");
//				}
//			}
//			for(Demon mon : spawnDemon){
//				if(char1.isCloseTo(mon)){ //need to iterate through the collection of zombies to find the closest one
//					if(mon.getHealth()>=0){
//						char1.attack();
//						mon.getImage("down2Hit");
//						mon.setHealth(mon.getHealth()-1);
//					}
//				}
//			}
//			bc.repaint();
//		}
//		if(key == KeyEvent.VK_ESCAPE){
//			System.out.println("Pause");
//		}
//		if(key == KeyEvent.VK_E){
//			System.out.println("Interact");
//		}
//		if(key == KeyEvent.VK_C){
//			System.out.println("Chat");
//		}
	}

	public void keyReleased(KeyEvent e) {
//		int key = e.getKeyCode();
//		if(key == KeyEvent.VK_LEFT){ //change to stationary
//			char1.getImage("Left2");
//			bc.repaint();
//
//		}
//		if(key == KeyEvent.VK_UP){
//			char1.getImage("Up2");
//			bc.repaint();
//		}
//		if(key == KeyEvent.VK_RIGHT){
//			char1.getImage("Right2");
//			bc.repaint();
//		}
//		if(key == KeyEvent.VK_DOWN){
//			char1.getImage("Down2");
//			bc.repaint();
//		}
//
//
//		if(key == KeyEvent.VK_A){
//
//			if(currentDirection.equals("left")){
//				char1.getImage("Left2");
//			} else if(currentDirection.equals("right")){
//				char1.getImage("Right2");
//			} else if(currentDirection.equals("up")){
//				char1.getImage("Up2");
//			} else if(currentDirection.equals("down")){
//				char1.getImage("Down2");
//			}
//
//			for(Demon mon:spawnDemon){
//				mon.getImage("Down2");
//
//			}
//			bc.repaint();
//		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public static void main(String[] args) {
		new Main();
	}

	public static String getCurrentDirection() {
		return currentDirection;
	}


	/**
	 * where the character is drawn
	 * @author ross and friends
	 *
	 */
	public class BoardCanvas extends JComponent{
		public synchronized void paint(Graphics g){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(color);
			g2d.fillRect(0,0, getWidth(), getHeight());
			//	w.draw(g2d);
			//	m.draw(g2d);
			char1.draw(g2d);
			char1.setRed(false);
			for(Characters c : b.characters()){
				if(c instanceof Demon){
					((Demon) c).draw(g2d);
					c.ping(b);
				}
			}
//			for(Demon d: spawnDemon){
//				d.draw(g2d);
//				d.ping(b);
//				if(d.isCloseTo(char1) && d.stateToString().equals("alive")){
//					d.attackPlayer(char1);
//					char1.setRed(true);
//				}
//			}

			char1.ping(b);
		}

	}
}
