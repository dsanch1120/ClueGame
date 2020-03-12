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
		door = DoorDirection.NONE;
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
	
	public boolean isWalkway() {
		if(this.initial == 'W') {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isX() {
		return initial == 'X';
	}
	
	public boolean isRoom() {
		if(this.initial != 'X' && this.initial != 'W') {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDoorway() {
		if(this.getDoorDirection() != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setDoorDirection(char d) {
		if (d == 'D') {
			this.door = DoorDirection.DOWN;
		}
		if (d == 'U') {
			this.door = DoorDirection.UP;
		}
		if (d == 'L') {
			this.door = DoorDirection.LEFT;
		}
		if (d == 'R') {
			this.door = DoorDirection.RIGHT;
		}
	}
	
	public DoorDirection getDoorDirection() {
		return door;
	}
	
}
