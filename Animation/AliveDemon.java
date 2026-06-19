package Animation;

import game.Board;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Behavior of an alive demon
 * @author ross
 *
 */
public class AliveDemon implements DemonState {
	private final int height = 32;
	private final int width = 32;
	

	public void draw(Demon mon, Graphics g,BufferedImage bi) {
		g.drawImage(bi, mon.getCoordinate().getX(), mon.getCoordinate().getY(), width, height, null);
	}
	

	public void attackPlayer(Hero c,int damage) {
		c.setRed(true);
		if(c.getHealth()>0){
			c.setHealth(c.getHealth()-damage);
		}
	}


	public synchronized void ping(Demon d,Board b) {
		if(d.getHealth()<=0){
			d.setState(new DeadDemon(d.getCoordinate().getX(),d.getCoordinate().getY(),d.getType()));
		}

		if(d.getType().equals("weak")){ //weak demon ai -  move random
			Random r = new Random(); //will add can move right etc when its working
			int direction = r.nextInt(100); //1/5 chance of moving per ping..might too often in the real game
			if(direction == 0 && b.canMoveDownDemon(d) ){d.moveDown();}
			if(direction == 1 && b.canMoveLeftDemon(d)){d.moveLeft();}
			if(direction == 2 && b.canMoveRightDemon(d)){d.moveRight();}
			if(direction == 3 && b.canMoveUpDemon(d)){d.moveUp();}
		}
		if(d.getType().equals("med") || d.getType().equals("boss")){
			//med demon and boss chase hero anywhere
			for(Characters c : b.characters()){
				if(c instanceof Hero){
					if(d.getCoordinate().getY()<c.getCoordinate().getY()){
						if(b.canMoveDownDemon(d)){
							d.moveDown();

						}
					}
					if(d.getCoordinate().getY()>c.getCoordinate().getY()){
						if(b.canMoveUpDemon(d)){
							d.moveUp();

						}
					}
					if(d.getCoordinate().getX()<c.getCoordinate().getX()){
						if(b.canMoveRightDemon(d)){
							d.moveRight();

						}
					}
					if(d.getCoordinate().getX()>c.getCoordinate().getX()){
						if(b.canMoveLeftDemon(d)){
							d.moveLeft();

						}
					}


					//					if((d.getCoordinate().getY()-c.getCoordinate().getY())<0 && (d.getCoordinate().getY()-c.getCoordinate().getY())>-48){
					//						if(b.canMoveDownDemon(d)){
					//							d.moveDown();
					//
					//						}
					//					}
					//					if((d.getCoordinate().getY()-c.getCoordinate().getY())>16&& (d.getCoordinate().getY()-c.getCoordinate().getY())<64){
					//						if(b.canMoveUpDemon(d) ){
					//							d.moveUp();
					//
					//						}
					//					}
					//					if((d.getCoordinate().getX()-c.getCoordinate().getX())<0 && (d.getCoordinate().getX()-c.getCoordinate().getX())>-64){
					//						if(b.canMoveRightDemon(d)){
					//							d.moveRight();
					//
					//						}
					//					}
					//					if((d.getCoordinate().getX()-c.getCoordinate().getX())>16 && (d.getCoordinate().getX()-c.getCoordinate().getX())<48){
					//						if(b.canMoveLeftDemon(d)){
					//							d.moveLeft();
					//
					//
					//						}
					//					}
					//					//	count++;
					//
					//}//if count==0 statement
					//					if(count==10){
					//						System.out.println("here");
					//						count =0;
					//					}
				}//if instance hero statement
			}//med for if statement
		}//med if statement
	}

	public String stateToString() {
		return "alive";
	}

	public void moveLeft(Demon h) {
		if(!h.isAttacked()){
			if(h.getPosition().equals("Left2")){
				this.getImageSetPosition("Left1",h);
			} else if(h.getPosition().equals("Left1")){
				this.getImageSetPosition("Left3",h);
			} else {
				this.getImageSetPosition("Left2",h);
			}
		} else{
			if(h.getPosition().equals("left2Hit")){
				this.getImageSetPosition("left1Hit",h);
			} else if(h.getPosition().equals("left1Hit")){
				this.getImageSetPosition("left3Hit",h);
			} else {
				this.getImageSetPosition("left2Hit",h);
			}
		}
		//various demon speeds of moving
		if(h.getType().equals("weak")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX()-16, h.getCoordinate().getY());
		}
		if(h.getType().equals("med")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX()-4, h.getCoordinate().getY());
		}
		if(h.getType().equals("boss")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX()-1, h.getCoordinate().getY());
		}
		//h.getCoordinate().updateCoordinate(h.getCoordinate().getX()-16, h.getCoordinate().getY());
	}

	public void moveRight(Demon h) {
		if(!h.isAttacked()){
			if(h.getPosition().equals("Right2")){
				this.getImageSetPosition("Right1",h);
			} else if(h.getPosition().equals("Right1")){
				this.getImageSetPosition("Right3",h);
			} else {
				this.getImageSetPosition("Right2",h);
			}
		} else{
			if(h.getPosition().equals("right2Hit")){
				this.getImageSetPosition("right1Hit",h);
			} else if(h.getPosition().equals("right1Hit")){
				this.getImageSetPosition("right3Hit",h);
			} else {
				this.getImageSetPosition("right2Hit",h);
			}
		}
		if(h.getType().equals("weak")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX()+16, h.getCoordinate().getY());
		}
		if(h.getType().equals("med")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX()+4, h.getCoordinate().getY());
		}
		if(h.getType().equals("boss")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX()+1, h.getCoordinate().getY());
		}
	}


	public void moveUp(Demon h) {
		if(!h.isAttacked()){
			if(h.getPosition().equals("Up2")){
				this.getImageSetPosition("Up1",h);
			} else if(h.getPosition().equals("Up1")){
				this.getImageSetPosition("Up3",h);
			} else {
				this.getImageSetPosition("Up2",h);
			}
		} else{
			if(h.getPosition().equals("up2Hit")){
				this.getImageSetPosition("up1Hit",h);
			} else if(h.getPosition().equals("up1Hit")){
				this.getImageSetPosition("up3Hit",h);
			} else {
				this.getImageSetPosition("up2Hit",h);
			}
		}
		if(h.getType().equals("weak"))
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()-16);
		if(h.getType().equals("med")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()-4);
		}
		if(h.getType().equals("boss")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()-1);
		}
	}

	public void moveDown(Demon h) {
		if(!h.isAttacked()){
			if(h.getPosition().equals("Down2")){
				this.getImageSetPosition("Down1",h);
			} else if(h.getPosition().equals("Down1")){
				this.getImageSetPosition("Down3",h);
			} else {
				this.getImageSetPosition("Down2",h);
			}
		} else{
			if(h.getPosition().equals("down2Hit")){
				this.getImageSetPosition("down1Hit",h);
			} else if(h.getPosition().equals("down1Hit")){
				this.getImageSetPosition("down3Hit",h);
			} else {
				this.getImageSetPosition("down2Hit",h);
			}
		}
		//h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()+16);
		if(h.getType().equals("weak"))
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()+16);
		if(h.getType().equals("med")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()+4);
		}
		if(h.getType().equals("boss")){
			h.getCoordinate().updateCoordinate(h.getCoordinate().getX(), h.getCoordinate().getY()+1);
		}
	}

	public void attack(Demon c) {
		if (c.isAttacked()) {
			if (c.getCurrentDirection().equals("left")){c.getImage("left2AHit");}
			else if (c.getCurrentDirection().equals("right")){c.getImage("right2AHit");}
			else if (c.getCurrentDirection().equals("up")){c.getImage("up2AHit");}
			else if (c.getCurrentDirection().equals("down")){c.getImage("down2AHit");}
		} else {
			if (c.getCurrentDirection().equals("left")){c.getImage("left2Att");}
			else if (c.getCurrentDirection().equals("right")){c.getImage("right2Att");}
			else if (c.getCurrentDirection().equals("up")){c.getImage("up2Att");}
			else if (c.getCurrentDirection().equals("down")) {c.getImage("down2Att");}
		}
	}

	/**
	 * shortcut method to doing demon.getImage(string)
	 * and demon.setPosistion(string)
	 * @param image
	 * @param demon
	 */
	public void getImageSetPosition(String image, Demon demon){
		demon.getImage(image);
		demon.setPosition(image);
	}

}
