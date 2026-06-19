package Animation;

import game.Board;
import game.GameCoordinate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import loader.LoadGame;
import loader.Item;

/**
 * The hero is a player controlled character it needs to kill demons before it
 * gets killed by it it can pick up items etc.
 * 
 * @author ross and friends
 * 
 */
public class Hero implements Characters, Serializable {
	private static final long serialVersionUID = -9073509488856846311L;
	@SuppressWarnings("unused")
	private String name;
	private String position = "Down2";
	private BufferedImage img;
	private String currentDirection = "down";
	private int health = 10000;
	private boolean dead = false;
	private HeroState currentState = new AliveHero();
	private GameCoordinate pos;
	public BufferedImage up1;
	public BufferedImage up2;
	public BufferedImage up3;
	public BufferedImage down1;
	public BufferedImage down2;
	public BufferedImage down3;
	public BufferedImage right1;
	public BufferedImage right2;
	public BufferedImage right3;
	public BufferedImage left1;
	public BufferedImage left2;
	public BufferedImage left3;
	public BufferedImage up1Att;
	public BufferedImage up2Att;
	public BufferedImage up3Att;
	public BufferedImage down1Att;
	public BufferedImage down2Att;
	public BufferedImage down3Att;
	public BufferedImage right1Att;
	public BufferedImage right2Att;
	public BufferedImage right3Att;
	public BufferedImage left1Att;
	public BufferedImage left2Att;
	public BufferedImage left3Att;
	public BufferedImage up1Hit;
	public BufferedImage up2Hit;
	public BufferedImage up3Hit;
	public BufferedImage down1Hit;
	public BufferedImage down2Hit;
	public BufferedImage down3Hit;
	public BufferedImage right1Hit;
	public BufferedImage right2Hit;
	public BufferedImage right3Hit;
	public BufferedImage left1Hit;
	public BufferedImage left2Hit;
	public BufferedImage left3Hit;
	public BufferedImage up1AHit;
	public BufferedImage up2AHit;
	public BufferedImage up3AHit;
	public BufferedImage down1AHit;
	public BufferedImage down2AHit;
	public BufferedImage down3AHit;
	public BufferedImage right1AHit;
	public BufferedImage right2AHit;
	public BufferedImage right3AHit;
	public BufferedImage left1AHit;
	public BufferedImage left2AHit;
	public BufferedImage left3AHit;
	private List<Characters> chars = new ArrayList<Characters>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private boolean red = false;
	private int uid;
	private int selected = 0;
	private boolean clearing = false;
	private static boolean canEvent = false;

	public Hero(String name, int uid, int x, int y) {
		img = down2;
		this.name = name;
		this.setUid(uid);
		pos = new GameCoordinate(x, y, name);
		this.getImage("Down2");
		// items =
	}

	public void nextItem() {
		if ((selected + 1) == items.size()) {
			selected = 0;
		} else {
			selected++;
		}
	}

	public void prevItem() {
		if ((selected - 1) == -1) {
			selected = items.size() - 1;
		} else {
			selected--;
		}
	}

	public void addItem(Item i) {
		items.add(i);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * check if character is dead
	 */

	public void ping(Board game) {
		currentState.ping(this, game);
		this.chars = game.characters();
	}

	/**
	 * character is healing
	 */
	public void playerRegenerating() {
		currentState.playerRegenerating(this);
	}

	/**
	 * draw the character
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void draw(Graphics g) {
		currentState.draw(this, g, img);
	}

	public void drawHealth(Graphics g, int wid, int hei) {
		g.clearRect(0, 0, wid, 120);
		
		g.setFont(new Font("default",Font.PLAIN, 16));
		g.setColor(Color.white);
		g.drawString("HEALTH: ", wid / 4, 20);
		
		g.setFont(new Font("default", Font.BOLD, 16));
		
		if (this.health >= 2000 && this.health < 4000) {
			g.setColor(Color.YELLOW);
		} else if (this.health > 0 && this.health < 2000) {
			g.setColor(Color.RED);
		} else if(this.health <= 0) {
			dead = true;
		}
		
		if (!dead) {
			String healthString = String.valueOf(this.health);
			g.drawString(healthString, wid / 4, 50);
		}else{
			g.setColor(Color.RED);
			g.drawString("DEAD", wid / 4, 50);

		}
	}

	/**
	 * Draws the items the player currently has
	 * 
	 * @param g
	 * @param wid
	 * @param hei
	 */
	public void drawItems(Graphics g, int wid, int hei) {
		int place = (8 * 4) * 4;
		// if(clearing)g.clearRect(0, 0, wid,hei);
		if (clearing)
			g.clearRect(0, place, wid, hei);// technical preventation of
											// flashing
		g.setFont(new Font("default",Font.PLAIN, 16));
		g.setColor(Color.white);
		g.drawString("ITEMS: ", wid / 4, 110);
		int count = 0;
		if (items != null) {
			for (Item i : items) {
				BufferedImage img = i.getImage();
				if (count == selected) {
					g.drawImage(img, img.getWidth() * 4, place,
							img.getWidth() * 4, img.getHeight() * 4, null);
				} else {
					g.drawImage(img, 0, place, img.getWidth() * 4,
							img.getHeight() * 4, null);
				}
				count++;
				place += img.getHeight() * 4 + 10;
			}
		}
		clearing = false;
	}

	/**
	 * Prevent item panel flashing from the clear graphics
	 */
	public void clearItems() {
		clearing = true;
	}

	/**
	 * Move character methods automatically
	 */
	public void moveLeft() {
		currentDirection = "left";
		currentState.moveLeft(this);
	}

	public void moveRight() {

		currentDirection = "right";
		currentState.moveRight(this);
	}

	public void moveUp() {

		currentDirection = "up";
		currentState.moveUp(this);
	}

	public void moveDown() {
		currentDirection = "down";
		currentState.moveDown(this);
	}

	public void attack() {
		// this.returnClosestDemon(chars).setAttacked(true); //not
		// working..suppose to make a demon turn red
		if (this.returnClosestDemon(chars) != null) {
			currentState.hit(this, this.returnClosestDemon(chars)); // decrease
																	// demon
																	// health
		}
		currentState.attack(this); // animation
	}

	public Demon returnClosestDemon(List<Characters> list) {
		if (chars != null || !chars.isEmpty()) {
			for (Characters d : chars) {
				if (d instanceof Demon) {
					if (this.isCloseTo((Demon) d)) {
						return (Demon) d;
					}
				}
			}
		}
		return null;

	}

	public boolean isCloseTo(Demon d) {
		if (Math.abs(this.getCoordinate().getX() - d.getCoordinate().getX()) < 20
				&& Math.abs(this.getCoordinate().getY()
						- d.getCoordinate().getY()) < 20) {
			return true;
		}
		return false;
	}

	/**
	 * hero goes stationary when not moving
	 */
	public void resetStationaryPoints() {
		if (!this.isRed()) {
			if (currentDirection.equals("left")) {
				img = left2;
			}
			if (currentDirection.equals("right")) {
				img = right2;
			}
			if (currentDirection.equals("up")) {
				img = up2;
			}
			if (currentDirection.equals("down")) {
				img = down2;
			}
		} else {
			if (currentDirection.equals("left")) {
				img = left2Hit;
			}
			if (currentDirection.equals("right")) {
				img = right2Hit;
			}
			if (currentDirection.equals("up")) {
				img = up2Hit;
			}
			if (currentDirection.equals("down")) {
				img = down2Hit;
			}
		}
	}

	public GameCoordinate getCoordinate() {
		return pos;
	}

	public boolean setCoordinate(int x, int y) {
		pos.setCoordinate(x, y);
		return true;
	}

	/**
	 * get the image associated with the direction
	 * 
	 * @param source
	 * @return
	 */
	public void getImage(String source) {
		if (source.equals("Up1")) {
			img = up1;
		} else if (source.equals("Up2")) {
			img = up2;
		} else if (source.equals("Up3")) {
			img = up3;
		} else if (source.equals("Down1")) {
			img = down1;
		} else if (source.equals("Down2")) {
			img = down2;
		} else if (source.equals("Down3")) {
			img = down3;
		} else if (source.equals("Right1")) {
			img = right1;
		} else if (source.equals("Right2")) {
			img = right2;
		} else if (source.equals("Right3")) {
			img = right3;
		} else if (source.equals("Left1")) {
			img = left1;
		} else if (source.equals("Left2")) {
			img = left2;
		} else if (source.equals("Left3")) {
			img = left3;
		}

		else if (source.equals("up1Hit")) {
			img = up1Hit;
		} else if (source.equals("up2Hit")) {
			img = up2Hit;
		} else if (source.equals("up3Hit")) {
			img = up3Hit;
		} else if (source.equals("down2Hit")) {
			img = down2Hit;
		} else if (source.equals("down1Hit")) {
			img = down1Hit;
		} else if (source.equals("down3Hit")) {
			img = down3Hit;
		} else if (source.equals("right2Hit")) {
			img = right2Hit;
		} else if (source.equals("right1Hit")) {
			img = right1Hit;
		} else if (source.equals("right3Hit")) {
			img = right3Hit;
		} else if (source.equals("left2Hit")) {
			img = left2Hit;
		} else if (source.equals("leftt1Hit")) {
			img = left1Hit;
		} else if (source.equals("left3Hit")) {
			img = left3Hit;
		} else if (source.equals("left1Hit")) {
			img = left1Att;
		} else if (source.equals("left2Hit")) {
			img = left3Hit;
		} else if (source.equals("up1Att")) {
			img = up1Att;
		} else if (source.equals("up2Att")) {
			img = up2Att;
		} else if (source.equals("up3Att")) {
			img = up3Att;
		} else if (source.equals("down2Att")) {
			img = down2Att;
		} else if (source.equals("down1Att")) {
			img = down2Att;
		} else if (source.equals("down3Att")) {
			img = down3Att;
		} else if (source.equals("right2Att")) {
			img = right2Att;
		} else if (source.equals("right1Att")) {
			img = right1Att;
		} else if (source.equals("right3Att")) {
			img = right3Att;
		} else if (source.equals("left2Att")) {
			img = left2Att;
		} else if (source.equals("left1Att")) {
			img = left1Att;
		} else if (source.equals("left3Att")) {
			img = left3Att;
		} else if (source.equals("up1AHit")) {
			img = up1AHit;
		} else if (source.equals("up2AHit")) {
			img = up2AHit;
		} else if (source.equals("up3AHit")) {
			img = up3AHit;
		} else if (source.equals("down2AHit")) {
			img = down2AHit;
		} else if (source.equals("down1AHit")) {
			img = down1AHit;
		} else if (source.equals("down3AHit")) {
			img = down3AHit;
		} else if (source.equals("right2AHit")) {
			img = right2AHit;
		} else if (source.equals("right1AHit")) {
			img = right1AHit;
		} else if (source.equals("right3AHit")) {
			img = right3AHit;
		} else if (source.equals("left2AHit")) {
			img = left2AHit;
		} else if (source.equals("leftt1AHit")) {
			img = left1AHit;
		} else if (source.equals("left3AHit")) {
			img = left3AHit;
		} else if (source.equals("left1AHit")) {
			img = left1Att;
		} else if (source.equals("left2AHit")) {
			img = left3AHit;
		}
	}

	public void setPosition(String source) {
		this.position = source;
	}

	public String getPosition() {
		return position;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int d) {
		this.health = d;
	}

	public void setState(HeroState cs) {
		this.currentState = cs;
	}

	/**
	 * perform attack
	 */
	public void hit(Demon demon) {
		currentState.hit(this, demon);
	}

	// =============================================assign images to
	// character===================================//
	public void createUp1Hit(BufferedImage img) {
		this.up1Hit = img;
	}

	public void createUp2Hit(BufferedImage img) {
		this.up2Hit = img;
	}

	public void createUp3Hit(BufferedImage img) {
		this.up3Hit = img;
	}

	public void createDown1Hit(BufferedImage img) {
		this.down1Hit = img;
	}

	public void createDown2Hit(BufferedImage img) {
		this.down2Hit = img;
	}

	public void createDown3Hit(BufferedImage img) {
		this.down3Hit = img;
	}

	public void createRight1Hit(BufferedImage img) {
		this.right1Hit = img;
	}

	public void createRight2Hit(BufferedImage img) {
		this.right2Hit = img;
	}

	public void createRight3Hit(BufferedImage img) {
		this.right3Hit = img;
	}

	public void createLeft1Hit(BufferedImage img) {
		this.left1Hit = img;
	}

	public void createLeft2Hit(BufferedImage img) {
		this.left2Hit = img;
	}

	public void createLeft3Hit(BufferedImage img) {
		this.left3Hit = img;
	}

	// Normal sprite
	public void createUp1(BufferedImage img) {
		this.up1 = img;
	}

	public void createUp2(BufferedImage img) {
		this.up2 = img;
	}

	public void createUp3(BufferedImage img) {
		this.up3 = img;
	}

	public void createDown1(BufferedImage img) {
		this.down1 = img;
	}

	public void createDown2(BufferedImage img) {
		this.down2 = img;
	}

	public void createDown3(BufferedImage img) {
		this.down3 = img;
	}

	public void createRight1(BufferedImage img) {
		this.right1 = img;
	}

	public void createRight2(BufferedImage img) {
		this.right2 = img;
	}

	public void createRight3(BufferedImage img) {
		this.right3 = img;
	}

	public void createLeft1(BufferedImage img) {
		this.left1 = img;
	}

	public void createLeft2(BufferedImage img) {
		this.left2 = img;
	}

	public void createLeft3(BufferedImage img) {
		this.left3 = img;
	}

	public void createUp1AHit(BufferedImage img) {
		this.up1AHit = img;
	}

	public void createUp2AHit(BufferedImage img) {
		this.up2AHit = img;
	}

	public void createUp3AHit(BufferedImage img) {
		this.up3AHit = img;
	}

	public void createDown1AHit(BufferedImage img) {
		this.down1AHit = img;
	}

	public void createDown2AHit(BufferedImage img) {
		this.down2AHit = img;
	}

	public void createDown3AHit(BufferedImage img) {
		this.down3AHit = img;
	}

	public void createRight1AHit(BufferedImage img) {
		this.right1AHit = img;
	}

	public void createRight2AHit(BufferedImage img) {
		this.right2AHit = img;
	}

	public void createRight3AHit(BufferedImage img) {
		this.right3AHit = img;
	}

	public void createLeft1AHit(BufferedImage img) {
		this.left1AHit = img;
	}

	public void createLeft2AHit(BufferedImage img) {
		this.left2AHit = img;
	}

	public void createLeft3AHit(BufferedImage img) {
		this.left3AHit = img;
	}

	// Attack sprite
	public void createUp1Att(BufferedImage img) {
		this.up1Att = img;
	}

	public void createUp2Att(BufferedImage img) {
		this.up2Att = img;
	}

	public void createUp3Att(BufferedImage img) {
		this.up3Att = img;
	}

	public void createDown1Att(BufferedImage img) {
		this.down1Att = img;
	}

	public void createDown2Att(BufferedImage img) {
		this.down2Att = img;
	}

	public void createDown3Att(BufferedImage img) {
		this.down3Att = img;
	}

	public void createRight1Att(BufferedImage img) {
		this.right1Att = img;
	}

	public void createRight2Att(BufferedImage img) {
		this.right2Att = img;
	}

	public void createRight3Att(BufferedImage img) {
		this.right3Att = img;
	}

	public void createLeft1Att(BufferedImage img) {
		this.left1Att = img;
	}

	public void createLeft2Att(BufferedImage img) {
		this.left2Att = img;
	}

	public void createLeft3Att(BufferedImage img) {
		this.left3Att = img;
	}

	public synchronized int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean isRed() {
		return red;
	}

	public void setRed(boolean red) {
		this.red = red;
	}

	public void setImage(String string) {
		position = string;

	}

	public String getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(String currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Characters fromInputStream(DataInputStream din, int rx, int ry)
			throws IOException {
		int uid = din.readByte();
		int x = din.readByte();
		int y = din.readByte();
		Hero p = LoadGame.createCharacter("", uid, x, y);
		return p;
	}

	public void toOutputStream(DataOutputStream dout) throws IOException {
		// dout.writeByte(Hero.);
		// dout.writeByte(x);
		// dout.writeByte(y);
		// dout.writeByte(uid);
		// dout.writeByte(direction);
		// dout.writeByte(state);
		// dout.writeByte(lives);
		// dout.writeShort(score);

	}

	public List<Characters> getChars() {
		return chars;
	}

	public void setChars(ArrayList<Characters> chars) {
		this.chars = chars;
	}

	public boolean isCanEvent() {
		return canEvent;
	}

	public void setCanEvent(boolean canEvent) {
		this.canEvent = canEvent;
	}

}