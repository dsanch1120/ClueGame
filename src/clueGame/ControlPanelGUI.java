package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanelGUI extends JPanel {

	private JTextField currentPlayer;
	private JTextField roll;

	public ControlPanelGUI() {
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(500,150);
		
		ControlPanelGUI cp = new ControlPanelGUI();
		frame.add(cp, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
