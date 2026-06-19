package Animation;
import game.Board;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * interface that contains methods for
 * whatever state the character is in
 * dead or alive
 * @author ross and friends
 *
 */
public interface HeroState {
	public void ping(Hero c,Board b);
	public void draw(Hero c, Graphics g,BufferedImage b);
	public void hit(Hero c,Demon demon);
	public void attack(Hero c);
	public void playerRegenerating(Hero c);
	public void moveLeft(Hero h);
	public void moveRight(Hero h);
	public void moveUp(Hero h);
	public void moveDown(Hero h);
	public Demon findClosestDemon(Board b,Hero c);
}
