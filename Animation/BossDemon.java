package Animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import loader.LoadGame;
import game.Board;
import game.GameCoordinate;

/**
 * the boss demon is the strongest demon in the game
 * they have a high hp and damage but move very slow
 * @author ross
 *
 */
public class BossDemon extends Demon {
	private DemonState currentState = new AliveDemon();
	private int health = 100;
	private int damage = 10;
	private GameCoordinate pos;
	private String position = "";
	private static BufferedImage img;
	private String type = "boss"; //Easy,Med, or boss
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

	public BossDemon(int x, int y) {
		super(x, y);
		this.setPos(new GameCoordinate(x,y,"Boss Demon"));
		LoadGame.createDemon("H", this);
		this.getImage("Down2");
	}

	public void ping(Board b){currentState.ping(this,b);}
	public void attackPlayer(Hero c) {currentState.attackPlayer(c,this.getDamage());}
	public void draw(Graphics g) {currentState.draw(this, g,img);}
	public int getDamage(){return damage;}
	public String getPosition() {return position;}

	public boolean isCloseTo(Hero chara){
		if(Math.abs(this.getCoordinate().getX()-chara.getCoordinate().getX())<25 && Math.abs(this.getCoordinate().getY()-chara.getCoordinate().getY())<25){
			return true;
		}
		return false;
	}

	//==============================================================================================//
	public void setState(DemonState state){this.currentState = state;}
	public void setImage(String source){this.position = source;}
	public void setPosition(String position) {
		this.position = position;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Characters fromInputStream(DataInputStream din, int x, int y)
			throws IOException {
		return null;
	}

	public void toOutputStream(DataOutputStream dout) throws IOException {

	}


	//Hit sprite
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

	//Normal sprite
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

	//Attack sprite
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

	public BufferedImage getImage(String source) {
		if(source.equals("Up1")){
			img = up1;
		}else if(source.equals("Up2")){
			img = up2;
		}else if(source.equals("Up3")){
			img = up3;
		}else if(source.equals("Down1")){
			img = down1;
		}else if(source.equals("Down2")){
			img = down2;
		}else if(source.equals("Down3")){
			img = down3;
		}else if(source.equals("Right1")){
			img = right1;
		}else if(source.equals("Right2")){
			img = right2;
		}else if(source.equals("Right3")){
			img = right3;
		}else if(source.equals("Left1")){
			img = left1;
		}else if(source.equals("Left2")){
			img = left2;
		}else if(source.equals("Left3")){
			img = left3;
		}
		else if(source.equals("up1Hit")){
			img = up1Hit;
		}else if(source.equals("up2Hit")){
			img = up2Hit;
		}else if(source.equals("up3Hit")){
			img = up3Hit;
		}else if(source.equals("down2Hit")){
			img = down2Hit;
		}else if(source.equals("down1Hit")){
			img = down1Hit;
		}else if(source.equals("down3Hit")){
			img = down3Hit;
		}else if(source.equals("right2Hit")){
			img = right2Hit;
		}else if(source.equals("right1Hit")){
			img = right1Hit;
		}else if(source.equals("right3Hit")){
			img = right3Hit;
		}else if(source.equals("left2Hit")){
			img = left2Hit;
		}else if(source.equals("leftt1Hit")){
			img = left1Hit;
		}else if(source.equals("left3Hit")){
			img = left3Hit;
		}
		return null;
	}

	public GameCoordinate getPos() {
		return pos;
	}

	public void setPos(GameCoordinate pos) {
		this.pos = pos;
	}
}
