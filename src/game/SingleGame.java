package game;

import multiplayer.ClockThread;
import multiplayer.Player;

public class SingleGame extends Thread {

	private int playerID = 1;
	private Board game;
	private GUIFrame display;
	private int gameClock;

	public SingleGame(int gameClock, Board game) {

		this.gameClock = gameClock;
		this.game = game;

		Player player = new Player(playerID, game);
		game.registerHeroPortal();
		player.setMyHero(game.registerHero());
		this.display = new GUIFrame("Left 2 Dead", game, playerID, player);

	}

	@Override
	public void run() {
		ClockThread clk = new ClockThread(gameClock, game, display);
		clk.start();
		game.registerDemon();
		game.setState(Board.PLAYING);
	}
}
