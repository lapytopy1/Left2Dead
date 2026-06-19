package testGame;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Gui {
	private JFrame f = new JFrame("Testing");
	private JTextArea j = new JTextArea();
	private JTextField t = new JTextField();
	public void displayGui(){
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setBounds(10, 10, 365, 200);
		j.append("Hello");
		j.setEditable(false);
		t.setBounds(10, 210, 365, 30);
		
		f.add(t);
		f.add(j);
		f.setSize(400, 400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.displayGui();
		
	}
}
