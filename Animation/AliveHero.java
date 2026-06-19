package Animation;

import game.Board;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * This class contains the behavior of an alive character
 *
 * @author ross
 *
 */
public class AliveHero implements HeroState,Serializable {
	private static final long serialVersionUID = 3226829422678709033L;
	private final int width = 32;
	private final int height = 32;
	private int health = 100;


	public void draw(Hero c, Graphics g,BufferedImage b) {
		g.drawImage(b,c.getCoordinate().getX(),c.getCoordinate().getY(), width, height, null);

	}

	public void ping(Hero c,Board b) {
		if (c.getHealth() <= 0) {
			c.setState(new DeadHero(c.getCoordinate().getX(), c.getCoordinate().getY(), c.getUid()));
		}

		if(findClosestDemon(b,c)!=null){findClosestDemon(b,c).attackPlayer(c);} //player is attacked by closest demon
		if(findClosestDemon(b,c)==null){c.setRed(false);}
		//	c.setRed(false);
		//		for(Characters d : b.characters()){ //player is attacked when close to any demon
		//			if(d instanceof Demon){
		//				if(((Demon) d).isCloseTo(c)){
		//					//	c.setRed(true);
		//					((Demon) d).attackPlayer(c);
		//
		//				}
		//				if(!((Demon) d).isCloseTo(c)){
		//					c.setRed(false);
		//				}
		////				if(!((Demon) d).isCloseTo(c)){
		////						c.setRed(false);
		////					//((Demon) d).attackPlayer(c);
		////				}
		//
		//			}
		//		}
		//this.draw(this, g, b);
	}

	/**
	 * return the demon currently attacking the player
	 * @param b
	 * @param c
	 * @return
	 */
	public Demon findClosestDemon(Board b,Hero c){
		for(Characters d : b.characters()){
			if(d instanceof Demon){
				if(((Demon) d).isCloseTo(c)){
					return (Demon) d;
				}
			}
		}
		return null;
	}

	/**
	 * damage the demon
	 */
	public void hit(Hero c, Demon demon) {
		demon.setHealth(demon.getHealth()-10);
	}

	public void playerRegenerating(Hero c) {
		if (c.getHealth() < 100) {
			c.setHealth(this.health++);
		}
	}
	/**
	 * move left animation
	 */
	public void moveLeft(Hero h) {
		if(!h.isRed()){ //the hero is not hurt
			if(h.getPosition().equals("Left2")){
				h.getImage("Left1");
				h.setPosition("Left1");
			} else if (h.getPosition().equals("Left1")) {
				h.getImage("Left3");
				h.setPosition("Left3");
			} else {
				h.getImage("Left2");
				h.setPosition("Left2");
			}
		} else { //the hero is hurt
			if (h.getPosition().equals("left2Hit")) {
				h.getImage("left1Hit");
				h.setPosition("left1Hit");
			} else if (h.getPosition().equals("left1Hit")) {
				h.getImage("left3Hit");
				h.setPosition("left3Hit");
			} else {
				h.getImage("left2Hit");
				h.setPosition("left2Hit");
			}
		}
		h.getCoordinate().moveLeft();
	}

	/**
	 * move right animation
	 */
	public void moveRight(Hero h) {
		if(!h.isRed()){
			if (h.getPosition().equals("Right2")) {
				h.getImage("Right1");
				h.setPosition("Right1");
			} else if (h.getPosition().equals("Right1")) {
				h.getImage("Right3");
				h.setPosition("Right3");
			} else {
				h.getImage("Right2");
				h.getImage("Right2");
				h.setPosition("Right2");
			}
		} else{
			if (h.getPosition().equals("right2Hit")) {
				h.getImage("right1Hit");
				h.setPosition("right1Hit");
			} else if (h.getPosition().equals("right1Hit")) {
				h.getImage("right3Hit");
				h.setPosition("right3Hit");
			} else {
				h.getImage("right2Hit");
				h.setPosition("right2Hit");
			}
		}
		h.getCoordinate().moveRight();
	}

	/**
	 * move up animation
	 */
	public void moveUp(Hero h) {

		if (!h.isRed()) {
			if (h.getPosition().equals("Up2")) {
				h.getImage("Up1");
				h.setPosition("Up1");
			} else if (h.getPosition().equals("Up1")) {
				h.getImage("Up3");
				h.setPosition("Up3");
			} else {
				h.getImage("Up2");
				h.setPosition("Up2");
			}
		} else{
			if (h.getPosition().equals("up2Hit")) {
				h.getImage("up1Hit");
				h.setPosition("up1Hit");
			}  else if(h.getPosition().equals("up1Hit")) {
				h.getImage("up3Hit");
				h.setPosition("up3Hit");
			} else {
				h.getImage("up2Hit");
				h.setPosition("up2Hit");
			}
		}
		h.getCoordinate().moveUp();
	}

	/**
	 * move down animation
	 */
	public void moveDown(Hero h) {
		if (!h.isRed()) {
			if (h.getPosition().equals("Down2")) {
				h.getImage("Down1");
				h.setPosition("Down1");
			} else if (h.getPosition().equals("Down1")) {
				h.getImage("Down3");
				h.setPosition("Down3");
			} else {
				h.getImage("Down2");
				h.setPosition("Down2");
			}
		} else{
			if(h.getPosition().equals("down2Hit")){
				h.getImage("down1Hit");
				h.setPosition("down1Hit");
			} else if(h.getPosition().equals("down1Hit")){
				h.getImage("down1Hit");
				h.setPosition("down1Hit");;
			} else{
				h.getImage("down2Hit");
				h.setPosition("down2Hit");
			}
		}
		h.getCoordinate().moveDown();
	}

	/**
	 * attack animation
	 */
	public void attack(Hero c) {
		if(c.isRed()){
			if (c.getCurrentDirection().equals("left")) {
				c.getImage("left2AHit");
			} else if (c.getCurrentDirection().equals("right")) {
				c.getImage("right2AHit");
			} else if (c.getCurrentDirection().equals("up")) {
				c.getImage("up2AHit");
			} else if (c.getCurrentDirection().equals("down")) {
				c.getImage("down2AHit");
			}
		} else{
			if (c.getCurrentDirection().equals("left")) {
				c.getImage("left2Att");
			} else if (c.getCurrentDirection().equals("right")) {
				c.getImage("right2Att");
			} else if (c.getCurrentDirection().equals("up")) {
				c.getImage("up2Att");
			} else if (c.getCurrentDirection().equals("down")) {
				c.getImage("down2Att");
			}
		}
	}
}
