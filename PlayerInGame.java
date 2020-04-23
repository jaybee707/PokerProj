import java.util.LinkedList;

public class PlayerInGame {
	private LinkedList<Card> twoCardHand;
	private LinkedList<Card> fiveCardHand;
	private chipCount;

	// Constructor: Sets chipCount and the two card hand the player has
	public PlayerInGame(LinkedList<Card> playerHand, int count){
		twoCardHand = playerHand;
		chipCount = count;
	}

	//
	public LinkedList<Card> getTwoCardHand() {
		return 2CardHand;
	}

	//
	public int getChipCount() {
		return chipCount;
	}

	//
	public void addChips(int chips) {
		chipCount += chips;
	}

	public void receiveCard(Card newCard) {
		twoCardHand.add(newCard);
	}


	/******* This function needs to be tested using JUnit, add to Test Plan *******/
	public void setBestFiveCardHand(LinkedList<Card> hand) {
		// Takes in the community 5 card hand as a paramater and sets the players fiveCardHand to
		// the best 5 card hand the player can have using the 2 cards hand they currently have
	}

	//
	public LinkedList<Card> getFiveCardHand() {
		return fiveCardHand;
	}

	//
	public void check() {

	}

	//
	public void hold() {

	}

	//
	public void bet() {

	}

	//
	public void call() {

	}

}
