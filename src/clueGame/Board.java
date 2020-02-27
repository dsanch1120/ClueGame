/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
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

	public static void main(String args[]) throws FileNotFoundException {
		Board test = new Board();
		test.setConfigFiles("data/layout.csv", "data/rooms.txt");
		test.loadRoomConfig();
		test.loadBoardConfig();

	}

	public void loadRoomConfig() throws FileNotFoundException {
		File file = new File(roomConfigFile);
		legend = new HashMap<Character, String>();
		Scanner sc = new Scanner(file);
		String line = "";
		Character room; String rName;
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			room = line.charAt(0);
			rName = line.substring(3, line.lastIndexOf(','));
			legend.put(room, rName);
		}
	}

	public void loadBoardConfig() throws FileNotFoundException {

		File file = new File(boardConfigFile);
		Scanner sc = new Scanner(file);
		String line = "";
		int counter1 = 1;
		int counter2 = 0; 
		
		line = sc.nextLine();
		
		for (int i = 0; i < line.length(); ++i) {
			if (line.charAt(i) == ',') {
				counter2++;
			}
		}
		counter2++;

		this.numColumns = counter2;
		
		while(sc.hasNextLine()) {
			String l = sc.nextLine();
			counter1++;
		}
		
		this.numRows = counter1;
		
		System.out.println(this.numColumns + " " + this.numRows);
		
		sc = new Scanner(file);

		theInstance.board = new BoardCell[numRows][numColumns];

		counter1 = 0; 
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			int counter3 = 0;

			for (int i = 0; i < line.length(); ++i) {
				if(line.charAt(i) == ',') {
					continue;
				}
				if((i != line.length() - 1) && (line.charAt(i + 1) != ',' && !(line.charAt(i + 1) == '\n'))) {
					theInstance.board[counter1][counter3] = new BoardCell(counter3, counter1, line.charAt(i));
					theInstance.board[counter1][counter3].setDoorDirection(line.charAt(i + 1));
					System.out.println(theInstance.board[counter1][counter3].getRow() + " " + theInstance.board[counter1][counter3].getColumn() + " " + theInstance.board[counter1][counter3].getInitial() + "" + line.charAt(i+1));
					counter3++;
					i++;
					continue;
				}
				theInstance.board[counter1][counter3] = new BoardCell(counter3, counter1, line.charAt(i));
				theInstance.board[counter1][counter3].setDoorDirection('N');
				System.out.println(theInstance.board[counter1][counter3].getRow() + " " + theInstance.board[counter1][counter3].getColumn() + " " + theInstance.board[counter1][counter3].getInitial());
				counter3++;

			}
			counter1++;
		}
	}

	public void calcAdjacencies() {
		
		for (int i = 0; i < numColumns; i++) {
			for (int j = 0; j < numRows; j++) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				if(i+1 < numRows) {
					tempSet.add(theInstance.board[i+1][j]);
				}
				if(i-1 >= 0) {
					tempSet.add(theInstance.board[i-1][j]);
				}
				if(j+1 < numColumns) {
					tempSet.add(theInstance.board[i][j+1]);
				}
				if(j-1 >= 0) {
					tempSet.add(theInstance.board[i][j-1]);
				}
				adjMatrix.put(theInstance.board[i][j], tempSet);
					
			}
		}
		
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
