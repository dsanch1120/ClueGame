package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;




public class ClueGame extends JFrame{

	private JTextField currentPlayer;
	private JTextField roll;
	private Board board;

	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt",  "people.txt", "cards.txt");
		board.initialize();

		setSize(new Dimension (850, 850));
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds board to JFrame
		//JPanel cBoard = createBoard();
		//add(cBoard, BorderLayout.CENTER);

		//Adds player hand to right side of JFrame
		JPanel pCards = createCards();
		Dimension eastSideDim = new Dimension();
		eastSideDim.width = 100;
		pCards.setPreferredSize(eastSideDim);
		add(pCards, BorderLayout.EAST);

		//Adds south Menu to south of JFrame
		JPanel southMenu = createSouthMenu();
		add(southMenu, BorderLayout.SOUTH);

		//Adds menu to JFrame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		add(Board.getInstance(), BorderLayout.CENTER);
	}



	private JPanel createSouthMenu() {
		class southMenu extends JPanel {
			public southMenu() {
				setLayout(new GridLayout(2,3));
				//Row 1, Column 1
				JPanel panel = createWhoseTurn();
				add(panel);

				//Row 1, Column 2
				panel = createNextPlayerButton();
				add(panel);

				//Row 1, Column3
				panel = createAccusationButton();
				add(panel);

				//Row 2, Column 1
				panel = createDieRoll();
				add(panel);

				//Row 2, Column 2
				panel = createGuess();
				add(panel);

				//Row 2, Column 3
				panel = createGuessResponse();
				add(panel);
			}
		}

		southMenu output = new southMenu();

		return output;
	}


	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createShowDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createShowDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Show Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				DetectiveNotes dn = new DetectiveNotes();
				dn.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	private JPanel createWhoseTurn() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));

		JLabel whoseTurn = new JLabel("Whose Turn?");
		currentPlayer = new JTextField(20);
		//currentPlayer = "M"
		currentPlayer.setEditable(false);
		panel.add(whoseTurn);
		panel.add(currentPlayer);
		panel.setBorder(new EtchedBorder());
		return panel;
	}

	private JPanel createNextPlayerButton() {
		JButton next = new JButton("Next Player");
		JPanel panel = new JPanel();
		panel.add(next);

		return panel;
	}

	private JPanel createAccusationButton() {
		JButton next = new JButton("Make an Accusation");
		JPanel panel = new JPanel();
		panel.add(next);

		return panel;
	}

	private JPanel createDieRoll() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));

		JLabel rollTitle = new JLabel("Roll");
		roll = new JTextField(1);
		roll.setEditable(false);
		panel.add(rollTitle);
		panel.add(roll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));

		return panel;
	}

	private JPanel createGuess() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));

		JLabel rollTitle = new JLabel("Guess");
		roll = new JTextField(30);
		roll.setEditable(false);
		panel.add(rollTitle);
		panel.add(roll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		return panel;
	}

	private JPanel createGuessResponse() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));

		JLabel rollTitle = new JLabel("Response");
		roll = new JTextField(1);
		roll.setEditable(false);
		panel.add(rollTitle);
		panel.add(roll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		return panel;
	}

	private JPanel createCards() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));

		JPanel peopleTitle = new JPanel();
		JPanel roomTitle = new JPanel();
		JPanel weaponTitle = new JPanel();

		peopleTitle.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		roomTitle.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		weaponTitle.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		panel.add(peopleTitle);
		panel.add(roomTitle);
		panel.add(weaponTitle);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));

		return panel;
	}

	public static void main(String[] args) {

		ClueGame cp = new ClueGame();
		cp.setVisible(true);
	}
}
