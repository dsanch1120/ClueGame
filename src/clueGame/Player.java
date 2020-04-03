/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> hand = new ArrayList<Card>();
	protected BoardCell[][] board;
	protected Map<Character, String> legend;
	protected ArrayList<Card> roomList = new ArrayList<Card>();
	protected ArrayList<Card> peopleList = new ArrayList<Card>();
	protected ArrayList<Card> weaponList = new ArrayList<Card>();
	protected PlayerType type;

	public void setBoard(BoardCell[][] board) {
		this.board = board;
	}
	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}
	public PlayerType getType() {
		return type;
	}
	public Player() {
		super();
		setLists();
	}

	private void setLists(){
		try {
			String FILENAME = "cards.txt";
			File file = new File("data/" + FILENAME);
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				Card temp = new Card();
				temp.setCardName(line[0]);
				temp.setCardType(line[1]);
				switch (line[1]) {
				case "ROOM":
					roomList.add(temp);
					break;
				case "PERSON":
					peopleList.add(temp);
					break;
				case "WEAPON":
					weaponList.add(temp);
					break;
				default:
					System.out.println("Error");
				}	
			}
		}
		catch (Exception FileNotFoundException){
			FileNotFoundException.printStackTrace();
		}

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
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
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

	public ArrayList<Card> getHand() {
		return hand;
	}
	public void addToHand(Card card) {
		this.hand.add(card);
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

	public Card disproveSuggestion(Solution solution) {
		Collections.shuffle(hand);
		ArrayList<String> handNames = new ArrayList<String>();
		for (int i = 0; i < hand.size(); i++) {
			handNames.add(hand.get(i).getCardName());
		}
		Set<String> sol = new HashSet<String>();
		sol.add(solution.person);
		sol.add(solution.room);
		sol.add(solution.weapon);
		
		for (int i = 0; i < handNames.size(); i++) {
			if (sol.contains(handNames.get(i))) {
				return hand.get(i);
			}
		}
		
		return null;
	}
	
	
}
