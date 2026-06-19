package game;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import multiplayer.Slave;

public class AdventureGame extends Thread {

	public final static int GAME_TYPE_SINGLE = 1;
	public final static int GAME_TYPE_SERVER = 2;
	public final static int GAME_TYPE_CLIENT = 3;
	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;

	private static JTextArea p;
	private static int type;
	private static int nPlayers;	
	private static String serverName;

	public AdventureGame(int type, int nPlayers, String serverName2, JTextArea p) {
		this.p = p;
		this.type = type;
		this.nPlayers = nPlayers;
		this.serverName = serverName2;
	}

	public void run() {

		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768;
		
		try {
			if (type == GAME_TYPE_CLIENT) {
				runClient(serverName,port);
			} else if (type == GAME_TYPE_SERVER) {
				Board b = new Board(500,500);
				multiUserGame(port,nPlayers,gameClock,broadcastClock,b);
			} else if (type == GAME_TYPE_SINGLE) {
				Board b = new Board(500, 500);
				singleUserGame(gameClock, b);
			} else {
				System.err
						.println("The type of game can only be of GAME_TYPE_CLIENT, GAME_TYPE_SERVER or GAME_TYPE_SINGLE");
			}
		} catch (Exception e) {

		}

	}

	private static void singleUserGame(int gameClock, Board game)
			throws IOException {
		SingleGame single = new SingleGame(gameClock, game);
		single.start();
	}
	
	private static void multiUserGame(int port, int nPlayers, int gameClock, int broadcastClock, Board game)throws IOException {		
		MultiGame m = new MultiGame(port,nPlayers,gameClock,broadcastClock,game,p);
		m.start();			
	}
	
	private static void runClient(String addr, int port) {
		try {
			Socket s = new Socket(addr,port);
			System.out.println("AdvGame CLIENT CONNECTED TO " + addr + ":" + port);			
			new Slave(s).run();
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());	
		}
	}
	
	public static JPanel createServerMenu(JTextArea a) {
		JPanel p = new JPanel(null);
		p.setBackground(Color.BLACK);
		a.setBounds(10, 10, 500, 160);
		p.add(a);		
		return p;
	}
	
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
	
	public static void main(String[] args){
		JTextArea p = new JTextArea();
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setSize(530, 220);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setContentPane(createServerMenu(p));
		
		type = GAME_TYPE_SINGLE;
		nPlayers = 0;
		serverName = "";		
		
		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if(arg.equals("-help")) {
					helpInfo();
					System.exit(0);
				} else if(arg.equals("-server")) {
					type = GAME_TYPE_SERVER;
					nPlayers = Integer.parseInt(args[++i]);
				} else if(arg.equals("-connect")) {
					type = GAME_TYPE_CLIENT;
					serverName = args[++i];
				}
				
			}
		}
		f.setVisible(true);
		if(type != GAME_TYPE_SERVER){
			f.dispose();
		}
		new AdventureGame(type,nPlayers,serverName,p).start();
		
	}

}
