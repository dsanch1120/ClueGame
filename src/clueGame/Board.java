/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package clueGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel {
	// initialize instance variables
	private int numRows;
	private int numColumns;
	public static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String peopleConfigFile;
	private String cardConfigFile;
	private static Board theInstance = new Board();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Player[] players = new Player[6];
	private Card[] cards = new Card[21];
	private Solution solution;
	private int roll = 0;
	private int currentPlayerIndex = -1;
	private Boolean hasMoved = false;
	private Boolean hasPrinted = false;


	public Boolean getHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(Boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public void initialize() {

		try {
			theInstance.loadRoomConfig();
			theInstance.loadBoardConfig();
			theInstance.calcAdjacencies();
			theInstance.loadPeopleConfig();
			theInstance.loadCardConfig();
			theInstance.dealCards();
			theInstance.roll();
			System.out.println(theInstance.solution.person+ " " + theInstance.solution.room + " " + theInstance.solution.weapon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		File file = new File("data/" + roomConfigFile);
		// open a new file scanner to read in the file
		legend = new HashMap<Character, String>();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		String line = "";
		Character roomInitial;
		String roomName;
		String roomType;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();

			roomInitial = line.charAt(0);
			if (line.charAt(1) != ',') {
				// throw BadConfigFormatException if the second character is anything but a
				// comma indicating that the room initial is not one character long
				throw new BadConfigFormatException("Room initial can only be one character!");
			}

			roomName = line.substring(3, line.lastIndexOf(','));
			// set room name

			if (roomName == "") {
				// throw BadConfigFormatException if the name is 0 characters long
				throw new BadConfigFormatException("Room name cannot be empty!");
			}

			roomType = line.substring((line.lastIndexOf(',') + 2), (line.length()));
			// set room type

			if (!(roomType.equals("Card") || roomType.contentEquals("Other"))) {
				// throw BadConfigFormatException if the room type is not Card or Other
				throw new BadConfigFormatException("Room type must either be Card or Other");
			}

			// add room to legend
			legend.put(roomInitial, roomName);
		}
	}

	public void roll() {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		nums.add(1);
		nums.add(2);
		nums.add(3);
		nums.add(4);
		nums.add(5);
		nums.add(6);
		Collections.shuffle(nums);
		this.roll = nums.get(0);

	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public static int getMAX_BOARD_SIZE() {
		return MAX_BOARD_SIZE;
	}

	public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}

	public String getBoardConfigFile() {
		return boardConfigFile;
	}

	public String getRoomConfigFile() {
		return roomConfigFile;
	}

	public String getPeopleConfigFile() {
		return peopleConfigFile;
	}

	public String getCardConfigFile() {
		return cardConfigFile;
	}

	public static Board getTheInstance() {
		return theInstance;
	}

	public Set<BoardCell> getVisited() {
		return visited;
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		File file = new File("data/" + boardConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		int columnCount = -1;
		int rowCounter = 0;
		// iterate through board file and count the number of rows and columns
		while (scanner.hasNextLine()) {
			String line[] = scanner.nextLine().split(",");
			if (columnCount >= 0 && columnCount != line.length) {
				// throw BadConfigFormatException if the number of columns is not consistent
				throw new BadConfigFormatException("Number of columns must be the same for each row");
			}
			columnCount = line.length;
			rowCounter++;
		}

		theInstance.board = new BoardCell[rowCounter][columnCount];
		numColumns = columnCount;
		numRows = rowCounter;

		scanner = new Scanner(file);

		rowCounter = 0;
		// iterate through csv file again to create BoardCells
		while (scanner.hasNextLine()) {
			String line[] = scanner.nextLine().split(",");
			for (int i = 0; i < line.length; i++) {

				if (!legend.containsKey(line[i].charAt(0))) {
					// throw BadConfigFormatException if the initial is not found in the legend
					throw new BadConfigFormatException(
							"Initial must exist in legend! Couldn't find " + line[i].charAt(0) + " in legend");
				}
				// create new BoardCell object in theInstance
				theInstance.board[rowCounter][i] = new BoardCell(rowCounter, i, line[i].charAt(0));
				if (line[i].length() > 2) {
					// throw BadConfigFormatException if BoardCell initial has more than 2
					// characters
					throw new BadConfigFormatException("Board Cell can only be 1 or 2 letters");
				}
				if (line[i].length() == 2) {
					if (line[i].charAt(1) != 'U' && line[i].charAt(1) != 'D' && line[i].charAt(1) != 'L'
							&& line[i].charAt(1) != 'R' && line[i].charAt(1) != 'N') {
						// throw BadConfigFormatException if the door direction is not up, down, left,
						// or right, and the second initial is not N.
						throw new BadConfigFormatException("Door direction must be U, D, L, or R");
					}
					if (line[i].charAt(1) != 'N') {
						// set door direction for doors only
						theInstance.board[rowCounter][i].setDoorDirection(line[i].charAt(1));
					}
				}
			}
			rowCounter++;
		}

	}

	public void calcAdjacencies() {

		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < numColumns; i++) {
			for (int j = 0; j < numRows; j++) {
				Set<BoardCell> tempAdjSet = new HashSet<BoardCell>();
				if (theInstance.board[j][i].isWalkway() || theInstance.board[j][i].isDoorway()) {
					// if we are on a doorway, then the only way we can go is out in the opposite
					// direction of the door
					if (theInstance.board[j][i].isDoorway()) {
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.DOWN)) {
							// door direction down case
							tempAdjSet.add(theInstance.board[j + 1][i]);
						}
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.UP)) {
							// door direction is up case
							tempAdjSet.add(theInstance.board[j - 1][i]);
						}
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.RIGHT)) {
							// door direction is right case
							tempAdjSet.add(theInstance.board[j][i + 1]);
						}
						if (theInstance.board[j][i].getDoorDirection().equals(DoorDirection.LEFT)) {
							// door direction is left case
							tempAdjSet.add(theInstance.board[j][i - 1]);
						}
						adjMatrix.put(theInstance.board[j][i], tempAdjSet);

					}
					// if we are not on a doorway, add each direction to the temporary adjacency set
					// unless it is a room or closet or the wrong side of a door
					else if (!theInstance.board[j][i].isDoorway()) {
						if ((j + 1 < numRows)
								&& (!(theInstance.board[j + 1][i].isRoom()) || theInstance.board[j + 1][i].isDoorway())
								&& !theInstance.board[j + 1][i].isCloset()) {
							// test cell below and see if it meets criteria for adjacent cell
							if (theInstance.board[j + 1][i].isDoorway()) {
								if (theInstance.board[j + 1][i].getDoorDirection().equals(DoorDirection.UP)) {
									tempAdjSet.add(theInstance.board[j + 1][i]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j + 1][i]);
							}
						}
						if ((j - 1 >= 0)
								&& (!(theInstance.board[j - 1][i].isRoom()) || theInstance.board[j - 1][i].isDoorway())
								&& !theInstance.board[j - 1][i].isCloset()) {
							// test cell above and see if it meets criteria for adjacent cell
							if (theInstance.board[j - 1][i].isDoorway()) {
								if (theInstance.board[j - 1][i].getDoorDirection().equals(DoorDirection.DOWN)) {
									tempAdjSet.add(theInstance.board[j - 1][i]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j - 1][i]);
							}
						}
						if ((i + 1 < numColumns)
								&& (!(theInstance.board[j][i + 1].isRoom()) || theInstance.board[j][i + 1].isDoorway())
								&& !theInstance.board[j][i + 1].isCloset()) {
							// test cell to the right and see if it meets criteria for adjacent cell
							if (theInstance.board[j][i + 1].isDoorway()) {
								if (theInstance.board[j][i + 1].getDoorDirection().equals(DoorDirection.LEFT)) {
									tempAdjSet.add(theInstance.board[j][i + 1]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j][i + 1]);
							}
						}
						if ((i - 1 >= 0)
								&& (!(theInstance.board[j][i - 1].isRoom()) || theInstance.board[j][i - 1].isDoorway())
								&& !theInstance.board[j][i - 1].isCloset()) {
							// test cell to the left and see if it meets criteria for adjacent cell
							if (theInstance.board[j][i - 1].isDoorway()) {
								if (theInstance.board[j][i - 1].getDoorDirection().equals(DoorDirection.RIGHT)) {
									tempAdjSet.add(theInstance.board[j][i - 1]);
								}
							} else {
								tempAdjSet.add(theInstance.board[j][i - 1]);
							}
						}
						adjMatrix.put(theInstance.board[j][i], tempAdjSet);
					}

				} else {
					adjMatrix.put(theInstance.board[j][i], tempAdjSet);
				}
			}
		}

	}

	public void loadPeopleConfig() throws FileNotFoundException {
		File file = new File("data/" + peopleConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		int counter = 0;

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");
			if (counter == 0) {
				theInstance.players[counter] = new HumanPlayer();
				theInstance.players[counter].setPlayerName(line[0]);
				theInstance.players[counter].setColor(line[1]);
				theInstance.players[counter].setRow(Integer.parseInt(line[2]));
				theInstance.players[counter].setColumn(Integer.parseInt(line[3]));
				counter++;
			} else {
				theInstance.players[counter] = new ComputerPlayer();
				theInstance.players[counter].setPlayerName(line[0]);
				theInstance.players[counter].setColor(line[1]);
				theInstance.players[counter].setRow(Integer.parseInt(line[2]));
				theInstance.players[counter].setColumn(Integer.parseInt(line[3]));
				counter++;
			}
		}

	}

	public void loadCardConfig() throws FileNotFoundException {
		File file = new File("data/" + cardConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		int counter = 0;

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");
			theInstance.cards[counter] = new Card();
			theInstance.cards[counter].setCardName(line[0]);
			theInstance.cards[counter].setCardType(line[1]);
			counter++;
		}
	}

	public void dealCards() {
		ArrayList<Card> roomCards = new ArrayList<Card>();
		ArrayList<Card> personCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].getType() == CardType.ROOM) {
				roomCards.add(cards[i]);
			}
			if (cards[i].getType() == CardType.PERSON) {
				personCards.add(cards[i]);
			}
			if (cards[i].getType() == CardType.WEAPON) {
				weaponCards.add(cards[i]);
			}
		}
		// shuffle lists of room cards, person cards, and weapon cards and pick the
		// first index of each to be the solution
		Collections.shuffle(roomCards);
		Collections.shuffle(personCards);
		Collections.shuffle(weaponCards);
		theInstance.solution = new Solution(personCards.get(0).getCardName(), roomCards.get(0).getCardName(),
				weaponCards.get(0).getCardName());
		// remove solution from deck
		roomCards.remove(0);
		personCards.remove(0);
		weaponCards.remove(0);
		ArrayList<Card> dealableCards = new ArrayList<Card>();
		// consolidate all cards to create dealable deck without the solution
		dealableCards.addAll(roomCards);
		dealableCards.addAll(personCards);
		dealableCards.addAll(weaponCards);
		// Shuffles cards again after they are consolidated
		Collections.shuffle(dealableCards);
		// iterate through all dealable cards and deal them
		for (int i = 0; i < dealableCards.size(); i++) {
			theInstance.players[i % players.length].addToHand(dealableCards.get(i));
		}
		for (Player player : theInstance.players) {
			System.out.println(player.getPlayerName());
			for (Card card : player.getHand()) {
				System.out.print(card.getCardName()+ " ");
			}
			System.out.println();
		}

	}

	public void calcTargets(int row, int column, int pathLength) {
		targets.clear();
		// reset the targets list before we re-populate it
		findAllTargets(row, column, pathLength);
		// recursive call
		visited.clear();

		//Ensures that a target containing another player is not added
		Set<BoardCell> playerLocations = new HashSet<BoardCell>();
		for (int i = 0; i < players.length; i++) {
			BoardCell temp = theInstance.getCellAt(players[i].getRow(), players[i].getColumn());
			playerLocations.add(temp);
		}

		Set<BoardCell> newTargets = new HashSet<BoardCell>();

		for (BoardCell i : targets) {
			newTargets.add(i);
		}

		for (BoardCell i : targets) {
			for (BoardCell j : playerLocations) {
				if (i.equals(j)) {
					newTargets.remove(i);
				}
			}
		}

		if (players[currentPlayerIndex].getType() == PlayerType.COMPUTER) {
			ComputerPlayer cp = (ComputerPlayer) players[currentPlayerIndex];
			for (BoardCell i : targets) {
				if(theInstance.getCellAt(i.getRow(), i.getColumn()).getInitial() == cp.getVisited().getInitial()) {
					//if(i.getInitial() == theInstance.getCellAt(players[currentPlayerIndex].getRow(), players[currentPlayerIndex].getColumn()).getInitial()){
					newTargets.remove(i);
					//}
				}

			}
		}

		theInstance.targets = newTargets;

	}

	public void findAllTargets(int row, int column, int pathLength) {
		// recursive method to calculate targets
		BoardCell startCell = theInstance.getCellAt(row, column);
		visited.add(startCell);
		for (BoardCell i : theInstance.getAdjList(row, column)) {
			// iterate through adjacent cells and if the path length is still greater than
			// one, do the recursive call on each adjacent cell
			if (i.isDoorway() && !(visited.contains(i))) {
				targets.add(i);
			}
			if (!visited.contains(i)) {
				visited.add(i);
				if (pathLength == 1) {
					targets.add(i);
				} else {
					findAllTargets(i.getRow(), i.getColumn(), pathLength - 1);

				}
				visited.remove(i);

			}

		}
	}

	// Method to handle player suggestions.
	public Card handleSuggestions(Solution suggestion, int index) {
		// Set containing the string values of the suggestion.
		Set<String> sol = new HashSet<String>();
		sol.add(suggestion.person);
		sol.add(suggestion.weapon);
		sol.add(suggestion.room);
		// For loop to check every player.
		int counter = index;
		if (index == players.length - 1) {
			index = -1;
		} 

		for (int i = index + 1; i < players.length; i++) {
			// If the loop reaches the player who made the accusation, returns null.
			if (i == counter) {
				return null;
			}

			// Checks the hand of the next player in the array to see if they can disprove
			// the suggestion.
			ArrayList<Card> tempHand = players[i].getHand();
			Collections.shuffle(tempHand);
			for (int j = 0; j < tempHand.size(); j++) {
				if (sol.contains(tempHand.get(j).getCardName())) {
					// Returns the card if they can disprove the suggestion.
					return tempHand.get(j);
				}
			}

			// If the player at the highest index is reached, it loops back to the player at
			// index 0.
			if (i == players.length - 1) {
				i = -1;
			}
		}
		return null;
	}

	// Method to display the board
	public void paintComponent(Graphics cell) {
		// For loop that iterates through every board cell
		super.paintComponent(cell);

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell boardcell = Board.getInstance().getCellAt(i, j);
				boardcell.draw(cell);
			}

		}

		// For loop that iterates through the players
		for (int i = 0; i < players.length; i++) {
			BoardCell boardcell = Board.getInstance().getCellAt(players[i].getRow(), players[i].getColumn());
			boardcell.drawPlayer(cell, players[i].getColor());
		}

		// System.out.println(theInstance.hasMoved);
		if (!theInstance.hasMoved) {
			if (theInstance.currentPlayerIndex > -1
					&& theInstance.players[theInstance.currentPlayerIndex].getType().equals(PlayerType.HUMAN)
					&& theInstance.roll != 0) {
				theInstance.calcTargets(theInstance.players[currentPlayerIndex].getRow(),
						theInstance.players[currentPlayerIndex].getColumn(), theInstance.roll);
				for (BoardCell i : theInstance.targets) {
					i.drawTargets(cell);
				}
			}
		}
		hasPrinted = true;

	}

	public Boolean getHasPrinted() {
		return hasPrinted;
	}

	public void setHasPrinted(Boolean hasPrinted) {
		this.hasPrinted = hasPrinted;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public boolean checkAccusation(Solution s) {
		if (s.person == theInstance.solution.person && s.room == theInstance.solution.room
				&& s.weapon == theInstance.solution.weapon) {
			return true;
		} else {
			return false;
		}
	}

	public Solution getSolution() {
		return solution;
	}

	public BoardCell[][] getBoard() {
		return board;
	}

	// constructor is private to ensure only one can be created
	private Board() {
		Listener listener = new Listener();
		this.addMouseListener(listener);
	}

	// This method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	// Getters 'n setters:
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setBoard(BoardCell[][] board) {
		this.board = board;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	void setAdjMatrix(Map<BoardCell, Set<BoardCell>> adjMatrix) {
		this.adjMatrix = adjMatrix;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void setConfigFiles(String boardConfigFile, String roomConfigFile, String peopleConfigFile,
			String cardConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
		this.peopleConfigFile = peopleConfigFile;
		this.cardConfigFile = cardConfigFile;
	}

	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMatrix.get(board[row][column]);
	}

	public Player[] getPlayers() {
		return players;
	}

	public Card[] getCards() {
		return cards;
	}

	// Mouse listener class to control what happens when it's the human player's turn.
	public class Listener implements MouseListener {

		//Controls what happens when the user clicks the mouse
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			BoardCell clickedCell = theInstance.getCellAt((y / 20)%25, (x / 20)%25);

			//Checks to ensure that the current player is a human
			if (theInstance.getPlayers()[currentPlayerIndex].getType().equals(PlayerType.HUMAN)) {
				//Ensures that the user's choice of cells is a correct option
				for (BoardCell i : targets) {
					if (clickedCell.equals(i)) {
						if (!hasMoved) {
							theInstance.getPlayers()[currentPlayerIndex].setRow(y / 20);
							theInstance.getPlayers()[currentPlayerIndex].setColumn(x / 20);

							theInstance.hasMoved = true;

							theInstance.repaint();
						}

					}
				}
				//The user will not move if they have clicked an incorrect cell. Thus if the user has not moved, a JDialog box displays an error
				if (!hasMoved) {
					errorMessage message = new errorMessage();
				}
			}

			//Repaints the board after the user's click
			theInstance.repaint();

		}

		// The following Overriden methods are unneeded and therefore have been kept empty
		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		//This class creates an "error" message when the user clicks the wrong BoardCell
		public class errorMessage extends JDialog {
			public errorMessage() {

				JOptionPane.showMessageDialog(this, "Invalid Entry");
			}
		}

	}

}
