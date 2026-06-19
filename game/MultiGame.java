package game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import multiplayer.ClockThread;
import multiplayer.Master;

public class MultiGame extends Thread{
	
	//private Master cons;
	private Board game;
	//private ClockThread ct;
	private int port;
	private int nPlayers;
	private int gameClock;
	private int broadcastClock;
	private JTextArea print;
	private String serverName = "";
	
	public MultiGame(int port,int nPlayers, int gameClock, int broadcastClock, Board game ,JTextArea print) throws IOException{
		this.port = port;
		this.nPlayers = nPlayers;
		this.gameClock = gameClock;
		this.broadcastClock = broadcastClock;
		this.game = game;	
		this.print = print;
	}
	
	public void run(){
		final int realNum = nPlayers;
		//GUIFrame display = new GUIFrame("Client"+uid,game,uid,this);
		ClockThread clk = new ClockThread(gameClock,game,null);	
		print.append("Server waiting for "+nPlayers+" clients on port: "+port+"\n");
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			print.append("Server started, server ip is: "+ip.getHostAddress()+"\nServer name is: "+ip.getHostName()+" \n");
			serverName = ip.getHostName(); 
			//create an array of Master(which will contain the 4 clients)
			Master[] connections = new Master[nPlayers];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);
			while(true) {
				// 	Wait for a socket
				Socket s = ss.accept();
				print.append("ACCEPTED CONNECTION FROM: " + s.getInetAddress()+"\n");	
				//create a player for them

				//Player play = new Player(uid, game);
				game.registerHeroPortal();
				int uid = game.registerHero().getUid();
				 
				//create a new Master (client) and parse in the socket, uid clock and board
				connections[--nPlayers] = new Master(s,uid,broadcastClock, game, realNum);
				
				//start the thread
				connections[nPlayers].start();	
				//when all players have connected
				if(nPlayers == 0) {
					print.append("ALL CLIENTS ACCEPTED --- GAME BEGINS"+"\n");
					runServer(clk,game,connections);
					print.append("\nALL CLIENTS DISCONNECTED --- GAME OVER"+"\n");
					System.exit(0); //end game
				}
			}
	 
		  } catch (Exception e) {	 
			 // e.printStackTrace();	
			  print.append("Address already in use!"+"\n");
			  //System.exit(1);
		  }
	}
	private static void runServer(ClockThread clk, Board game,
			Master... connections) throws IOException {
		clk.start(); // start the clock ticking!!!				
		
		// loop forever		
		while(atleastOneConnection(connections)) {			
			game.setState(Board.PLAYING);			
			while(game.state() == Board.PLAYING) {
				Thread.yield();
			}					
		}
	}
	
	private static boolean atleastOneConnection(Master... connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}			
		}
		return false;
	}
	
	public String getServerName(){
		return this.serverName;
	}
}
