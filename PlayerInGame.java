import java.util.LinkedList;

public class PlayerInGame {
	private LinkedList<Card> twoCardHand;
	private LinkedList<Card> fiveCardHand;
	private int handRank;
	private int chipCount;
	private int totalInvest;
	private boolean inHand;

	// Constructor: Sets chipCount and the two card hand the player has
	public PlayerInGame(/*LinkedList<Card> playerHand,*/ int count){
		//twoCardHand = playerHand;
		twoCardHand = new LinkedList<Card>();
		chipCount = count;
	}

	//
	public LinkedList<Card> getTwoCardHand() {
		return twoCardHand;
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
		LinkedList<Card> allCards = new LinkedList<Card>();

	}

	//
	public LinkedList<Card> getFiveCardHand() {
		return fiveCardHand;
	}

	//
	public void check() {

	}

	//
	public void fold() {

	}

	//
	public int bet(int biggestBet) {

	}

	//
	public int call(int biggestBet) {

	}

	public int blind(int amount){
		chipCount -= amount;
		totalInvest += amount;
		return amount;
	}

	public int getTotalInvest(){
		return totalInvest;
	}

	public void resetTotalInvest(){
		totalInvest = 0;
	}

	public boolean getInHand(){
		return inHand;
	}
		
	public void setInHand(boolean a){
		inHand = a;
	}	

    public int getHandRank(){
		return handRank;
	}
}
