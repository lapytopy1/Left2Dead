package Animation;
import game.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface DemonState {
	public void ping(Demon d,Board b);
	public void draw(Demon mon,Graphics g,BufferedImage bimg);
	public void attackPlayer(Hero c,int damage);
	public String stateToString();
	public void attack(Demon h);
	public void moveLeft(Demon h);
	public void moveRight(Demon h);
	public void moveUp(Demon h);
	public void moveDown(Demon h);
}
