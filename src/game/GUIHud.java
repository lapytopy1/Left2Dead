package game;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import Animation.Hero;
import loader.Item;

public class GUIHud extends JPanel {
	private boolean first = true;
	private Board board;
	private int uid;
	private Hero hero;
	private ArrayList<Item> items = new ArrayList<Item>();

	GUIHud(int uid, Board board, Dimension scrnsize) {
		this.uid = uid;
		this.board = board;
		Dimension d = new Dimension(100, scrnsize.height-200);
		setPreferredSize(d);
	}

	public synchronized void update() {
		if (this.board.player(this.uid) != null && first) {
			this.hero = this.board.player(this.uid);
			first = false;
		}
		items = this.hero.getItems();
		this.repaint();
	}

	public void repaint() {
		Graphics g = this.getGraphics();
		this.setBackground(Color.BLACK);
		try {
			if (hero != null && g != null && this != null) {
				hero.drawHealth(g, this.getWidth(), this.getHeight());
			}
			if (items != null && g != null && this != null) {
				hero.drawItems(g, this.getWidth(), this.getHeight());
			}
		} catch (NullPointerException e) {

		}
	}

}
