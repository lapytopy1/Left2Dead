
// Copyright 2020, TimmyC <- is gay
package multiplayer;

import game.Board;
import game.GUIFrame;

/**
 * The Clock Thread is responsible for producing a consistent "pulse" which is
 * used to update the game state, and refresh the display. Setting the pulse
 * rate too high may cause problems, when the point is reached at which the work
 * done to service a given pulse exceeds the time between pulses.
 * 
 * @author JONO RULZ LOLOLOL HAXORZZZZ
 * 
 */
public class ClockThread extends Thread {
	private final int delay; // delay between pulses in us
	private final Board game;
	private final GUIFrame display;

	public ClockThread(int delay, Board game, GUIFrame display) {
		this.delay = delay;
		this.game = game;
		this.display = display;
	}

	public synchronized void run() {
		
		while(true) {
			// Loop forever			
			try {
				Thread.sleep(delay); //TODO
				game.setState(Board.PLAYING);
				game.clockTick();
				//check for if the display is null
				//as server doesn't have a display
				if(display != null) {
					display.repaint();
				}

			} catch(InterruptedException e) {
				// should never happen
			}			
		}
	}
}
