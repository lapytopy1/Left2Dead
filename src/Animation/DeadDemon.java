package Animation;

import game.Board;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Behavior of a dead demon
 * does nothing
 * @author ross
 *
 */
public class DeadDemon implements DemonState {
	private int x;
	private int y;
	private final int width = 32;
	private final int height = 32;
	private BufferedImage dead;

	public DeadDemon(int x, int y,String type) {
		this.x = x;
		this.y = y;
		try {
			if(type.equals("weak")){dead = ImageIO.read(new File("data/Character/demons/EasyDD.png"));}
			if(type.equals("med")){dead = ImageIO.read(new File("data/Character/demons/MedDD.png"));}
			if(type.equals("boss")){dead = ImageIO.read(new File("data/Character/demons/HardDD.png"));}
		} catch (IOException e) {
			System.out.println("cannot find dead hero");
		}
	}

	public void draw(Demon mon, Graphics g, BufferedImage bimg) {
		g.drawImage(dead,x,y, width, height, null);
	}
	//disable demon stuff
	public String stateToString(){return "dead";}
	public void attackPlayer(Hero c,int damage){}
	public void ping(Demon d,Board b) {}
	public void moveLeft(Demon h){}
	public void moveRight(Demon h){}
	public void moveUp(Demon h){}
	public void moveDown(Demon h){}
	public void attack(Demon h){}
}
