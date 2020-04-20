/*
 * Authored by Daniel Sanchez and Trent Douglas
 */

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;




public class ClueGame extends JFrame{

	private JTextField currentPlayer;
	private JTextField roll;
	private Board board;
	


	public ClueGame() {
		//Configures board to be used throughout object
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt",  "people.txt", "cards.txt");
		board.initialize();



		setSize(new Dimension (850, 850));
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JOptionPane.showMessageDialog(this, "You are " + board.getPlayers()[0].getPlayerName() + ", press Next Player to begin play");

		//Adds player hand to right side of JFrame
		JPanel pCards = createCards();
		Dimension eastSideDim = new Dimension();
		eastSideDim.width = 150;
		pCards.setPreferredSize(eastSideDim);
		add(pCards, BorderLayout.EAST);

		/*
		//Adds south Menu to south of JFrame
		JPanel southMenu = createSouthMenu();
		add(southMenu, BorderLayout.SOUTH);
*/
		
		JPanel southMenu = new JPanel();
		southMenu.setLayout(new GridLayout(2,3));
		WhoseTurn whoseTurn = new WhoseTurn();
		DieRoll dieRoll = new DieRoll();
		southMenu.add(whoseTurn);
		southMenu.add(new NextPlayerButton(whoseTurn, dieRoll).getButton());
		southMenu.add(new AccusationButton().getButton());
		southMenu.add(dieRoll);
		southMenu.add(new Guess());
		southMenu.add(new GuessResponse());
		
		
		
		add(southMenu, BorderLayout.SOUTH);
		//Adds menu to JFrame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		//Adds the board itself to the JFrame
		add(Board.getInstance(), BorderLayout.CENTER);
	}

	
	//Creates the File Menu
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createShowDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}

	//Handles the "Show Detective Notes" option on the file menu
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

	//Handles the "Exit" option on the file menu
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

	//Creates "Cards" JPanel to be shown on the East Side of JFrame
	private JPanel createCards() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));

		JPanel peopleTitle = new JPanel();
		peopleTitle.setLayout(new GridLayout(9,1));
		JPanel roomTitle = new JPanel();
		roomTitle.setLayout(new GridLayout(9,1));
		JPanel weaponTitle = new JPanel();
		weaponTitle.setLayout(new GridLayout(9,1));


		peopleTitle.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		roomTitle.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		weaponTitle.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		for (Card card : board.getPlayers()[0].getHand()) {
			if (card.getType().equals(CardType.PERSON)) {
				JLabel cardName = new JLabel(card.getCardName());
				peopleTitle.add(cardName);
			}
			if (card.getType().equals(CardType.ROOM)) {
				JLabel cardName = new JLabel(card.getCardName());
				roomTitle.add(cardName);
			}
			if (card.getType().equals(CardType.WEAPON)) {
				JLabel cardName = new JLabel(card.getCardName());
				weaponTitle.add(cardName);
			}
		}



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
