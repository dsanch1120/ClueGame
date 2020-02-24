/*
 * Authors Daniel Sanchez and Trent Douglas
 */
package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	BoardCell[][] gridList;													//declare instance variables:

	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();

	private Map<BoardCell, Set<BoardCell>> adjMtx;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 4;


	public IntBoard() {														//default constructor creates new 2 dimensional of BoardCell objects and calculates adjacencies
		super();
		this.gridList = new BoardCell[NUM_ROWS][NUM_COLS];
		this.adjMtx = new HashMap<BoardCell, Set<BoardCell>>();

		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				gridList[j][i] = new BoardCell(j, i);
			}
		}
		this.calcAdjacencies();
	}

	public void calcAdjacencies() {											//caldAdjacencies iterates through each board cell and checks if the adjacent cells are on the board and if so it adds them to the adjacencies set for that board cell
		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				if (i+1 < NUM_ROWS) {
					tempSet.add(gridList[i + 1][j]);
				}
				if (i-1 >= 0) {
					tempSet.add(gridList[i - 1][j]);
				}
				if (j+1 < NUM_COLS) {
					tempSet.add(gridList[i][j + 1]);
				}
				if (j-1 >= 0) {
					tempSet.add(gridList[i][j - 1]);
				}

				adjMtx.put(gridList[i][j], tempSet);
			}
		}
	}

	public Set<BoardCell> getAdjList(BoardCell cell){						//returns the adjacencies for a specified cell
		return adjMtx.get(cell);
	}

	public void calcTargets(BoardCell startCell, int pathLength) {			//recursively traverses each possible path given the starting cell and the path length

		visited.add(startCell);
		for (BoardCell i :  getAdjList(startCell)) {
			if (!visited.contains(i)) {
				visited.add(i);
				if (pathLength == 1) {
					targets.add(i);
				}
				else {
					calcTargets(i, pathLength - 1);
				}
				visited.remove(i);
			}
		}
	}

	public Set<BoardCell> getTargets(){										//returns the possible targets calculated in calcTargets
		return targets;
	}

	public BoardCell getCell(int row, int col) {							//returns the BoardCell object given the row and column it is on
		return gridList[row][col];
	}



	public static void main(String[] args) {								//this main method is simply used for testing purposes 
		IntBoard testBoard = new IntBoard();


		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				//System.out.print(testBoard.gridList[i][j].getRow() + "," + (testBoard.gridList[i][j].getColumn()) + " ");
				if(j == 3) System.out.println();
			}
		}
		testBoard.calcAdjacencies();

	}




}
