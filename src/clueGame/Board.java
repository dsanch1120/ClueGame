package clueGame;

import java.util.Map;
import java.util.Set;



public class Board {

	private int numRows;
	private int numColumns;
	public static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	//Variable used for singleton pattern
	private static Board theInstance = new Board();
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	
	
	//constructor is private to ensure only one can be created
	private Board() {}
	
	//This method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public static int getMAX_BOARD_SIZE() {
		return MAX_BOARD_SIZE;
	}

	public static void setMAX_BOARD_SIZE(int mAX_BOARD_SIZE) {
		MAX_BOARD_SIZE = mAX_BOARD_SIZE;
	}

	public BoardCell[][] getBoard() {
		return board;
	}

	public void setBoard(BoardCell[][] board) {
		this.board = board;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}

	public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}

	public void setAdjMatrix(Map<BoardCell, Set<BoardCell>> adjMatrix) {
		this.adjMatrix = adjMatrix;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void setTargets(Set<BoardCell> targets) {
		this.targets = targets;
	}

	public String getBoardConfigFile() {
		return boardConfigFile;
	}

	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}

	public String getRoomConfigFile() {
		return roomConfigFile;
	}

	public static Board getTheInstance() {
		return theInstance;
	}

	public static void setTheInstance(Board theInstance) {
		Board.theInstance = theInstance;
	}

}
