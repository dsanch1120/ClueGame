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

//Creates the JPanel that displays the die roll within the southMenu JPanel
public class DieRoll extends JPanel{
	JTextField roll = new JTextField(1);
	Board board = Board.getInstance();

	
	public DieRoll() {
		setLayout(new GridLayout(1,1));

		JLabel rollTitle = new JLabel("Roll");
		
		roll.setEditable(false);
		add(rollTitle);
		add(roll);
		setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		updateText();
	}
	
	//Allows the JTextField to be updated throughout the game
	public void updateText() {
		if (board.getCurrentPlayerIndex() != -1) {
			roll.setText(Integer.toString(board.getRoll()));
		}
		else {
			roll.setText("0");
		}
	}
	
}
