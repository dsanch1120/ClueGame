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
	private DieRoll dieRoll;
	private Boolean firstTurn = true;
	public static final int playerLength = 6;









	public NextPlayerButton(WhoseTurn whoseTurn, DieRoll dieRoll) {
		this.whoseTurn = whoseTurn;
		this.dieRoll = dieRoll;
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
			if(board.getCurrentPlayerIndex() != -1 && board.getPlayers()[playerCounter].getType().equals(PlayerType.HUMAN)){
				if(!board.getHasMoved()) {
					errorMessage message = new errorMessage();
					return;
				} 
			}

			if (board.getCurrentPlayerIndex() != -1) {
				playerCounter = (playerCounter +1)%playerLength; 
			} else {
				playerCounter = 0;
			}
			board.setCurrentPlayerIndex(playerCounter);
			board.roll();
			dieRoll.updateText();
			whoseTurn.updateText();

			if (board.getCurrentPlayerIndex() != -1 && board.getPlayers()[playerCounter].getType().equals(PlayerType.COMPUTER)) {
				ComputerPlayer temp = (ComputerPlayer) board.getPlayers()[playerCounter];
				board.calcTargets(board.getPlayers()[playerCounter].getRow(), board.getPlayers()[playerCounter].getColumn(), board.getRoll());
				BoardCell tempCell = temp.selectTarget(board.getTargets());

				board.getPlayers()[playerCounter].setRow(tempCell.getRow());
				board.getPlayers()[playerCounter].setColumn(tempCell.getColumn());

				board.setCurrentPlayerIndex(playerCounter);


			}


			board.repaint();
			board.setHasMoved(false);

		}
	}

	public class errorMessage extends JDialog {
		public errorMessage() {

			JOptionPane.showMessageDialog(this, "Can't click that yet!");
		}
	}



}
