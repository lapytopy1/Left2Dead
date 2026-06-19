

package multiplayer;

import game.Board;
import Animation.Characters;
import Animation.Hero;
import java.io.*;
import java.net.*;

/**
 * A master connection receives events from a slave connection via a socket.
 * These events are registered with the board. The master connection is also
 * responsible for transmitting information to the slave about the current board
 * state.
 */

public final class Master extends Thread {
	//private final Board board;
	private final int broadcastClock;
	private final int uid;
	private final Socket socket;
	private Board board;
	private int nPlayer;

	public Master(Socket socket, int uid, int broadcastClock, Board board, int numPlayers) {
		this.board = board;	
		this.broadcastClock = broadcastClock;
		this.socket = socket;
		this.uid = uid;
		this.nPlayer = numPlayers;
	}
	
	public synchronized void run() {		
		try {
			
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new  DataOutputStream(socket.getOutputStream());
			
			// First, write the period to the stream

			output.writeInt(uid);
			output.writeInt(board.width());			
			output.writeInt(board.height());
			output.writeInt(nPlayer);
			
			boolean exit=false;
			while(!exit) {
				try {
					//
					if(input.available() != 0) {

						// read direction event from client.
						int dir = input.readInt();
						//System.out.println("Master"+uid+" dir"+dir);
						switch(dir) {
							case 1:
								if(board.canMoveUp(board.player(uid))){
									board.player(uid).moveUp();

									System.out.println(uid+" moving UP newX: "+board.player(uid).getCoordinate().getX());
									System.out.println(uid+" moving UP newY: "+board.player(uid).getCoordinate().getY());

								}
								break;
							case 2:
								if(board.canMoveDown(board.player(uid))){
								board.player(uid).moveDown();

								System.out.println(uid+" moving DOWN newX: "+board.player(uid).getCoordinate().getX());
								System.out.println(uid+" moving DOWN newY: "+board.player(uid).getCoordinate().getY());

								}
								break;
							case 3:
								if(board.canMoveRight(board.player(uid))){
								board.player(uid).moveRight();

								System.out.println(uid+" moving RIGHT newX: "+board.player(uid).getCoordinate().getX());
								System.out.println(uid+" moving RIGHT newY: "+board.player(uid).getCoordinate().getY());

								}
								break;
							case 4:
								if(board.canMoveLeft(board.player(uid))){
								board.player(uid).moveLeft();

								System.out.println(uid+" moving LEFT newX: "+board.player(uid).getCoordinate().getX());
								System.out.println(uid+" moving LEFT newY: "+board.player(uid).getCoordinate().getY());

								}
								break;
							case 5:
								System.out.println(uid+" attacking");
								board.player(uid).attack();

						}
					} 
					String newStr = "null";
					// Now, broadcast the state of the board to client
					for(Characters c : board.getCharacters()){				
						if(c instanceof Hero){
							c = (Hero)c;
							//System.out.println("X: "+c.getCoordinate().getX()+" Y: "+c.getCoordinate().getY());
							newStr += "uid "+(((Hero) c).getUid())+" x"+c.getCoordinate().getX()+","+c.getCoordinate().getY()+" -";
						}
					}

					output.writeUTF(newStr+" |");

					output.flush();
					Thread.sleep(broadcastClock);
				} catch(InterruptedException e) {					
				}
			}
			socket.close(); // release socket ... v.important!
		} catch(Exception e) {
			if (e instanceof SocketException){
				System.out.println("Client "+uid+" has disconnected");
				
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
	
}
