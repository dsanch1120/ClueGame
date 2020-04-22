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
	private GuessResponse guessResponse;
	//Boolean variable that determines whether or not it is the first turn.
	private Boolean firstTurn = true;
	//Static int to be used throughout the class. Equal to 6, which is the number of players.
	public static final int playerLength = 6;

	//Constructor for the "Next Player" button
	public NextPlayerButton(WhoseTurn whoseTurn, DieRoll dieRoll, GuessResponse guessResponse) {
		this.whoseTurn = whoseTurn;
		this.dieRoll = dieRoll;
		this.guessResponse = guessResponse;
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
			Guess guess = board.getGuess();
			guess.clearGuess();
			guessResponse.clearText();
			//If statements ensure that the JDialog box is only called when necessary
			if(board.getCurrentPlayerIndex() != -1 && board.getPlayers()[playerCounter].getType().equals(PlayerType.HUMAN)){
				if(!board.getHasMoved()) {
					//Calls the JDialog box and returns from the method.
					errorMessage message = new errorMessage();
					return;
				}
				board.setAccusationClicked(false);
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
				//board.setAccusationClicked(true);
				ComputerPlayer temp = (ComputerPlayer) board.getPlayers()[playerCounter];
				if (temp.getPeopleSeen().size() == 5 && temp.getWeaponsSeen().size() == 5 && temp.getRoomsSeen().size() == 8) {
					Solution solution = temp.makeAccusation();
					if (board.checkAccusation(solution)) {
						winMessage end = new winMessage(temp.getPlayerName(), solution.person, solution.weapon, solution.room);
						System.exit(0);
					}
				}
				
				
				
				
				
				board.calcTargets(board.getPlayers()[playerCounter].getRow(), board.getPlayers()[playerCounter].getColumn(), board.getRoll());
				BoardCell tempCell = temp.selectTarget(board.getTargets());

				board.getPlayers()[playerCounter].setRow(tempCell.getRow());
				board.getPlayers()[playerCounter].setColumn(tempCell.getColumn());

				board.setCurrentPlayerIndex(playerCounter);
				
				if (board.getCellAt(temp.getRow(), temp.getColumn()).isRoom()) {
					Solution solution = temp.createSuggestion();
					guess.updateGuess(solution.person, solution.room, solution.weapon);
					Card card = board.handleSuggestions(solution, playerCounter);
					guessResponse.updateText(card);
					
					for (Player player : board.getPlayers()) {
						if(player.getType() == PlayerType.COMPUTER && card != null) {
							ComputerPlayer cp = (ComputerPlayer) player;
							switch (card.getType()) {
							case PERSON:
								cp.updatePeopleSeen(card);
								break;
							case WEAPON:
								cp.updateWeaponsSeen(card);
								break;
							case ROOM:
								cp.updateRoomsSeen(card);
								break;
							default:
								System.out.println("error");
							}
						}
					}
			
				}
				
				
				
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
	
	public class winMessage extends JDialog {
		public winMessage(String name, String person, String weapon, String room) {

			JOptionPane.showMessageDialog(this, name + " won the game with the accusation: \n" + person + " in the " + room + " with the " + weapon);
		}
	}



}
