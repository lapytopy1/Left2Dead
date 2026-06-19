
package multiplayer;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import game.*;

/**
 * A slave connection receives information about the current state of the board
 * and relays that into the local copy of the board. The slave connection also
 * notifies the master connection of key presses by the player.
 */
public final class Slave extends Thread implements KeyListener {
	private final Socket socket;
	private Board game;
	private GUIFrame display;
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	private int totalSent;

	/**
	 * Construct a slave connection from a socket. A slave connection does no
	 * local computation, other than to display the current state of the board;
	 * instead, board logic is controlled entirely by the server, and the slave
	 * display is only refreshed when data is received from the master
	 * connection.
	 * 
	 * @param socket
	 * @param dumbTerminal
	 */
	public Slave(Socket socket) {				
		this.socket = socket;				
	}
	
	public synchronized void run() {
		try {			
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			//input.read
			// First job, is to read the period so we can create the clock				
			uid = input.readInt();
			
			int width = input.readInt();
			int height = input.readInt();	
			//how many players
			int nPlayers = input.readInt();
						
			System.out.println("CLIENT UID: " + uid);
			System.out.println("CLIENT BOARD DIMENSIONS: " + width + " x " + height);
			System.out.println("Number of players : "+nPlayers);
			//setup slaves board
			game = new Board(width,height);

			//wants chracters hear but not made yet
			display = new GUIFrame("Client"+uid,game,uid,this);		
			//add all players to the board			
			//
			
			boolean exit = false;
			long totalRec = 0;

			while(!exit) {
				// read event
				
				//update the players board
				
				game.updateBoard(input.readUTF());
				display.repaint();
				
				//
				//System.out.println(game.getCharacters().size());
				//System.out.println("X: "+game.player(uid).getCoordinate().getX()+" Y: "+game.player(uid).getCoordinate().getY());
				totalRec += nPlayers;
				//display.repaint(); TODO
				// print out some useful information about the amount of data
				// sent and received
				System.out.print("\rREC: " + (totalRec / 1024) + "KB ("
						+ (rate(nPlayers) / 1024) + "KB/s) TX: " + totalSent
						+ " Bytes");			
			}
			socket.close(); // release socket ... v.important!
		} catch(Exception e) {
			if (e instanceof SocketException){
				System.out.println("\nServer has disconnected, Game Over");
				System.exit(1);
			}
			else if (e instanceof IOException){
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
			}
			else{
				e.printStackTrace();
			}
		} 
	}

	/**
	 * The following method calculates the rate of data received in bytes/s, albeit
	 * in a rather coarse manner.
	 * 
	 * @param amount
	 * @return
	 */
	private int rate(int amount) {
		rateTotal += amount;
		long time = System.currentTimeMillis();
		long period = time - rateStart;		
		if(period > 1000) {
			// more than a second since last calculation
			currentRate = (rateTotal * 1000) / (int) period;
			rateStart = time;
			rateTotal = 0;
		}
		
		return currentRate;		
	}
	private int rateTotal = 0;   // total accumulated this second
	private int currentRate = 0; // rate of reception last second
	private long rateStart = System.currentTimeMillis();  // start of this accumulation period 
	
	// The following intercept keyboard events from the user.
	
	public void keyPressed(KeyEvent e) {		
		try {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				//System.out.println("read right in slave 129");
				output.writeInt(3);
				totalSent += 4;

				if(game.canMoveRight(game.player(uid))){
					game.player(uid).moveRight();	

				}
				
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {	

				output.writeInt(4);
				totalSent += 4;
				if(game.canMoveLeft(game.player(uid))){
					game.player(uid).moveLeft();

				}

			} else if(code == KeyEvent.VK_UP) {

				output.writeInt(1);
				totalSent += 4;
				if(game.canMoveUp(game.player(uid))){
					game.player(uid).moveUp();

				}			
				
			} else if(code == KeyEvent.VK_DOWN) {

				output.writeInt(2);
				totalSent += 4;
				if(game.canMoveDown(game.player(uid))){
					game.player(uid).moveDown();

				}				
			} 
			//attack
			else if(code ==KeyEvent.VK_A){
				output.writeInt(5);
				totalSent +=4;
				game.player(uid).attack();

			}
			else if (code == KeyEvent.VK_W) {
				output.writeInt(6);
				totalSent +=4;
				game.player(uid).clearItems();
				game.player(uid).nextItem();

			} else if (code == KeyEvent.VK_E) {
				output.writeInt(7);
				totalSent +=4;
				game.player(uid).clearItems();
				game.player(uid).prevItem();
			}
			//adds extra demons (no need for multiplayer yet) TODO
			for(int i = 0;i<game.getTrigers().size();i++){

				if(game.getTrigers().get(i).contains(game.player(uid).getCoordinate().getX(),game.player(uid).getCoordinate().getY())){
					game.getCharacters().addAll((game.getTrigers().get(i).getDemons()));
					game.getTrigers().remove(i);
				}
			}
			//adds items (R thingy) Multiplayer soonTODO
			for(int i = 0; i<game.getItems().size();i++){
				if(game.getItems().get(i).contains(game.player(uid).getCoordinate().getX(),game.player(uid).getCoordinate().getY())){
					game.player(uid).setCanEvent(true);
					break;
				}
				else {
					game.player(uid).setCanEvent(false);
				}
			}
			output.flush();
		} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server.  So, we just ignore it.
		}
	}
	
	
	public void keyReleased(KeyEvent e) {
		try{
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
				System.out.println("Pressed R: Slave 250");
				for(int i = 0; i<game.getItems().size();i++){
					if(game.getItems().get(i).contains(game.player(uid).getCoordinate().getX(),game.player(uid).getCoordinate().getY())){
						output.writeInt(8);
						totalSent +=4;
						game.player(uid).addItem(game.getItems().get(i));
						game.getItems().remove(i);
						game.player(uid).setCanEvent(false);
					}
				}
			}
		}
		output.flush();
		}catch (IOException g){
			System.err.println("\nslave 256\n");
			g.printStackTrace();
		}
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void readIn(String str){
		
	}
}
