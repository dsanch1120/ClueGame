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
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();
	private Set<BoardCell> visited = new HashSet<BoardCell>();


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
	public void calcAdjacencies() {

		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < numColumns; i++) {
			for (int j = 0; j < numRows; j++) {
				Set<BoardCell> tempAdjSet = new HashSet<BoardCell>();
				if(theInstance.board[j][i].isWalkway() || theInstance.board[j][i].isDoorway()) {

					if(theInstance.board[j][i].isDoorway()) {														//if we are on a doorway, then the only way we can go is out in the opposite direction of the door
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.DOWN)) {				//door direction  down case
							tempAdjSet.add(theInstance.board[j+1][i]);
						}
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.UP)) {					//door direction is up case
							tempAdjSet.add(theInstance.board[j-1][i]);
						}
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.RIGHT)) {				//door direction is right case
							tempAdjSet.add(theInstance.board[j][i+1]);
						}
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.LEFT)) {				//door direction is left case
							tempAdjSet.add(theInstance.board[j][i-1]);	
						}
						adjMatrix.put(theInstance.board[j][i], tempAdjSet);

					}

					else if(!theInstance.board[j][i].isDoorway()) {													//if we are not on a doorway, add each direction to the temporary adjacency set unless it is a room or closet or the wrong side of a door 

						if((j+1 < numRows) && (!(theInstance.board[j+1][i].isRoom()) || theInstance.board[j+1][i].isDoorway()) && !theInstance.board[j+1][i].isX()) {		//test cell below and see if it meets criteria for adjacent cell
							if (theInstance.board[j+1][i].isDoorway()) {
								if(theInstance.board[j+1][i].getDoorDirection().equals(DoorDirection.UP)) {
									tempAdjSet.add(theInstance.board[j+1][i]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j+1][i]);
							}
						}
						if((j-1 >= 0) && (!(theInstance.board[j-1][i].isRoom()) || theInstance.board[j-1][i].isDoorway()) && !theInstance.board[j-1][i].isX()) {			//test cell above and see if it meets criteria for adjacent cell
							if (theInstance.board[j-1][i].isDoorway()) {
								if(theInstance.board[j-1][i].getDoorDirection().equals(DoorDirection.DOWN)) {
									tempAdjSet.add(theInstance.board[j-1][i]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j-1][i]);
							}
						}
						if((i+1 < numColumns) && (!(theInstance.board[j][i+1].isRoom()) || theInstance.board[j][i+1].isDoorway()) && !theInstance.board[j][i+1].isX()) {	//test cell to the right and see if it meets criteria for adjacent cell
							if (theInstance.board[j][i+1].isDoorway()) {
								if(theInstance.board[j][i+1].getDoorDirection().equals(DoorDirection.LEFT)) {
									tempAdjSet.add(theInstance.board[j][i+1]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j][i+1]);
							}
						}
						if((i-1 >= 0) && (!(theInstance.board[j][i-1].isRoom()) || theInstance.board[j][i-1].isDoorway()) && !theInstance.board[j][i-1].isX()) {			//test cell to the left and see if it meets criteria for adjacent cell
							if (theInstance.board[j][i-1].isDoorway()) {
								if(theInstance.board[j][i-1].getDoorDirection().equals(DoorDirection.RIGHT)) {
									tempAdjSet.add(theInstance.board[j][i-1]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j][i-1]);
							}
						}
						adjMatrix.put(theInstance.board[j][i], tempAdjSet);
					}

				} else {
					adjMatrix.put(theInstance.board[j][i], tempAdjSet);
				}
			}
		}

	}

	public void calcTargets(int row, int column, int pathLength) {
		targets.clear();															//reset the targets
		findAllTargets(row, column, pathLength);									//recursive call
	}

	public void findAllTargets(int row, int column, int pathLength) {				//recursive method to calculate targets
		BoardCell startCell = theInstance.getCellAt(row, column);
		visited.add(startCell);
		for (BoardCell i :  theInstance.getAdjList(row, column)) {					//iterate through adjacent cells and if the pathlength is still greater than one, do the recursive call on each adjacent cell
			if (i.isDoorway() && !(visited.contains(i))) {
				targets.add(i);
			}
			if (!visited.contains(i)) {
				visited.add(i);
				if (pathLength == 1) {
					targets.add(i);
				}
				else {
					findAllTargets(i.getRow(), i.getColumn(), pathLength - 1);

				}
				visited.remove(i);

			}

		}
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
