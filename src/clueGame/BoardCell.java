/*
 * Authors Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection door;
	private int x;
	private int y;
	private Color color;
	int width = 20;
	int height = 20;

	public char getInitial() {
		return initial;
	}

	public Color getColor() {
		return color;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	//constructor sets row and column to parameters
	public BoardCell(int row, int column, char initial) {
		super();

		this.row = row;
		this.column = column;
		this.x = column*20;
		this.y = row*20;
		this.initial = initial;
		door = DoorDirection.NONE;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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

	public void draw(Graphics cell) {
		switch(this.initial){
		case('W'):
			cell.setColor(Color.BLACK);
			cell.drawRect(x, y, width, height);
//			this.color = convertColor("yellow");
			cell.setColor(Color.YELLOW);
			cell.fillRect(x+1, y+1, width-1, height-1);
			break;
		case('X'):
			cell.setColor(color.GRAY);
			cell.drawRect(x, y, width, height);
			cell.fillRect(x, y, width, height);
			break;
		default:
			cell.setColor(color.GRAY);
			cell.drawRect(x, y, width, height);
			cell.fillRect(x, y, width, height);
			break;
		}
		
		if(this.column == 1 && this.row == 3) {
			cell.setColor(color.BLACK);
			cell.drawString("Kitchen", this.x, this.y);
		}
		if(this.column == 8 && this.row == 2) {
			cell.setColor(color.BLACK);
			cell.drawString("Rec Room", this.x+10, this.y);
		}
		if(this.column == 15 && this.row == 3) {
			cell.setColor(color.BLACK);
			cell.drawString("Bathroom", this.x+3, this.y);
		}
		if(this.column == 20 && this.row == 9) {
			cell.setColor(color.BLACK);
			cell.drawString("Family Room", this.x+10, this.y-5);
		}
		if(this.column == 21 && this.row == 14) {
			cell.setColor(color.BLACK);
			cell.drawString("Nursery", this.x, this.y-5);
		}
		if(this.column == 20 && this.row == 22) {
			cell.setColor(color.BLACK);
			cell.drawString("Lounge", this.x, this.y-5);
		}
		if(this.column == 8 && this.row == 21) {
			cell.setColor(color.BLACK);
			cell.drawString("Study", this.x, this.y-5);
		}
		if(this.column == 0 && this.row == 21) {
			cell.setColor(color.BLACK);
			cell.drawString("Guest Room", this.x, this.y);
		}
		if(this.column == 0 && this.row == 10) {
			cell.setColor(color.BLACK);
			cell.drawString("Dining Room", this.x, this.y-5);
		}
		
		
	}

	public void drawPlayer(Graphics cell, Color color) {
		cell.setColor(color.BLACK);
		cell.drawArc(this.x, this.y, this.width, this.height, 0, 360);
		cell.setColor(color);
		cell.fillArc(this.x, this.y, this.width, this.height, 0, 360);
	}
	
	public void setColor(String strColor) {
		this.color = convertColor(strColor);
	}
	
	public Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null;
		}
		return color;
	}

}
