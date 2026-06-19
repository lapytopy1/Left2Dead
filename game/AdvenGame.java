package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;

import loader.LoadGame;
import multiplayer.Master;
import multiplayer.ClockThread;
import multiplayer.Player;
import multiplayer.Slave;

public class AdvenGame {
	
	int gameClock = 1000/60;
	int numPlayers = 1;
	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;

	private JDialog dialog = new JDialog();
	private final JPanel charPanel = new JPanel(new GridLayout(0, 1, 1, 1));
	private final ButtonGroup bg = new ButtonGroup();
	private final JButton done = new JButton("Done");

	private JRadioButtonMenuItem oneP = new JRadioButtonMenuItem();
	private JRadioButtonMenuItem twoP = new JRadioButtonMenuItem();
	private JRadioButtonMenuItem threeP = new JRadioButtonMenuItem();
	private JRadioButtonMenuItem fourP = new JRadioButtonMenuItem();
	
	private JTextArea text = new JTextArea();
	private JPanel p = new JPanel();
	

	public AdvenGame(int nClients, boolean server, String url) {
		//doesnt do anything atm #Lols yea right tim
		
		//boolean server = false;
		//int nclients = 0;		
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768; // default
		
		
		
		try {
			if (server) {				
				Board b = new Board(500, 500);
				// Run in Server mode
				System.out.println("New multiplayer server started");
				multiUserGame(port, nClients, gameClock, broadcastClock, b);
				
			} else if (url != null) {
				// Run in client mode
				System.out.println("New multiplayer client started");
				runClient(url, port);
			} else {
				// single user game
				//Welcome w = new Welcome();
				System.out.println("New Singleplayer game started");
				Board b = new Board(500, 500);
				singleUserGame(gameClock, b);
			}
		} catch (IOException ioe) {
			System.out.println("I/O error: " + ioe.getMessage());
			System.exit(1);
		}
		
	}
	
	public static void main(String[] args) {
		// ======================================================
		// ======== First, parse command-line arguments ========
		// ======================================================
		String filename = null;
		boolean server = false;
		int nclients = 0;
		String url = null;
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768; // default
		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if(arg.equals("-help")) {
					helpInfo();
					System.exit(0);
				} else if(arg.equals("-server")) {
					server = true;
					nclients = Integer.parseInt(args[++i]);
				} else if(arg.equals("-connect")) {
					url = args[++i];
				} else if(arg.equals("-clock")) {
					gameClock = Integer.parseInt(args[++i]);
				} else if(arg.equals("-port")) {
					port = Integer.parseInt(args[++i]);
				} else if(arg.equals("-nhoming")) {
					int lbah = Integer.parseInt(args[++i]);
				} else if(arg.equals("-nrandom")) {
					int nasn = Integer.parseInt(args[++i]);
				}
			} else {
				filename = args[i];
			}
		}
		filename = "Map.tmx"; //TODO just so you noobs can run without arguments -tim stfu richard -djp aka bjp


		// Sanity checks
		if (url != null && server) {
			System.out
					.println("Cannot be a server and connect to another server!");
			System.exit(1);
		} else if (url != null && gameClock != DEFAULT_CLK_PERIOD) {
			System.out
					.println("Cannot overide clock period when connecting to server.");
			System.exit(1);
		} else if (url == null && filename == null) {
			System.out
					.println("Board file must be provided for single user, or server mode.");
			System.exit(1);
		}

		try {
			if (server) {
				Board b = new Board(500, 500);

				// Run in Server mode
				System.out.println("Multiplayer Server Starting");
				multiUserGame(port, nclients, gameClock, broadcastClock, b);
				
			} else if (url != null) {
				// Run in client mode
				System.out.print("Multiplayer Client Starting");
				runClient(url, port);
			} else {
				// single user game
				//Welcome w = new Welcome();
				System.out.println("Single Player Game Starting");
				Board b = new Board(500, 500);			
				singleUserGame(gameClock, b);
			}
		} catch (IOException ioe) {
			System.out.println("I/O error: " + ioe.getMessage());
			System.exit(1);
		}

	}
	/**
	 * Run a single client
	 * 
	 */
	
	private static void runClient(String addr, int port) {
		try {
			Socket s = new Socket(addr,port);
			System.out.println("AdvGame CLIENT CONNECTED TO " + addr + ":" + port);			
			new Slave(s).run();
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());	
		}
	}

	private static void singleUserGame(int gameClock, Board game) throws IOException {
		// int playerID = game.registerHero();
		SingleGame single = new SingleGame(gameClock,game);
		single.start();
//		int playerID = 1;
//		Player play = new Player(playerID, game);
//		game.registerHeroPortal();
//		play.setMyHero(game.registerHero());
//		System.out.println("Here advengame 184");
//		GUIFrame display = new GUIFrame("Left 2 Dead", game, playerID, play);
//		System.out.println("Here advengame 186");
//		ClockThread clk = new ClockThread(gameClock, game, display);
//		// save initial state of board, so we can reset it.
//		clk.start(); // start the clock ticking!!
//		System.out.println("Here 191 advengame");
//		game.registerDemon(true);
//		while (display.isVisible()) {
//			// keep going until the frame becomes invisible
//			//game.setState(Board.READY);
//			//pause(3000);
//			
//			game.setState(Board.PLAYING);
//			System.out.println("Here 199 advengame");
//			//System.out.println("game starting");
//			// now, wait for the game to finish
//			while (game.state() == Board.PLAYING) {
//				Thread.yield();
//				//System.out.println("Here 204 advengame");
//			}
//			
//			System.out.println("It gets here");
//			// If we get here, then we're in game over mode
//			//pause(3000); // TODO SHOULD NEVER GET HERE
//			//System.out.println("\n\n\n\n\nRESETING");
//			//System.out.println("game over mode");
//			// Reset board state
//			// game.fromByteArray(state);
//		}
	}
	/**
	 * Start a game with more than one player
	 *
	 * @param port
	 * @param nPlayers
	 * @param gameClock
	 * @param broadcastClock
	 * @param game
	 * @throws IOException
	 */
	private static void multiUserGame(int port, int nPlayers, int gameClock, int broadcastClock, Board game)throws IOException {

		final int realNum = nPlayers;
		ClockThread clk = new ClockThread(gameClock,game,null);	
		System.out.println("Server waiting for "+nPlayers+" clients on port: "+port);
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			System.out.println("Server started, server ip is: "+ip.getHostAddress()+"\nServer name is: "+ip.getHostName());
			//create an array of Master(which will contain the 4 clients)
			Master[] connections = new Master[nPlayers];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());	
				//create a player for them

				game.registerHeroPortal();
				int uid = game.registerHero().getUid();
				 
				//create a new Master (client) and parse in the socket, uid clock and board
				connections[--nPlayers] = new Master(s,uid,broadcastClock, game, realNum);
				
				//start the thread
				connections[nPlayers].start();	
				//when all players have connected
				if(nPlayers == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					runServer(clk,game,connections);
					System.out.println("\nALL CLIENTS DISCONNECTED --- GAME OVER");
					System.exit(0); //end game
				}
			}
	 
		  } catch (UnknownHostException e) {	 
			  e.printStackTrace();	 
		  }

		
	}
	/**
	 * Run a server
	 * 
	 * 
	 */
	private static void runServer(ClockThread clk, Board game,
			Master... connections) throws IOException {
		clk.start(); // start the clock ticking!!!				
		
		// loop forever
		while(atleastOneConnection(connections)) {

			game.setState(Board.PLAYING);
			// now, wait for the game to finish
			while(game.state() == Board.PLAYING) {
				Thread.yield();
			}

		}
	}
	/**
	 * Check whether or not there is at least one connection alive.
	 * 
	 * @param connections
	 * @return
	 */
	private static boolean atleastOneConnection(Master... connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}			
		}
		return false;
	}

	/**
	 * Pause the game
	 * 
	 * @param delay - int (how long to pause for)
	 */

	private static void pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}
	}

	public void choosePlayers() {

		oneP.setText("One Player");
		twoP.setText("Two Players");
		threeP.setText("Three Players");
		fourP.setText("Four Players");
		done.setText("Done");

		oneP.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				BorderFactory.createEmptyBorder()));

		twoP.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				BorderFactory.createEmptyBorder()));

		threeP.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				BorderFactory.createEmptyBorder()));

		fourP.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				BorderFactory.createEmptyBorder()));

		dialog.setContentPane(charPanel);
		// charPanel.add(pName);

		// charPanel.setBackground(Color.white);
		//
		//
		bg.add(oneP);
		bg.add(twoP);
		bg.add(threeP);
		bg.add(fourP);

		charPanel.add(oneP);
		charPanel.add(twoP);
		charPanel.add(threeP);
		charPanel.add(fourP);
		charPanel.add(done);
		charPanel.validate();
		charPanel.setVisible(true);
		dialog.setTitle("Choose Player");
		dialog.setPreferredSize(new Dimension(200, 300));
		dialog.setResizable(true);
		// dialog.setLocationRelativeTo(frame);
		dialog.setLocation(700, 300);
		dialog.setModal(true);
		dialog.pack();
		dialog.validate();
		dialog.setFocusable(true);
		dialog.setVisible(true);
	}


			

	/**
	 * Useful for debugging
	 * 
	 */
	static {
		System.setProperty("sun.awt.exception.handler", "game.AdvenGame");
	}
	
	public void handle(Throwable ex) {
		try {
			ex.printStackTrace();
			System.exit(1); } 
		catch(Throwable t) {}
	}
	
	/**
	 * How to use the game
	 * 
	 */
	private static void helpInfo() {		
		String[][] info = {		
				{"server <n>", "Run in server mode, awaiting n client connections"},
				{"connect <url>", "Connect to server at <url>"},
				{"clock", "Set clock period (default 20ms)"},
				{"bclock","Set broadcast clock period (default 5ms)"},
				{"port", "Set port for use for connection (default 32768)"},			
		};
		System.out.println("Usage: java com.game.AdvenGame <options> ");
		System.out.println("Options:");

		// first, work out gap information
		int gap = 0;

		for (String[] p : info) {
			gap = Math.max(gap, p[0].length() + 5);
		}

		// now, print the information
		for (String[] p : info) {
			System.out.print("  -" + p[0]);
			int rest = gap - p[0].length();
			for (int i = 0; i != rest; ++i) {
				System.out.print(" ");
			}
			System.out.println(p[1]);
		}
	}
	private void charselectError() { //TODO ill remove this when were done - Jono
		final JDialog notify = new JDialog(dialog);
		JButton ok = new JButton("OK!");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				notify.dispose();
			}
		});
		JPanel dPanel = new JPanel();
		notify.setTitle("Warning");
		notify.setVisible(true);
		JTextField notification = new JTextField();
		notification.setText("Please Choose How Many Players!");
		notification.setEditable(false);
		notification.setBorder(BorderFactory.createEmptyBorder());
		notification.setBackground(Color.white);
		dPanel.add(notification);
		dPanel.add(ok);
		dPanel.setBackground(Color.white);
		notify.add(dPanel);
		notify.setBackground(Color.white);
		notify.setSize(new Dimension(200, 100));
		notify.setResizable(false);
		notify.setLocationRelativeTo(dialog);
		notify.setModal(true);
		notify.setVisible(true);

	}

}
