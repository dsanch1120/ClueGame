/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;

import javax.swing.JButton;
import javax.swing.JPanel;

//This class creates the Accusation Button that is placed in the South Menu
public class AccusationButton extends JButton{
	JButton button;
	//Makes the Button
	public AccusationButton() {
		button = new JButton("Make an Accusation");
	}
	public JButton getButton() {
		return button;
	}

}
