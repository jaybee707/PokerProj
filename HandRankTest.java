import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.Test;

public class HandRankTest {

	PlayerInGame p = new PlayerInGame(5);
	
	@Test
	public void StraightFlushTest() {
		Card myHand1 = new Card(12, 4, "Queen", "Spades");
		Card myHand2 = new Card(11, 4, "Jack", "Spades");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(6, 4, "6", "Spades");
		Card c2 = new Card(7, 4, "7", "Spades");
		Card c3 = new Card(8, 4, "8", "Spades");
		Card c4 = new Card(9, 4, "9", "Spades");
		Card c5 = new Card(10, 4, "10", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(c3);
		list.add(c4);
		list.add(c5);
		list.add(myHand2);
		list.add(myHand1);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(9, p.getHandRank());
	}
	
	@Test
	public void FourOfAKindTest() {
		Card myHand1 = new Card(2, 1, "2", "Clubs");
		Card myHand2 = new Card(14, 2, "Ace", "Diamonds");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(10, 4, "10", "Spades");
		Card c2 = new Card(10, 1, "10", "Clubs");
		Card c3 = new Card(10, 3, "10", "Hearts");
		Card c4 = new Card(10, 2, "10", "Diamonds");
		Card c5 = new Card(6, 4, "6", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(myHand2);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(8, p.getHandRank());
	}
	
	@Test
	public void fullHouseTest() {
		Card myHand1 = new Card(4, 1, "4", "Clubs");
		Card myHand2 = new Card(7, 2, "7", "Diamonds");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(4, 4, "4", "Spades");
		Card c2 = new Card(8, 1, "8", "Clubs");
		Card c3 = new Card(8, 3, "8", "Hearts");
		Card c4 = new Card(8, 2, "8", "Diamonds");
		Card c5 = new Card(14, 4, "Ace", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(myHand1);
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(7, p.getHandRank());
	}
	
	@Test
	public void flushTest() {
		Card myHand1 = new Card(5, 1, "5", "Diamonds");
		Card myHand2 = new Card(7, 2, "7", "Clubs");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(4, 4, "4", "Clubs");
		Card c2 = new Card(8, 1, "8", "Clubs");
		Card c3 = new Card(10, 3, "10", "Clubs");
		Card c4 = new Card(3, 2, "3", "Clubs");
		Card c5 = new Card(14, 4, "Ace", "Clubs");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(c1);
		list.add(myHand2);
		list.add(c2);
		list.add(c3);
		list.add(c5);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(6, p.getHandRank());
	}

	@Test
	public void straightTest() {
		Card myHand1 = new Card(4, 1, "4", "Diamonds");
		Card myHand2 = new Card(12, 2, "Queen", "Hearts");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(9, 4, "9", "Spades");
		Card c2 = new Card(11, 1, "Jack", "Clubs");
		Card c3 = new Card(10, 3, "10", "Diamonds");
		Card c4 = new Card(8, 2, "8", "Hearts");
		Card c5 = new Card(13, 4, "King", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(c1);
		list.add(c3);
		list.add(c2);
		list.add(myHand2);
		list.add(c5);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(5, p.getHandRank());
	}
	
	@Test
	public void threeOfAKindTest() {
		Card myHand1 = new Card(4, 1, "4", "Diamonds");
		Card myHand2 = new Card(2, 2, "2", "Hearts");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(2, 4, "2", "Spades");
		Card c2 = new Card(2, 1, "2", "Clubs");
		Card c3 = new Card(10, 3, "10", "Diamonds");
		Card c4 = new Card(12, 2, "Queen", "Hearts");
		Card c5 = new Card(11, 4, "Jack", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(c1);
		list.add(c2);
		list.add(myHand2);
		list.add(c5);
		list.add(c4);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(4, p.getHandRank());
	}
	
	@Test
	public void twoPairTest() {
		Card myHand1 = new Card(4, 1, "4", "Diamonds");
		Card myHand2 = new Card(6, 2, "6", "Hearts");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(4, 4, "4", "Spades");
		Card c2 = new Card(6, 1, "6", "Clubs");
		Card c3 = new Card(10, 3, "10", "Diamonds");
		Card c4 = new Card(10, 2, "10", "Hearts");
		Card c5 = new Card(11, 4, "Jack", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);
		
		list.clear();
		list.add(c1);
		list.add(myHand1);
		list.add(c2);
		list.add(myHand2);
		list.add(c5);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(3, p.getHandRank());

	}
	
	@Test
	public void pairTest() {
		Card myHand1 = new Card(5, 1, "5", "Diamonds");
		Card myHand2 = new Card(9, 2, "9", "Hearts");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(2, 4, "2", "Spades");
		Card c2 = new Card(2, 1, "2", "Clubs");
		Card c3 = new Card(10, 3, "10", "Diamonds");
		Card c4 = new Card(12, 2, "Queen", "Hearts");
		Card c5 = new Card(11, 4, "Jack", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);

		list.clear();
		list.add(c1);
		list.add(c2);
		list.add(myHand2);
		list.add(c5);
		list.add(c4);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(2, p.getHandRank());
	}
	
	@Test
	public void highCardTest() {
		Card myHand1 = new Card(11, 1, "Jack", "Diamonds");
		Card myHand2 = new Card(7, 2, "7", "Hearts");
		
		p.receiveCard(myHand1);
		p.receiveCard(myHand2);
		
		Card c1 = new Card(14, 4, "Ace", "Spades");
		Card c2 = new Card(2, 1, "2", "Clubs");
		Card c3 = new Card(4, 3, "4", "Diamonds");
		Card c4 = new Card(5, 2, "5", "Hearts");
		Card c5 = new Card(6, 4, "6", "Spades");
		
		LinkedList<Card> list = new LinkedList<Card>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		p.setBestFiveCardHand(list);

		list.clear();
		list.add(c4);
		list.add(c5);
		list.add(myHand2);
		list.add(myHand1);
		list.add(c1);
		
		assertEquals(list, p.getFiveCardHand());
		
		assertEquals(1, p.getHandRank());
	}
}
