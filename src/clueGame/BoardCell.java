/*
 * Authors Daniel Sanchez and Trent Douglas
 */
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	
	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public BoardCell(int row, int column, char initial) {
		super();
	
		this.row = row;
		this.column = column;					//default constructor sets row and column to parameters
		this.initial = initial;
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
	
	//FIXME
	public boolean isWalkway() {
		return false;
	}
	
	//FIXME
	public boolean isRoom() {
		return false;
	}
	
	//FIXME
	public boolean isDoorway() {
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}
	
}
