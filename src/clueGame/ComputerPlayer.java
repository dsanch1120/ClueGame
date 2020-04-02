/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private BoardCell visited = new BoardCell(0,0,'K');
	
	public BoardCell getVisited() {
		return visited;
	}

	public void setVisited(BoardCell visited) {
		this.visited = visited;
	}

	public BoardCell pickLocation( Set<BoardCell> targets) {
		ArrayList<BoardCell> newTargets = new ArrayList();
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
	
}
