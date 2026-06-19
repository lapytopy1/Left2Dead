package multiplayer;

import game.Board;
import java.awt.event.*;
import Animation.Hero;

public class Player implements KeyListener {
	private final Board game;
	private int uid;
	private Hero myHero;

	public Player(int uid, Board game) {
		this.game = game;
		this.setUid(uid);

		// myHero = LoadGame.createCharacter("",this.uid,1,1);
		// myHero = LoadGame.createCharacter("",this.uid,1,1);
		// myHero.draw(game.)
	}

	// The following intercept keyboard events from the user.

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {

			if (game.canMoveRight(myHero)) {
				game.player(uid).moveRight();
			}
		} 
		else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
			if (game.canMoveLeft(myHero)) {
				game.player(uid).moveLeft();
//				
			}
		} 
		else if (code == KeyEvent.VK_UP) {
			if (game.canMoveUp(myHero)) {
				game.player(uid).moveUp();
			}

		} 
		else if (code == KeyEvent.VK_DOWN) {
			if (game.canMoveDown(myHero)) {
				game.player(uid).moveDown();
			}

		} 
		else if (code == KeyEvent.VK_A) {
			game.player(uid).attack();
		} 
		else if (code == KeyEvent.VK_W) {

			game.player(uid).clearItems();
			game.player(uid).nextItem();

		} else if (code == KeyEvent.VK_E) {
			game.player(uid).clearItems();
			game.player(uid).prevItem();
		}
		
		for(int i = 0;i<game.getTrigers().size();i++){
			if(game.getTrigers().get(i).contains(game.player(uid).getCoordinate().getX(),game.player(uid).getCoordinate().getY())){
				game.getCharacters().addAll((game.getTrigers().get(i).getDemons()));
				game.getTrigers().remove(i);
			}
		}
		
		for(int i = 0; i<game.getItems().size();i++){
			if(game.getItems().get(i).contains(game.player(uid).getCoordinate().getX(),game.player(uid).getCoordinate().getY())){

				game.player(uid).setCanEvent(true);
				break;
			}else {
				game.player(uid).setCanEvent(false);
			}
		}
		// repaint does nothing
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_KP_RIGHT) { 
			// change to stationary
			game.player(uid).resetStationaryPoints();

		}
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
			game.player(uid).resetStationaryPoints();

		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_KP_RIGHT) {
			game.player(uid).resetStationaryPoints();

		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) {
			game.player(uid).resetStationaryPoints();

		}

		if (key == KeyEvent.VK_A) {
			game.player(uid).resetStationaryPoints();

		}
		
		if (key == KeyEvent.VK_R){
			if(game.player(uid).isCanEvent()){
				System.out.println("Pressed R: Player 110");
				for(int i = 0; i<game.getItems().size();i++){
					if(game.getItems().get(i).contains(game.player(uid).getCoordinate().getX(),game.player(uid).getCoordinate().getY())){
						game.player(uid).addItem(game.getItems().get(i));
						game.getItems().remove(i);
						game.player(uid).setCanEvent(false);
					}
				}
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Hero getMyHero() {
		return myHero;
	}

	public void setMyHero(Hero myHero) {
		this.myHero = myHero;
	}
}
