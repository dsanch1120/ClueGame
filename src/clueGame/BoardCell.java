/*
 * Authors Daniel Sanchez and Trent Douglas
 */
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection door;
	
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
	
	public void setDoorDirection(char d) {
		if (d == 'D') {
			door = DoorDirection.DOWN;
		}
		if (d == 'U') {
			door = DoorDirection.UP;
		}
		if (d == 'L') {
			door = DoorDirection.LEFT;
		}
		if (d == 'R') {
			door = DoorDirection.RIGHT;
		}
		if (d == 'N') {
			door = DoorDirection.NONE;
		}
	}
	
	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}
	
}
