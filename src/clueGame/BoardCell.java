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

	//constructor sets row and column to parameters
	public BoardCell(int row, int column, char initial) {
		super();

		this.row = row;
		this.column = column;
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

	public boolean isCloset() {
		return initial == 'X';
	}

	public boolean isRoom() {
		if(this.initial != 'X' && this.initial != 'W') {
			return true;
		} else {
			return false;
		}
	}
	//returns true if the BoardCell is a doorway
	public boolean isDoorway() {
		if(this.getDoorDirection() != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}

	//Method with a switch statement that assigns door a DoorDirection value
	public void setDoorDirection(char d) {
		
		switch (d) {
		case 'D':
			this.door = DoorDirection.DOWN;
			break;
		case 'U':
			this.door = DoorDirection.UP;
			break;
		case 'L':
			this.door = DoorDirection.LEFT;
			break;
		case 'R':
			this.door = DoorDirection.RIGHT;
			break;
		default:
			this.door = DoorDirection.NONE;
			break;
		}
	}

	public DoorDirection getDoorDirection() {
		return door;
	}

}
