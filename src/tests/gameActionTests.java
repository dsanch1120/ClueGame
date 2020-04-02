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
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

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
	//@Test
	public void computerTarget() {

		//Testing for randomness with no doors as options
		ComputerPlayer compPlayer = new ComputerPlayer();
		int roll = 2;
		board.calcTargets(24,13,roll);
		HashMap<BoardCell, Integer> picks = new HashMap<BoardCell, Integer>();
		for (int i = 0; i < roll*1000; i++) {
			BoardCell compPick = compPlayer.pickLocation(board.getTargets());
			if (!picks.containsKey(compPick)) {
				picks.put(compPick, 1);
			} else {
				int count = picks.get(compPick);
				count++;
				picks.replace(compPick, count);
			}
		}
		for (int count : picks.values()) {
			assert (count < 500 && count > 300);
		}


		//Tests to ensure that computer always chooses a door if it hasn't been visited
		compPlayer = new ComputerPlayer();
		roll = 5;
		int counter = 0;
		board.calcTargets(24,13,5);
		for (int i = 0; i < 1000; i++) {
			BoardCell compPick = compPlayer.pickLocation(board.getTargets());
			if (compPick.isDoorway()) {
				counter++;
			}
		}
		assertEquals(counter, 1000);


		//Tests if computer randomly picks if door is already visited
		compPlayer = new ComputerPlayer();
		compPlayer.setVisited(board.getCellAt(22, 18));
		roll = 2;
		board.calcTargets(23,17,roll);
		picks = new HashMap<BoardCell, Integer>();
		for (int i = 0; i < roll*1000; i++) {
			BoardCell compPick = compPlayer.pickLocation(board.getTargets());
			if (!picks.containsKey(compPick)) {
				picks.put(compPick, 1);
			} else {
				int count = picks.get(compPick);
				count++;
				picks.replace(compPick, count);
			}
		}
		for (int count : picks.values()) {
			assert (count < 600 && count > 400);
		}
	}

	//Tests if the checkSolution method properly returns true and false
	@Test
	public void checkAccusation() {
		Solution gameSolution = board.getSolution();

		//Test passes when solution is correct
		assert(board.checkAccusation(gameSolution));

		gameSolution.person = "";

		//Test should fail, person is incorrect, room and weapon are correct.
		assert(!(board.checkAccusation(gameSolution)));

		gameSolution = board.getSolution();
		gameSolution.room = "";

		//Test should fail, room is incorrect, person and weapon are correct.
		assert(!(board.checkAccusation(gameSolution)));

		gameSolution = board.getSolution();
		gameSolution.weapon = "";

		//Test should fail, weapon is incorrect, room and person are correct.
		assert(!(board.checkAccusation(gameSolution)));

	}






}
