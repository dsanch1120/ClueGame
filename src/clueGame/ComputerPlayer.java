/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComputerPlayer extends Player {
	Board gameBoard = Board.getInstance();
	private BoardCell visited = new BoardCell(0,0,'K');
	private Set<Card> peopleSeen = new HashSet<Card>();
	private Set<Card> weaponsSeen = new HashSet<Card>();
	private Set<Card> roomsSeen = new HashSet<Card>();
	
	
	
	public ComputerPlayer() {
		super();
		type = PlayerType.COMPUTER;
	}

	public Set<Card> getPeopleSeen() {
		return peopleSeen;
	}

	public Set<Card> getWeaponsSeen() {
		return weaponsSeen;
	}

	public Set<Card> getRoomsSeen() {
		return roomsSeen;
	}

	public void updatePeopleSeen (Card c) {
		peopleSeen.add(c);
	}
	
	public void updateWeaponsSeen (Card c) {
		weaponsSeen.add(c);
	}
	
	public void updateRoomsSeen (Card c) {
		roomsSeen.add(c);
	}
	
	public BoardCell getVisited() {
		return visited;
	}

	public void setVisited(BoardCell visited) {
		this.visited = visited;
	}

	//Method that allows the computer player to randomly select a target.
	public BoardCell selectTarget( Set<BoardCell> targets) {
		gameBoard = Board.getInstance();
		ArrayList<BoardCell> newTargets = new ArrayList<BoardCell>();
		
		if (targets.size() == 0) {
			for (BoardCell i : gameBoard.getVisited()) {
				return i;
			}
		}
		for (BoardCell j : targets) {
			newTargets.add(j);
		}
		for (BoardCell i : targets) {
			for (int k = 0; k < gameBoard.getPlayers().length; k++) {
				if(k != gameBoard.getCurrentPlayerIndex()) {
					if(i.getRow() == (gameBoard.getPlayers()[k].getRow()) && i.getColumn() == (gameBoard.getPlayers()[k].getColumn())) {
						newTargets.remove(i);
					}
				}
			}
			
			
			if (!visited.equals(i) && i.isDoorway()) {
				visited = i;
				return i;
			} 
			else if (visited.equals(i)) {
				newTargets.remove(i);
			} 

		}
		
		
		//System.out.println(newTargets.size() + " " + this.getPlayerName());
		Collections.shuffle(newTargets);
		if(newTargets.size() != 0) {
			return newTargets.get(0);
		}
		else {
			return (gameBoard.getCellAt(gameBoard.getPlayers()[gameBoard.getCurrentPlayerIndex()].getRow(), gameBoard.getPlayers()[gameBoard.getCurrentPlayerIndex()].getColumn()));
		}
	}
	
	//Method that allows the computer player to create a suggestion.
	public Solution createSuggestion() {
		Solution output;
		
		Map<Character, String> legend = gameBoard.getLegend();
		String roomSuggestion = legend.get(gameBoard.getCellAt(this.getRow(), this.getColumn()).getInitial());
		String personSuggestion = "";
		String weaponSuggestion = "";
		
		for (Card i : this.getHand()) {
			if(i.getType() == CardType.PERSON) {
				peopleSeen.add(i);
			}
			if(i.getType() == CardType.WEAPON) {
				weaponsSeen.add(i);
			}
			if(i.getType() == CardType.ROOM) {
				roomsSeen.add(i);
			}
		}
		
		
		Collections.shuffle(this.peopleList);
		Collections.shuffle(this.weaponList);
		
		ArrayList<String>stringPeopleList = new ArrayList<String>();
		for (Card i : peopleList) {
			stringPeopleList.add(i.getCardName());
		}
		ArrayList<String>stringPeopleSeen = new ArrayList<String>();
		for (Card i : peopleSeen) {
			stringPeopleSeen.add(i.getCardName());
		}
		
		ArrayList<String>stringWeaponList = new ArrayList<String>();
		for (Card i : weaponList) {
			stringWeaponList.add(i.getCardName());
		}
		ArrayList<String>stringWeaponSeen = new ArrayList<String>();
		for (Card i : weaponsSeen) {
			stringWeaponSeen.add(i.getCardName());
		}
	
		for (String i : stringPeopleList) {
			if (!(stringPeopleSeen.contains(i))) {
				personSuggestion = i;
				break;
			}
		}
		
		for (String i : stringWeaponList) {
			if (!stringWeaponSeen.contains(i)) {
				weaponSuggestion = i;
				break;
			}
		}
		
		
		output = new Solution(personSuggestion, roomSuggestion, weaponSuggestion);
		
		return output;
	}
	
	public Solution makeAccusation() {
		System.out.println("Made Accusation!!!");
		Set<String>stringPeopleSeen = new HashSet<String>();
		Set<String>stringRoomsSeen = new HashSet<String>();
		Set<String>stringWeaponsSeen = new HashSet<String>();
		String personSuggestion = "";
		String roomSuggestion = "";
		String weaponSuggestion = "";
		
		for (Card i : this.peopleList) {
			stringPeopleSeen.add(i.getCardName());
		}
		for (Card i : this.weaponList) {
			stringWeaponsSeen.add(i.getCardName());
		}
		for (Card i : this.roomList) {
			stringRoomsSeen.add(i.getCardName());
		}
		
		for (Card i : this.peopleSeen) {
			if (!stringPeopleSeen.contains(i.getCardName())) {
				personSuggestion = i.getCardName();
			}
		}
		for (Card i : this.weaponsSeen) {
			if (!stringWeaponsSeen.contains(i.getCardName())) {
				weaponSuggestion = i.getCardName();
			}
		}
		for (Card i : this.roomsSeen) {
			if (!stringRoomsSeen.contains(i.getCardName())) {
				roomSuggestion = i.getCardName();
			}
		}
		
		Solution output = new Solution(personSuggestion, roomSuggestion, weaponSuggestion);
		return output;
	}
}
