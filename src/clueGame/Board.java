/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;



public class Board {

	private int numRows;												//initialize instance variables
	private int numColumns;
	public static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();

	
	public void initialize() {

		try {
			theInstance.loadRoomConfig();
			theInstance.loadBoardConfig();
			theInstance.calcAdjacencies();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		File file = new File("data/" + roomConfigFile);												//open a new file scanner to read in the file
		legend = new HashMap<Character, String>();
		Scanner sc = new Scanner(file);
		String line = "";
		Character room; 
		String rName;
		String rType;
		while(sc.hasNextLine()) {
			line = sc.nextLine();

			room = line.charAt(0);
			if(line.charAt(1)!=',') {																//throw BadConfigFormatException if the second character is anything but a comma indicating that the room initial is not one character long
				throw new BadConfigFormatException("Room initial can only be one character!");
			}


			rName = line.substring(3, line.lastIndexOf(','));										//set room name

			if(rName == "") {																		//throw BadConfigFormatException if the name is 0 characters long
				throw new BadConfigFormatException("Room name cannot be empty!");
			}

			rType = line.substring((line.lastIndexOf(',') + 2), (line.length()));					//set room type

			if(!(rType.equals("Card") || rType.contentEquals("Other"))) {							//throw BadConfigFormatException if the room type is not Card or Other
				throw new BadConfigFormatException("Room type must either be Card or Other");
			}


			legend.put(room, rName);																//add room to legend
		}
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		File file = new File("data/" + boardConfigFile);
		Scanner sc = new Scanner(file);
		int ColumnCount = -1;
		int rowCounter = 0;

		while(sc.hasNextLine()) {																	//iterate through board file and count the number of rows and columns
			String line[] = sc.nextLine().split(",");
			if (ColumnCount != -1 && ColumnCount != line.length) {
				throw new BadConfigFormatException("Number of columns must be the same for each row");	//throw BadConfigFormatException if the number of columns is not consistent
			}
			ColumnCount = line.length;
			rowCounter++;
		}

		theInstance.board = new BoardCell[rowCounter][ColumnCount];
		numColumns = ColumnCount;
		numRows = rowCounter;

		sc = new Scanner(file);

		rowCounter = 0;
		while(sc.hasNextLine()) {																		//iterate through csv file again to create BoardCells
			String line[] = sc.nextLine().split(",");
			for (int i = 0; i < line.length; i++) {

				if(!legend.containsKey(line[i].charAt(0))) {
					throw new BadConfigFormatException("Initial must exist in legend! Couldn't find " + line[i].charAt(0) + " in legend");		//throw BadConfigFormatException if the initial is not found in the legend
				}

				theInstance.board[rowCounter][i] = new BoardCell(rowCounter, i, line[i].charAt(0));		//create new BoardCell object in theInstance
				if (line[i].length() > 2) {
					throw new BadConfigFormatException("Board Cell can only be 1 or 2 letters");		//throw BadConfigFormatException if BoardCell initial has more than 2 characters
				}
				if (line[i].length() == 2) {
					if (line[i].charAt(1) != 'U' &&line[i].charAt(1) != 'D' && line[i].charAt(1) != 'L' && line[i].charAt(1) != 'R' && line[i].charAt(1) != 'N') {
						throw new BadConfigFormatException("Door direction must be U, D, L, or R");		//throw BadConfigFormatException if the door direction is not up, down, left, or right, and the second initial is not N.
					}
					if (line[i].charAt(1) != 'N') {
						theInstance.board[rowCounter][i].setDoorDirection(line[i].charAt(1));			//set door direction for doors only
					}
				}
			}
			rowCounter++;
		}



	}
	public void calcAdjacencies() {													//not completed yet but in the works...

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

	public void calcTargets(int row, int column, int pathLength) {
			
	}


	//constructor is private to ensure only one can be created
	private Board() {}

	//This method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
																	//Getters 'n setters:
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
	
	public Set<BoardCell> getAdjList(int row, int column){
		return adjMatrix.get(board[row][column]);
	}

}
