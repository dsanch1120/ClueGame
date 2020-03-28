/*
 * Created by Daniel Sanchez and Trent Douglas
 */
package clueGame;

public class Card {
	private String name;
	private CardType type;
	
	
	
	public Card() {
		super();
	}

	public String getCardName() {
		return name;
	}

	public void setCardName(String cardName) {
		this.name = cardName;
	}

	public CardType getType() {
		return type;
	}
	
	public void setCardType(String type) {
		switch(type) {
		case "ROOM":
			this.type = CardType.ROOM;
			break;
		case "PERSON":
			this.type = CardType.PERSON;
			break;
		case "WEAPON":
			this.type = CardType.WEAPON;
			break;
		default:
			this.type = null;
			break;
		}
		
	}

	public boolean equals() {
		return false;
	}
}
