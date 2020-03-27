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

public class gameSetupTests {
	public static final int DECK_SIZE = 21;
	public static final int NUM_PEOPLE = 6;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt");		
		board.initialize();
		
		
	}
	
}
