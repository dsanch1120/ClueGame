/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package tests;

/*
 * This program tests 
 * 1. The game correctly loads the people
 * 2. The game correctly loads the cards
 * 3. The game properly deals the cards.
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

import java.awt.Color;

public class gameSetupTests {
	public static final int DECK_SIZE = 21;
	public static final int NUM_PEOPLE = 6;
	
	private static Board board;
	
	//Gets everything ready before the tests
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt", "people.txt", "cards.txt");		
		board.initialize();
	}
	
	//This tests ensures that the program correctly loads and implements the "people.txt" file
	@Test
	public void testPeople() {
		Player[] people = board.getPlayers();
		//Tests if the Array of people contains 6 players
		assertEquals(6, people.length);
		
		//Tests that the people loaded correctly
		assertEquals(people[0].getPlayerName(), "Mrs. White");
		assertEquals(people[1].getPlayerName(), "Mr. Green");
		assertEquals(people[2].getPlayerName(), "Mrs. Peacock");
		assertEquals(people[3].getPlayerName(), "Professor Plum");
		assertEquals(people[4].getPlayerName(), "Miss Scarlett");
		assertEquals(people[5].getPlayerName(), "Colonel Mustard");
		
		//Tests that the colors loaded correctly
		assertEquals(people[0].getColor(), Color.white);
		assertEquals(people[1].getColor(), Color.green);
		assertEquals(people[2].getColor(), Color.blue);
		assertEquals(people[3].getColor(), Color.magenta);
		assertEquals(people[4].getColor(), Color.red);
		assertEquals(people[5].getColor(), Color.yellow);
		
		//Tests that the row coordinates loaded correctly
		assertEquals(people[0].getRow(), 24);
		assertEquals(people[1].getRow(), 24);
		assertEquals(people[2].getRow(), 15);
		assertEquals(people[3].getRow(), 0);
		assertEquals(people[4].getRow(), 0);
		assertEquals(people[5].getRow(), 18);
		
		//Tests that the column coordinates loaded correctly
		assertEquals(people[0].getColumn(), 6);
		assertEquals(people[1].getColumn(), 13);
		assertEquals(people[2].getColumn(), 0);
		assertEquals(people[3].getColumn(), 18);
		assertEquals(people[4].getColumn(), 4);
		assertEquals(people[5].getColumn(), 24);
		
	
	}
}
