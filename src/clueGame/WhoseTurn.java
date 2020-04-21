/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

//Creates a JPanel that displays whose turn with the south menu Panel
public class WhoseTurn extends JPanel{
	public JTextField currentPlayer = new JTextField(20);
	Board board = Board.getInstance();

	public WhoseTurn() {
		setLayout(new GridLayout(1,1));

		JLabel whoseTurn = new JLabel("Whose Turn?");


		currentPlayer.setEditable(false);
		add(whoseTurn);
		add(currentPlayer);
		setBorder(new EtchedBorder());
		updateText();
	}
	
	// Allows the JTextField to be updated throughout the program
	public void updateText() {
		//System.out.println(board.getCurrentPlayerIndex());
		if (board.getCurrentPlayerIndex() != -1) {
			
			currentPlayer.setText(board.getPlayers()[board.getCurrentPlayerIndex()].getPlayerName());
		}
		else {
			currentPlayer.setText(board.getPlayers()[0].getPlayerName());
		}
	}
}

