/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	
	private BoardCell visited;
	
	public BoardCell getVisited() {
		return visited;
	}

	public void setVisited(BoardCell visited) {
		this.visited = visited;
	}

	public BoardCell pickLocation( Set<BoardCell> targets) {
		
		return null;
	}
	
}
