/*
 * Authors Daniel Sanchez and Trent Douglas
 */
package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	BoardCell[][] gridList;

	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();

	private Map<BoardCell, Set<BoardCell>> adjMtx;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 4;


	public IntBoard() {
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

	public void calcAdjacencies() {
		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();


				System.out.println("tile: " + gridList[i][j].getRow() + " , " + gridList[i][j].getColumn());
				if (i+1 < NUM_ROWS) {
					tempSet.add(gridList[i + 1][j]);
					System.out.println(gridList[i + 1][j].getRow() + " , " + gridList[i + 1][j].getColumn());
				}
				if (i-1 >= 0) {
					tempSet.add(gridList[i - 1][j]);
					System.out.println(gridList[i - 1][j].getRow() + " , " + gridList[i - 1][j].getColumn());
				}
				if (j+1 < NUM_COLS) {
					tempSet.add(gridList[i][j + 1]);
					System.out.println(gridList[i][j + 1].getRow() + " , " + gridList[i][j + 1].getColumn());
				}
				if (j-1 >= 0) {
					tempSet.add(gridList[i][j - 1]);
					System.out.println(gridList[i][j - 1].getRow() + " , " + gridList[i][j - 1].getColumn());
				}

				//System.out.println(tempSet);
				System.out.println();
				adjMtx.put(gridList[i][j], tempSet);
			}
		}
	}

	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjMtx.get(cell);
	}

	public void calcTargets(BoardCell startCell, int pathLength) {

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

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public BoardCell getCell(int row, int col) {
		return gridList[row][col];
	}



	public static void main(String[] args) {
		IntBoard testBoard = new IntBoard();


		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				System.out.print(testBoard.gridList[i][j].getRow() + "," + (testBoard.gridList[i][j].getColumn()) + " ");
				if(j == 3) System.out.println();
			}
		}
		testBoard.calcAdjacencies();

	}




}
