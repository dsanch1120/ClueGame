/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package tests;

/*
 * This will test
 * 1. The Computer player's ability to properly select a target location
 * 2. The Board class's ability to check a player accusation
 * 3. The Player's ability to disprove a suggestion
 * 4. The Board class's ability to handle a suggestion
 * 5. The Computer Player's ability to create a suggestion
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class gameActionTests {

	private static Board board;

	//Gets everything ready before the tests
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt", "people.txt", "cards.txt");		
		board.initialize();
	}

	//Tests if the computer can properly select a target location that makes sense
	@Test
	public static void computerTarget() {
		ComputerPlayer compPlayer = new ComputerPlayer();
		compPlayer.setRow(24);
		compPlayer.setColumn(13);
		board.calcTargets(24,13,3);
		BoardCell compPick = compPlayer.pickLocation(board.getTargets());
		
		
	}


}
