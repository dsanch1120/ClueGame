package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board.Listener.errorMessage;

public class NextPlayerButton extends JPanel{
	private Board board = Board.getInstance();
	private JButton button;
	private ButtonListener listener = new ButtonListener();
	private int playerCounter;
	//private Boolean hasBeenClicked = false;
	private WhoseTurn whoseTurn;
	private int oldX = 6;
	private int oldY = 24;
	private Boolean firstTurn = true;









	public NextPlayerButton(WhoseTurn whoseTurn) {
		this.whoseTurn = whoseTurn;
		this.button = new JButton("Next Player");
		this.button.addActionListener(listener);
		this.playerCounter = 0;
	}
	public JButton getButton() {
		return button;
	}

	private class ButtonListener implements ActionListener{

		@Override

		public void actionPerformed(ActionEvent e) {
			board = Board.getInstance();
			boolean showError = false;
			board.roll();
			board.setCurrentPlayerIndex(playerCounter);
			whoseTurn.updateText();

			board.repaint();
			
			if (!firstTurn && board.getPlayers()[playerCounter].getType().equals(PlayerType.HUMAN) && oldX == board.getHumanX() && oldY == board.getHumanY()) {
				errorMessage message = new errorMessage();
				showError = true;
				board.repaint();
			}
			if (!showError) {
				firstTurn = false;
				if (board.getPlayers()[playerCounter].getType().equals(PlayerType.COMPUTER)) {
					ComputerPlayer temp = (ComputerPlayer) board.getPlayers()[playerCounter];
					board.calcTargets(board.getPlayers()[playerCounter].getRow(), board.getPlayers()[playerCounter].getColumn(), board.getRoll());
					BoardCell tempCell = temp.selectTarget(board.getTargets());

					board.getPlayers()[playerCounter].setRow(tempCell.getRow());
					board.getPlayers()[playerCounter].setColumn(tempCell.getColumn());
					
					oldX = board.getPlayers()[0].getColumn();
					oldY = board.getPlayers()[0].getRow();
					
					


				}
				playerCounter++;
				if(playerCounter == 6) {
					playerCounter = 0;
					board.setHasMoved(true);
				} else {
					board.setHasMoved(false);
				}


				//board.setHasMoved(false);
				board.repaint();
			}

		}
	}

	public class errorMessage extends JDialog {
		public errorMessage() {

			JOptionPane.showMessageDialog(this, "Can't click that yet!");
		}
	}



}
