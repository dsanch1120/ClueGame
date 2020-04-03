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
		ArrayList<BoardCell> newTargets = new ArrayList<BoardCell>();
		for (BoardCell i : targets) {
			if (!visited.equals(i) && i.isDoorway()) {
				return i;
			} 
			else if (visited.equals(i)) {
				targets.remove(i);
			} 
			else {
				newTargets.add(i);
			}
		}
		Collections.shuffle(newTargets);
		return newTargets.get(0);
	}
	
	//Method that allows the computer player to create a suggestion.
	public Solution createSuggestion() {
		Solution output;
		
		String roomSuggestion = this.legend.get(board[this.getRow()][this.getColumn()].getInitial());
		String personSuggestion = "";
		String weaponSuggestion = "";
		
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
	
	
}
