package game;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class GUIFrame extends JFrame{
	private final GUICanvas canvas;
	private GUIHud hud;
	
	public GUIFrame(String title, Board game, int uid, KeyListener... keys) {
		super(title);		
				
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		canvas = new GUICanvas(uid,game,scrnsize);
		hud = new GUIHud(uid,game,scrnsize);
		Container c = this.getContentPane();
		setLayout(new BorderLayout());
		for(KeyListener k : keys) {
			canvas.addKeyListener(k);
			hud.addKeyListener(k);
		}	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		c.add(canvas, BorderLayout.LINE_START);
		c.add(hud,BorderLayout.LINE_END);
//		add(canvas);
//		add(hud);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);						
		setBackground(Color.black);
		// Center window in screen
		//setBounds(0,0, getWidth(), getHeight());
//		setBounds((scrnsize.width - getWidth()) / 2,
//				(scrnsize.height - getHeight()) / 2, getWidth(), getHeight());
		pack();

		setResizable(false);	
		setLocationRelativeTo(null);
		// Display window
		setVisible(true);		
		canvas.requestFocus();
	}
	
	public synchronized void repaint() {
		canvas.repaint();
		hud.update();
	}	
	
	public GUICanvas getCan(){
		return canvas;
	}
	
	public GUIHud getHud(){
		return hud;
	}
}
