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
	JTextField guess = new JTextField(30);

	
	public Guess() {
		setLayout(new GridLayout(1,1));
		JLabel title = new JLabel("Guess");
		add(title);
		guess.setEditable(false);
		setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
	}
	
}
