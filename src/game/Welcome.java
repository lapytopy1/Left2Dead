package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Severity;
import javax.swing.*;
import javax.swing.text.*;

import loader.LoadGame;

/**
 * Simple welcome message box to intro them to the game
 * 
 * @author Jon
 * 
 */

public class Welcome {

	public final int GAME_TYPE_SINGLE = 1;
	public final int GAME_TYPE_SERVER = 2;
	public final int GAME_TYPE_CLIENT = 3;
	// For start up pop-up menu
	// private JDialog menu = new JDialog();
	// private final JPanel menuPanel = new JPanel(new GridLayout(0, 1, 1, 1));
	// private final JButton controls = new JButton();
	// private final JButton start = new JButton();
	// private JLabel imageTitle;

	private int bWidth;
	private int bHeight;

	private JFrame mFrame = new JFrame();
	private JPanel mPanel = new JPanel(new GridLayout(0, 1, 1, 1));
	private JPanel cPanel = new JPanel(new GridLayout(0, 1, 1, 1));
	private JPanel sPanel = new JPanel(new GridLayout(0, 1, 1, 1));

	private JTextField eNumber = new JTextField(10);
	private JTextField eName = new JTextField(10);
	private JTextArea serverPrint = new JTextArea();

	private JButton cont = new JButton();
	private JButton start = new JButton();
	private JButton back = new JButton();
	private JButton single = new JButton();
	private JButton multi = new JButton();
	private JButton backS = new JButton();
	private JButton backM = new JButton();
	private JButton host = new JButton();
	private JButton join = new JButton();
	private JButton joinS = new JButton();
	private JButton hostS = new JButton();
	private JButton joinH = new JButton();

	private BufferedImage title;
	private ImageIcon startImg;
	private ImageIcon controlImg;
	private ImageIcon backImg;

	private ImageIcon singleImg;
	private ImageIcon multiImg;
	private ImageIcon joinImg;
	private ImageIcon hostImg;

	public Welcome() {
		startMenu();
	}

	public void startMenu() {
		try {
			title = ImageIO.read(new File("data/Title/left2Dead.png"));
			startImg = new ImageIcon("data/Title/Start.png");
			controlImg = new ImageIcon("data/Title/Controls.png");
			backImg = new ImageIcon("data/Title/Back.png");
			singleImg = new ImageIcon("data/Title/Single.png");
			multiImg = new ImageIcon("data/Title/Multi.png");
			joinImg = new ImageIcon("data/Title/Join.png");
			hostImg = new ImageIcon("data/Title/Host.png");

			bWidth = startImg.getIconWidth();
			bHeight = startImg.getIconHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		serverPrint.setEditable(false);

		eNumber.setHorizontalAlignment(JTextField.CENTER);
		eName.setHorizontalAlignment(JTextField.CENTER);

		cont.setIcon(controlImg);
		cont.setOpaque(false);
		cont.setContentAreaFilled(false);
		cont.setBorderPainted(false);

		start.setIcon(startImg);
		start.setOpaque(false);
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);

		back.setIcon(backImg);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);

		single.setIcon(singleImg);
		single.setOpaque(false);
		single.setContentAreaFilled(false);
		single.setBorderPainted(false);

		multi.setIcon(multiImg);
		multi.setOpaque(false);
		multi.setContentAreaFilled(false);
		multi.setBorderPainted(false);

		backS.setIcon(backImg);
		backS.setOpaque(false);
		backS.setContentAreaFilled(false);
		backS.setBorderPainted(false);

		backM.setIcon(backImg);
		backM.setOpaque(false);
		backM.setContentAreaFilled(false);
		backM.setBorderPainted(false);

		host.setIcon(hostImg);
		host.setOpaque(false);
		host.setContentAreaFilled(false);
		host.setBorderPainted(false);

		hostS.setIcon(hostImg);
		hostS.setOpaque(false);
		hostS.setContentAreaFilled(false);
		hostS.setBorderPainted(false);

		join.setIcon(joinImg);
		join.setOpaque(false);
		join.setContentAreaFilled(false);
		join.setBorderPainted(false);

		joinS.setIcon(joinImg);
		joinS.setOpaque(false);
		joinS.setContentAreaFilled(false);
		joinS.setBorderPainted(false);

		joinH.setIcon(startImg);
		joinH.setOpaque(false);
		joinH.setContentAreaFilled(false);
		joinH.setBorderPainted(false);

		mFrame.setContentPane(createMainMenu());

		mFrame.setSize(700, 700);
		mFrame.setLocationRelativeTo(null);
		mFrame.setResizable(false);
		mFrame.setVisible(true);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createStartMenu());
				mFrame.validate();
			}

		});

		cont.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				mFrame.setContentPane(createContMenu());
				mFrame.validate();

			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createMainMenu());
				mFrame.validate();

			}

		});

		multi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createMultiMenu());
				mFrame.validate();

			}

		});

		single.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runGame();
			}

		});

		host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createHostMenu());
				mFrame.validate();
			}

		});

		hostS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int answer = 0;
				String x = eNumber.getText();
				try {
					answer = Integer.parseInt(x);
					if (!(answer >= 1 && answer <= 4)) {
						System.out
								.println("Please enter a number between 1 - 4");
					}
				} catch (NumberFormatException e) {
					System.out
							.print("Invalid input!\nPlease enter a number between 1 - 4");
				}

				if (answer >= 1 && answer <= 4) {
					// mFrame.dispose();
					mFrame.setSize(535, 360);
					mFrame.setContentPane(createServerMenu());
					mFrame.setResizable(true);
					mFrame.setLocationRelativeTo(null);

					// new AdvenGame(answer,true,null);
					AdventureGame g = new AdventureGame(GAME_TYPE_SERVER,
							answer, null, serverPrint);
					g.start();
					mFrame.validate();

				}

			}

		});

		joinS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.dispose();
				//new AdvenGame(0, false, eName.getText());
				AdventureGame g = new AdventureGame(GAME_TYPE_CLIENT, 0, eName.getText(), serverPrint);
				g.start();
			}

		});

		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createJoinMenu());
				mFrame.validate();
			}

		});

		backS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createStartMenu());
				mFrame.validate();
			}

		});

		backM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(createMultiMenu());
				mFrame.validate();
			}

		});

		joinH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!eName.getText().equals("")) {
					String serverName = eName.getText();
					//serverPrint.append(serverName);
					AdventureGame a = new AdventureGame(GAME_TYPE_CLIENT, 0,
							serverName, serverPrint);
					a.start();
					joinH.setEnabled(false);
				}
			}

		});

	}

	public JPanel createServerMenu() {
		JPanel p = new JPanel(null);
		p.setBackground(Color.BLACK);
		serverPrint.setBounds(10, 10, 500, 160);
		eName.setBounds(10, 170, 500, 30);
		joinH.setBounds(110, 205, bWidth, bHeight);

		p.add(serverPrint);
		p.add(eName);
		p.add(joinH);
		return p;
	}

	public JPanel createJoinMenu() {
		JPanel p = new JPanel(null);
		JLabel gameTitle = new JLabel(new ImageIcon(title));
		p.setBackground(Color.black);
		gameTitle.setBounds(20, -50, title.getWidth(), title.getHeight());

		eName.setBounds(195, 300, bWidth, 30);
		joinS.setBounds(195, 350, bWidth, bHeight);
		backM.setBounds(195, 450, bWidth, bHeight);

		p.add(gameTitle);
		p.add(eName);
		p.add(joinS);
		p.add(backM);

		return p;
	}

	public JPanel createContMenu() {
		JPanel p = new JPanel(null);
		JLabel gameTitle = new JLabel(new ImageIcon(title));
		JTextArea controlText = new JTextArea();
		p.setBackground(Color.black);

		back.setBounds(40, 500, bWidth, bHeight);
		gameTitle.setBounds(20, -50, title.getWidth(), title.getHeight());

		p.add(gameTitle);
		p.add(back);

		controlText.setForeground(Color.RED);
		controlText.setOpaque(false);
		Font font = new Font("Verdana", Font.BOLD, 20);
		// Add some text:
		controlText.setFont(font);
		controlText.append("	                    Keys:\n");
		controlText.append(" 	     Move: Arrow Keys\n");
		controlText.append(" 	     Attack:  A Key\n");
		controlText.append(" 	     Next Item:  W Key\n");
		controlText.append("	     Prev Item:  E Key\n");
		controlText.append("	     Pick Up Item:  R Key\n");
		controlText.append("	     Use Item:  F Key\n");

		controlText.setBounds(-125, 275, 500, 300);

		p.add(controlText);

		return p;

	}

	public JPanel createMainMenu() {
		JPanel p = new JPanel(null);
		JLabel gameTitle = new JLabel(new ImageIcon(title));
		p.setBackground(Color.black);
		gameTitle.setBounds(20, -50, title.getWidth(), title.getHeight());
		cont.setBounds(40, 400, bWidth, bHeight);
		start.setBounds(40, 300, bWidth, bHeight);
		p.add(gameTitle);
		p.add(start);
		p.add(cont);

		return p;
	}

	public JPanel createStartMenu() {
		JPanel p = new JPanel(null);
		JLabel gameTitle = new JLabel(new ImageIcon(title));
		p.setBackground(Color.black);
		gameTitle.setBounds(20, -50, title.getWidth(), title.getHeight());

		single.setBounds(40, 250, bWidth, bHeight);
		multi.setBounds(40, 350, bWidth, bHeight);
		back.setBounds(40, 450, bWidth, bHeight);

		p.add(gameTitle);
		p.add(single);
		p.add(multi);
		p.add(back);

		return p;
	}

	public JPanel createMultiMenu() {
		JPanel p = new JPanel(null);
		JLabel gameTitle = new JLabel(new ImageIcon(title));
		p.setBackground(Color.black);
		gameTitle.setBounds(20, -50, title.getWidth(), title.getHeight());

		join.setBounds(40, 250, bWidth, bHeight);
		host.setBounds(40, 350, bWidth, bHeight);
		backS.setBounds(40, 450, bWidth, bHeight);

		p.add(gameTitle);
		p.add(join);
		p.add(host);
		p.add(backS);

		return p;
	}

	public JPanel createHostMenu() {
		JPanel p = new JPanel(null);
		JLabel gameTitle = new JLabel(new ImageIcon(title));
		p.setBackground(Color.black);
		gameTitle.setBounds(20, -50, title.getWidth(), title.getHeight());

		eNumber.setBounds(195, 300, bWidth, 30);
		hostS.setBounds(195, 350, bWidth, bHeight);
		backM.setBounds(195, 450, bWidth, bHeight);

		p.add(gameTitle);
		p.add(eNumber);
		p.add(hostS);
		p.add(backM);

		return p;
	}

	public void runGame() {
		mFrame.dispose();
		AdventureGame game = new AdventureGame(GAME_TYPE_SINGLE, 0, null,
				serverPrint);
		game.start();
	}

	public static void main(String[] args) {
		new Welcome();
	}

}
