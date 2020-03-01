/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class BoardAdjTargetTests {

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


	//Locations with only walkways as adjacent locations [1pt]
	
	//Adjacency tests
	//Locations with only walkways as adjacent locations [1pt]
	
	//Tests walkways to see if all adjacent BoardCell objects are walkways
	@Test
	public void testAdjacenyOnlyWalkways() {
		Set<BoardCell> temp = board.getAdjList(16, 12);
		assert temp.contains(board.getCellAt(17,12));
		assert temp.contains(board.getCellAt(16,13));
		assert temp.contains(board.getCellAt(15,12));
		assert temp.contains(board.getCellAt(16,11));
		assert temp.size() == 4;
	}
	
	//Tests room BoardCells to ensure that the adj set is of size 0
	@Test
	public void testAdjacencyInsideRoom() {
		Set<BoardCell> temp = board.getAdjList(22, 21);
		assert temp.size() == 0;
	}
	
	//Tests walkway BoardCells to ensure correct adjacency
	@Test
	public void testAdjacencyEdge() {
		Set<BoardCell> temp = board.getAdjList(13,0);
		assert temp.contains(board.getCellAt(13, 1));
		assert temp.contains(board.getCellAt(14, 0));
		assert temp.size() == 2;
		
		temp = board.getAdjList(0, 13);
		assert temp.contains(board.getCellAt(0, 14));
		assert temp.contains(board.getCellAt(1, 13));
		assert temp.size() == 2;
		
		temp = board.getAdjList(17, 24);
		assert temp.contains(board.getCellAt(16, 24));
		assert temp.contains(board.getCellAt(18, 24));
		assert temp.contains(board.getCellAt(17, 23));
		assert temp.size() == 3;
		
		temp = board.getAdjList(24, 14);
		assert temp.contains(board.getCellAt(24, 13));
		assert temp.contains(board.getCellAt(24, 15));
		assert temp.contains(board.getCellAt(23, 14));
		assert temp.size() == 3;
	}
	
	//Tests walkway BoardCells that are not beside a doorway
	@Test
	public void testAdjancyRoomNotDoor() {
		Set<BoardCell> temp = board.getAdjList(18, 9);
		assert temp.contains(board.getCellAt(18, 8));
		assert temp.contains(board.getCellAt(18, 10));
		assert temp.contains(board.getCellAt(17, 9));
		assert temp.size() == 3;
		
		temp = board.getAdjList(17, 18);
		assert temp.contains(board.getCellAt(16, 18));
		assert temp.contains(board.getCellAt(17, 17));
		assert temp.contains(board.getCellAt(17, 19));
		assert temp.size() == 3;
	}
	
	//Tests adjacency for walkways which are adjacent to doors
	@Test
	public void testAdjacencyDoorDirection() {
		//Direction LEFT
		Set<BoardCell> temp = board.getAdjList(20, 6);
		assert temp.contains(board.getCellAt(21, 6));
		assert temp.contains(board.getCellAt(20, 7));
		assert temp.contains(board.getCellAt(20, 5));
		assert temp.contains(board.getCellAt(19, 6));
		assert temp.size() == 4;
		
		//Direction UP
		temp = board.getAdjList(17, 2);
		assert temp.contains(board.getCellAt(17, 1));
		assert temp.contains(board.getCellAt(17, 3));
		assert temp.contains(board.getCellAt(18, 2));
		assert temp.contains(board.getCellAt(16, 2));
		assert temp.size() == 4;
		
		//Direction RIGHT
		temp = board.getAdjList(10, 5);
		assert temp.contains(board.getCellAt(9, 5));
		assert temp.contains(board.getCellAt(11, 5));
		assert temp.contains(board.getCellAt(10, 6));
		assert temp.contains(board.getCellAt(10, 4));
		assert temp.size() == 4;
		
		//Direction DOWN
		temp = board.getAdjList(4, 10);
		assert temp.contains(board.getCellAt(4, 9));
		assert temp.contains(board.getCellAt(3, 10));
		assert temp.contains(board.getCellAt(5, 10));
		assert temp.size() == 3;
		
	}
	
	//Tests to ensure that doorway adjacency set has 1 element
	@Test
	public void testAdjacencyDoor() {
		//Direction LEFT
		Set<BoardCell> temp = board.getAdjList(20, 7);
		assert temp.contains(board.getCellAt(20, 6));
		assert temp.size() == 1;
		
		//Direction UP
		temp = board.getAdjList(18, 2);
		assert temp.contains(board.getCellAt(17, 2));
		assert temp.size() == 1;
		
		//Direction RIGHT
		temp = board.getAdjList(10, 4);
		assert temp.contains(board.getCellAt(10, 5));
		assert temp.size() == 1;
		
		//Direction DOWN
		temp = board.getAdjList(3, 10);
		assert temp.contains(board.getCellAt(4, 10));
		assert temp.size() == 1;
	}
	
	//Tests to ensure that targets are correct
	@Test
	public void testTargetOneStep() {
		board.calcTargets(18, 13, 1);
		Set<BoardCell> temp = board.getTargets();
		assert temp.size() == 4;
		assert temp.contains(board.getCellAt(17, 13));
		assert temp.contains(board.getCellAt(19, 13));
		assert temp.contains(board.getCellAt(18, 12));
		assert temp.contains(board.getCellAt(18, 14));
	}
	@Test
	public void testTargetTwoStep() {
		board.calcTargets(18, 13, 2);
		Set<BoardCell> temp = board.getTargets();
		assert temp.size() == 4;
		assert temp.contains(board.getCellAt(18, 15));
		assert temp.contains(board.getCellAt(18, 11));
		assert temp.contains(board.getCellAt(20, 13));
		assert temp.contains(board.getCellAt(16, 13));
	}
	@Test
	public void testTargetThreeStep() {
		board.calcTargets(18, 13, 3);
		Set<BoardCell> temp = board.getTargets();
		assert temp.size() == 3;
		assert temp.contains(board.getCellAt(15, 13));
		assert temp.contains(board.getCellAt(21, 13));
		assert temp.contains(board.getCellAt(18, 10));
	}
	@Test
	public void testTargetFourStep() {
		board.calcTargets(18, 13, 4);
		Set<BoardCell> temp = board.getTargets();
		assert temp.size() == 2;
		assert temp.contains(board.getCellAt(18, 9));
		assert temp.contains(board.getCellAt(22, 13));
	}
	
	//Test if target allows user to enter a room
	@Test
	public void testTargetEnterRoomOne() {
		board.calcTargets(8, 6, 4);
		Set<BoardCell> temp = board.getTargets();
		assert temp.contains(board.getCellAt(10, 4));
	}
	@Test
	public void testTargetEnterRoomTwo() {
		board.calcTargets(5, 16, 2);
		Set<BoardCell> temp = board.getTargets();
		assert temp.contains(board.getCellAt(3, 16));
	}
	
	//Test for targets calculated when leaving a room
	@Test
	public void testTargetLeavesRoomOne() {
		board.calcTargets(12, 23, 2);
		Set<BoardCell> temp = board.getTargets();
		assert temp.contains(board.getCellAt(11, 22));
		assert temp.contains(board.getCellAt(11, 24));
		assert temp.contains(board.getCellAt(10, 23));
		assert temp.size() == 3;
	}
	@Test
	public void testTargetLeavesRoomTwo() {
		board.calcTargets(4, 3, 1);
		Set<BoardCell> temp = board.getTargets();
		assert temp.contains(board.getCellAt(4, 4));
		assert temp.contains(board.getCellAt(5, 3));
		assert temp.size() == 2;
	}
	
}
