/*
 * Authors Daniel Sanchez and Trent Douglas
 */
package experiment;

public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;					//default constructor sets row and column to parameters
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
}
