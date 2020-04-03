/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private BoardCell visited = new BoardCell(0,0,'K');
	private Set<Card> peopleSeen = new HashSet<Card>();
	private Set<Card> weaponsSeen = new HashSet<Card>();
	private Set<Card> roomsSeen = new HashSet<Card>();
	
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
	
	public Solution createSuggestion() {
		return null;
	}
	
}
