/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package tests;

/*
 * This program tests that config files are loaded properly.
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 25;

	
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt", null, null);		//FIXME added two nulls as arguments 	
		board.initialize();
	}
	
	@Test
	public void testRooms() {												//test that initials match up with the room name
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Rec Room", legend.get('R'));
		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Family Room", legend.get('F'));
		assertEquals("Guest room", legend.get('G'));
		assertEquals("Nursery", legend.get('N'));
		assertEquals("Study", legend.get('S'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
		@Test
		public void FourDoorDirections() {
			BoardCell room = board.getCellAt(4, 3);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(3, 9);									//test 4 different doors and their directions
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
					if (cell.isDoorway()) {
						numDoors++;											//calculate total number of doorways
					}
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
			assertEquals('W', board.getCellAt(6, 14).getInitial());
																			// Test the closet
			assertEquals('X', board.getCellAt(11, 13).getInitial());
		}

}
