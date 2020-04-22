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

//Creates the JPanel that displays the player's guess when they make a suggestion within the southMenu panel.
public class Guess extends JPanel{
	JTextField guess = new JTextField(50);

	
	public Guess() {
		setLayout(new GridLayout(1,2));
		JLabel title = new JLabel("Guess");
		add(title);
		guess.setEditable(false);
		setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		add(guess);
	}
	
	public void updateGuess(String person, String room, String weapon) {
		String tempGuess = "";
		tempGuess = person + " | " + room + " | " + weapon;
		
		guess.setText(tempGuess);
	}
	public void clearGuess() {
		guess.setText("");
	}
}
