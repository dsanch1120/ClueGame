/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class SouthMenu extends JPanel {

	
	public SouthMenu() {
		Board board = Board.getInstance();
		Guess guess = new Guess();
		board.setGuess(guess);

		setLayout(new GridLayout(2,3));
		WhoseTurn whoseTurn = new WhoseTurn();
		DieRoll dieRoll = new DieRoll();
		GuessResponse guessResponse = new GuessResponse();
		board.setGuessResponse(guessResponse);
		add(whoseTurn);
		add(new NextPlayerButton(whoseTurn, dieRoll, guessResponse).getButton());
		add(new AccusationButton().getButton());
		add(dieRoll);
		add(guess);

		add(guessResponse);
		
	}
}
