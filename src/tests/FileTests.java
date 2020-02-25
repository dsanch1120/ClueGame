/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package tests;

/*
 * This program tests that config files are loaded properly.
 */

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 25;

	// NOTE: I made Board static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test.
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("layout.csv", "rooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Rec Room", legend.get('R'));
		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Family Room", legend.get('F'));
		assertEquals("Guest room", legend.get('G'));
		assertEquals("Nursery", legend.get('N'));
		assertEquals("Study", legend.get('S'));
		assertEquals("Dining", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell room = board.getCellAt(4, 3);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(3, 9);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCellAt(9, 20);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(18, 2);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(23, 23);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(15, 14);
			assertFalse(cell.isDoorway());		

		}
	
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(18, numDoors);
		}
	
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('K', board.getCellAt(2, 1).getInitial());
			assertEquals('R', board.getCellAt(1, 7).getInitial());
			assertEquals('B', board.getCellAt(2, 16).getInitial());
			// Test last cell in room
			assertEquals('F', board.getCellAt(1, 24).getInitial());
			assertEquals('G', board.getCellAt(24, 0).getInitial());
			// Test a walkway
			assertEquals('W', board.getCellAt(6, 514).getInitial());
			// Test the closet
			assertEquals('C', board.getCellAt(11, 13).getInitial());
		}

}
