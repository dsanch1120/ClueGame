/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board.Listener.errorMessage;

//JPanel that displays the "Next Player" button within the south menu panel.
public class NextPlayerButton extends JPanel{
	//Creates an instance of the Board class
	private Board board = Board.getInstance();
	private JButton button;
	//Creates an instance of the Button Listener class
	private ButtonListener listener = new ButtonListener();
	private int playerCounter;
	//private Boolean hasBeenClicked = false;
	private WhoseTurn whoseTurn;
	private DieRoll dieRoll;
	//Boolean variable that determines whether or not it is the first turn.
	private Boolean firstTurn = true;
	//Static int to be used throughout the class. Equal to 6, which is the number of players.
	public static final int playerLength = 6;

	//Constructor for the "Next Player" button
	public NextPlayerButton(WhoseTurn whoseTurn, DieRoll dieRoll) {
		this.whoseTurn = whoseTurn;
		this.dieRoll = dieRoll;
		this.button = new JButton("Next Player");
		this.button.addActionListener(listener);
		this.playerCounter = 0;
	}
	
	//Getter method
	public JButton getButton() {
		return button;
	}

	//Class which will handles what happens when the user clicks the "next player" button
	private class ButtonListener implements ActionListener{

		//Controls what happens when the button is clicked.
		@Override
		public void actionPerformed(ActionEvent e) {
			//Ensures that the board object used in this class is current.
			board = Board.getInstance();
			
			//If statements ensure that the JDialog box is only called when necessary
			if(board.getCurrentPlayerIndex() != -1 && board.getPlayers()[playerCounter].getType().equals(PlayerType.HUMAN)){
				if(!board.getHasMoved()) {
					//Calls the JDialog box and returns from the method.
					errorMessage message = new errorMessage();
					return;
				} 
			}

			//Increments the player so that the code afterwards is correctly handled.
			if (board.getCurrentPlayerIndex() != -1) {
				playerCounter = (playerCounter +1)%playerLength; 
			} else {
				//If the Current Player Index is equal to -1, this means that it is the beginning of the game 
				//	and the "next player" button was pressed to begin the game. As such, it will change the 
				//	index to 0, so that the first player will go first.
				playerCounter = 0;
			}
			//Updates the playercounter of the board class
			board.setCurrentPlayerIndex(playerCounter);
			//"Rolls" the die
			board.roll();
			//Updates the JTextField for the die roll and whose turn panels
			dieRoll.updateText();
			whoseTurn.updateText();

			//If the current player is a computer, the targets are calculated and the player's location is changed.
			if (board.getCurrentPlayerIndex() != -1 && board.getPlayers()[playerCounter].getType().equals(PlayerType.COMPUTER)) {
				ComputerPlayer temp = (ComputerPlayer) board.getPlayers()[playerCounter];
				board.calcTargets(board.getPlayers()[playerCounter].getRow(), board.getPlayers()[playerCounter].getColumn(), board.getRoll());
				BoardCell tempCell = temp.selectTarget(board.getTargets());

				board.getPlayers()[playerCounter].setRow(tempCell.getRow());
				board.getPlayers()[playerCounter].setColumn(tempCell.getColumn());

				board.setCurrentPlayerIndex(playerCounter);
			}

			//Repaints the board and modifies boolean for next time this method is called.
			board.repaint();
			board.setHasMoved(false);

		}
	}

	//JDialog box that displays an "error" message when the user attempts to go on to their next turn before moving or making an accusation
	public class errorMessage extends JDialog {
		public errorMessage() {

			JOptionPane.showMessageDialog(this, "Can't click that yet!");
		}
	}



}
