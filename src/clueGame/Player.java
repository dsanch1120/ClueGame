/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	
	public Player() {
		super();
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setColor(String color) {
		this.color = convertColor(color);
	}
	//Getter functions 
	public String getPlayerName() {
		return playerName;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public Color getColor() {
		return color;
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
