package Animation;

import game.Board;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * this class contains the behavior of a dead character
 * @author ross
 *
 */
public class DeadHero implements HeroState  {
	private int x;
	private int y;
	private final int width = 32;
	private final int height = 32;
	BufferedImage deadHero;

	public DeadHero(int x,int y,int uid){
		this.setX(x); //coordinates of the dead character
		this.setY(y);
		//create dead hero bufferedImage(not final)
		try {
			deadHero = ImageIO.read(new File("data/Character/Players/Player"+uid+"/Player"+uid+"D.png"));
		} catch (IOException e) {
			System.out.println("cannot find dead hero");
		}
	}

	public void draw(Hero c, Graphics g, BufferedImage b) {
		g.drawImage(deadHero,x,y, width, height, null);
	}

	//do nothing!
	public void ping(Hero c,Board b){}
	public void hit(Hero c, Demon demon){}
	public void playerRegenerating(Hero c){}
	public int getX(){return x;}
	public void setX(int x){this.x = x;}
	public int getY(){return y;}
	public void setY(int y){this.y = y;}
	public void moveLeft(Hero h){}
	public void moveRight(Hero h){}
	public void moveUp(Hero h){}
	public void moveDown(Hero h){}
	public void attack(Hero c){}
	public Demon findClosestDemon(Board b, Hero c){return null;}
}
