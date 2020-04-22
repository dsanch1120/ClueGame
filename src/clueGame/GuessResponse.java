/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

//Creates the JPanel that displays the response to the player's guess within the southMenu panel
public class GuessResponse extends JPanel{
	
	private JTextField response = new JTextField(10);
	Board board = Board.getInstance();

	public GuessResponse() {
		setLayout(new GridLayout(1,1));

		JLabel title = new JLabel("Response");
		response.setEditable(false);
		add(title);
		add(response);
		setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
	}
	
	//Allows the JTextField to be updated throughout the program
	public void updateText(Card card) {
		if (card != null) {
			response.setText(card.getCardName());
		}
		else {
			response.setText("No new clue");
		}
	}
	
	public void clearText() {
		response.setText("");
	}
	

}
