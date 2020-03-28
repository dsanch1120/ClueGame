/*
 * Created by Daniel Sanchez and Trent Douglas
 */

package tests;

/*
 * This program tests 
 * 1. The game correctly loads the people
 * 2. The game correctly loads the cards
 * 3. The game properly deals the cards.
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

import java.awt.Color;

public class gameSetupTests {
	public static final int DECK_SIZE = 21;
	public static final int NUM_PEOPLE = 6;
	
	private static Board board;
	
	//Gets everything ready before the tests
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("layout.csv", "rooms.txt", "people.txt", "cards.txt");		
		board.initialize();
	}
	
	//This tests ensures that the program correctly loads and implements the "people.txt" file
	//@Test
	public void testPeople() {

		Player[] people = board.getPlayers();
		//Tests if the Array of people contains 6 players
		assertEquals(6, people.length);
		
		//Tests that the people loaded correctly
		assertEquals(people[0].getPlayerName(), "Mrs. White");
		assertEquals(people[1].getPlayerName(), "Mr. Green");
		assertEquals(people[2].getPlayerName(), "Mrs. Peacock");
		assertEquals(people[3].getPlayerName(), "Professor Plum");
		assertEquals(people[4].getPlayerName(), "Miss Scarlett");
		assertEquals(people[5].getPlayerName(), "Colonel Mustard");
		
		//Tests that the colors loaded correctly
		assertEquals(people[0].getColor(), Color.white);
		assertEquals(people[1].getColor(), Color.green);
		assertEquals(people[2].getColor(), Color.blue);
		assertEquals(people[3].getColor(), Color.magenta);
		assertEquals(people[4].getColor(), Color.red);
		assertEquals(people[5].getColor(), Color.yellow);
		
		//Tests that the row coordinates loaded correctly
		assertEquals(people[0].getRow(), 24);
		assertEquals(people[1].getRow(), 24);
		assertEquals(people[2].getRow(), 15);
		assertEquals(people[3].getRow(), 0);
		assertEquals(people[4].getRow(), 0);
		assertEquals(people[5].getRow(), 18);
		
		//Tests that the column coordinates loaded correctly
		assertEquals(people[0].getColumn(), 6);
		assertEquals(people[1].getColumn(), 13);
		assertEquals(people[2].getColumn(), 0);
		assertEquals(people[3].getColumn(), 18);
		assertEquals(people[4].getColumn(), 4);
		assertEquals(people[5].getColumn(), 24);
	
	}
	
	//This test ensures that the deck of cards is correctly loaded by the program
	@Test
	public void testCard() {
		Card[] cards = board.getCards();
		
		//Checks that the deck has the correct number of cards
		assertEquals(cards.length, 21);
		
		//Checks that the deck has the correct number of each type of card
		int numPeople = 0; 
		int numRooms = 0;
		int numWeapons = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].getType() == CardType.PERSON) {
				numPeople++;
			}
			if (cards[i].getType() == CardType.ROOM) {
				numRooms++;
			}
			if (cards[i].getType() == CardType.WEAPON) {
				numWeapons++;
			}
		}
		assertEquals(numPeople, 6);
		assertEquals(numRooms, 9);
		assertEquals(numWeapons, 6);
		
		//Checks 3 of the cards to see that they loaded correctly
		//Checks card names
		assertEquals(cards[0].getCardName(), "Kitchen");
		assertEquals(cards[10].getCardName(), "Mr. Green");
		assertEquals(cards[20].getCardName(), "Revolver");
		
		//Checks card type
		assertEquals(cards[0].getType(), CardType.ROOM);
		assertEquals(cards[10].getType(), CardType.PERSON);
		assertEquals(cards[20].getType(), CardType.WEAPON);
		
	}
	
	//This test ensures that the deck of cards has been dealt correctly
	public void testHand() {
		int cardCounter = 0;
		//add up all the cards in each player's hand
		Player[] people = board.getPlayers();
		for (int i = 0; i < people.length; i++) {
			cardCounter+=people[i].getHand().size();
		}
		assertEquals(cardCounter, 18);
		//stores the number of cards in the first player's hand
		int numCardsInHand = people[0].getHand().size();
		//test to make sure all of the other players have the same amount of cards as the first player or one less card
		for (int i = 0; i < people.length; i++) {
			assert(people[i].getHand().size() == numCardsInHand || people[i].getHand().size() == numCardsInHand-1);
		}
		//create an arraylist of cards to record all unique cards in all of the players' hands
		ArrayList<Card> seenCards = new ArrayList<Card>();
		for (int i = 0; i < people.length; i++) {
			//iterate through each card in each player's hand and check to see if we have seen it before and if not add it to the list of seen cards
			for (int j = 0; j < people[i].getHand().size(); j++) {
				if(!seenCards.contains(people[i].getHand().get(j))) {
					seenCards.add(people[i].getHand().get(j));
				}
			}
		}
		//check to make sure the amount of unique cards we have seen is equal to the amount of cards we dealt
		assertEquals(seenCards.size(), 18);
		
	}
}
