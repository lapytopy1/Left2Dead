package game;

import java.awt.*;

import javax.swing.JComponent;


public class GUICanvas extends JComponent {

	private final int uid;
	private final Board gameBoard;

	public GUICanvas(int uid, Board gameBoard, Dimension scrnsize){
		this.gameBoard = gameBoard;
		this.uid = uid;

	  //  GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Dimension d = new Dimension(scrnsize.width-101, scrnsize.height);
	    setPreferredSize(d);

	}

	public synchronized void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(-2000, -2000, 10000, 10000);
		gameBoard.draw(g, gameBoard.getxTrans(), gameBoard.getyTrans());
	}
	/*public synchronized void update(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(-2000, -2000, 10000, 10000);
		gameBoard.draw(g, gameBoard.getxTrans(), gameBoard.getyTrans());
	}*/
}
