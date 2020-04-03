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
			BoardCell compPick = compPlayer.selectTarget(board.getTargets());
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
			BoardCell compPick = compPlayer.selectTarget(board.getTargets());
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
			BoardCell compPick = compPlayer.selectTarget(board.getTargets());
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
	//@Test
	public void checkAccusation() {
		Solution gameSolution = new Solution(board.getSolution().person,board.getSolution().room,board.getSolution().weapon);
		//Test passes when solution is correct
		assert(board.checkAccusation(gameSolution));

		gameSolution.person = "";

		//Test should fail, person is incorrect, room and weapon are correct.

		assert(board.checkAccusation(gameSolution) != true);

		gameSolution.person = board.getSolution().person;
		gameSolution.room = "";

		//Test should fail, room is incorrect, person and weapon are correct.
		assert(!(board.checkAccusation(gameSolution)));

		gameSolution.weapon = board.getSolution().weapon;
		gameSolution.weapon = "";

		//Test should fail, weapon is incorrect, room and person are correct.
		assert(!(board.checkAccusation(gameSolution)));

	}


	//@Test
	public void checkSuggesion() {

		//Checks that the player's room suggestion is the same as the room they are in.
		ComputerPlayer compPlayer = new ComputerPlayer();
		compPlayer.setBoard(board.getBoard());
		compPlayer.setLegend(board.getLegend());

		compPlayer.updateRoomsSeen(board.getCards()[0]);
		compPlayer.updatePeopleSeen(board.getCards()[9]);
		compPlayer.updateWeaponsSeen(board.getCards()[15]);

		compPlayer.setRow(24);
		compPlayer.setColumn(0);
		String name = board.getLegend().get(board.getCellAt(compPlayer.getRow(), compPlayer.getColumn()).getInitial());

		Solution sol = compPlayer.createSuggestion();
		assertEquals(name, sol.room);

		//Checks that the people and weapons guessed are random if more than one are not seen
		compPlayer.updatePeopleSeen(board.getCards()[10]);
		compPlayer.updateWeaponsSeen(board.getCards()[16]);
		compPlayer.updatePeopleSeen(board.getCards()[11]);
		compPlayer.updateWeaponsSeen(board.getCards()[17]);
		compPlayer.updatePeopleSeen(board.getCards()[12]);
		compPlayer.updateWeaponsSeen(board.getCards()[18]);

		int mustards = 0;
		int scarletts = 0;
		int knifes = 0;
		int revolvers = 0;

		for (int i = 0; i < 1000; i++) {
			Solution solution = compPlayer.createSuggestion();
			//System.out.println(solution.person);
			if(solution.person.equals("Colonel Mustard")) {
				mustards++;
			} if(solution.person.equals("Miss Scarlett")) {
				scarletts++;
			} if(solution.weapon.equals("Knife")) {
				knifes++;
			} if (solution.weapon.equals("Revolver")) {
				revolvers++;
			}
		}

		assertEquals(1000,(mustards+scarletts));
		assert(mustards < 600 && mustards > 400 && scarletts < 600 && scarletts > 400);

		assertEquals(1000,(knifes+revolvers));
		assert(knifes < 600 && knifes > 400 && revolvers < 600 && revolvers > 400);


		//Checks that computer chooses the unseen person and weapon if 5 are seen.
		compPlayer.updatePeopleSeen(board.getCards()[13]);
		compPlayer.updateWeaponsSeen(board.getCards()[19]);

		int counter = 0;
		for (int i = 0; i < 100; i++) {
			Solution solution = compPlayer.createSuggestion();
			if (solution.person.equals("Colonel Mustard") && solution.weapon.contentEquals("Revolver")) {
				counter++;
			}
		}
		assertEquals(counter, 100);
	}

	@Test
	//check disproveSuggestion method of the Player class
	public void checkDisproveSuggestion() {
		
		Solution solution = new Solution ("Mrs. Peacock", "Study", "Rope");
		ComputerPlayer compPlayer = new ComputerPlayer();
		compPlayer.setBoard(board.getBoard());
		compPlayer.setLegend(board.getLegend());

		compPlayer.addToHand(board.getCards()[0]);
		compPlayer.addToHand(board.getCards()[9]);
		compPlayer.addToHand(board.getCards()[15]);
		//check that disproveSuggestion returns null when the player has none of the cards
		assert (compPlayer.disproveSuggestion(solution) == null);
		//give computer player one of the cards
		compPlayer.addToHand(board.getCards()[11]);
		//test to make sure the one card we added is the one that gets returned
		assert (compPlayer.disproveSuggestion(solution).equals(board.getCards()[11]));
		compPlayer.addToHand(board.getCards()[6]);
		
		//the counts for the possible cards the player could show
		int countCard1 = 0;
		int countCard2 = 0;
		
		for (int i = 0; i < 1000; i++) {
			Card temp = compPlayer.disproveSuggestion(solution);
			if (temp.equals(board.getCards()[11])) {
				countCard1++;
			} else if (temp.equals(board.getCards()[6])) {
				countCard2++;
			} else {
			}
		}
		//check to make sure each card the player could have shown is shown randomly
		assertEquals((countCard1+countCard2), 1000);
		assert(countCard1 < 600 && countCard1 > 400);
		assert(countCard2 < 600 && countCard2 > 400);
		
	}




	}
