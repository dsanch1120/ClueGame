/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//This class creates the Accusation Button that is placed in the South Menu
public class AccusationButton extends JButton{
	JButton button;
	Board board = Board.getInstance();
	//Makes the Button
	public AccusationButton() {
		button = new JButton("Make an Accusation");
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
	}
	public JButton getButton() {
		return button;
	}

	private class ButtonListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			if (board.getCurrentPlayerIndex() > -1) {
				if (board.getPlayers()[board.getCurrentPlayerIndex()].getType() != PlayerType.HUMAN) {
					JOptionPane.showMessageDialog(button, "Not your turn!");
				} else if (!board.getAccusationClicked()){
					MakeAnAccusation accusation = new MakeAnAccusation();
					board.setAccusationClicked(true);
					board.setHasMoved(true);
					board.repaint();
				} else {
					JOptionPane.showMessageDialog(button, "You can either move once or make 1 accusation per turn!");
				}
			}
		}


	}

	public class errorMessage extends JDialog {
		public errorMessage() {

			JOptionPane.showMessageDialog(this, "Can't click that yet!");
		}
	}
}
