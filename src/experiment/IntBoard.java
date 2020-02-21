
package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	BoardCell[][] gridList;
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 4;
	
	
	public IntBoard() {
		super();
		this.gridList = new BoardCell[4][4];
		this.adjMtx = null;
		
		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				gridList[j][i] = new BoardCell(j, i);
			}
		}
	}

	public void calcAdjacencies() {
		for (int i = 0; i < NUM_COLS; ++i) {
			for (int j = 0; j < NUM_ROWS; ++j) {
				
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
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

	}
	
	
	
	
}
