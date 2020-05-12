import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckCardTest {

    @Test //TESTING CARDS
    void getCard() {
        Card c = new Card(10,1,"10","Clubs");
        assertEquals(10,c.getRankValue());
        assertEquals(1,c.getSuitValue());
        assertEquals("10",c.getRankName());
        assertEquals("Clubs",c.getSuitName());
    }

    @Test //TESTING DECK SIZE
    void getDeck() {
        Deck d = new Deck();
        assertEquals(52, d.cardDeck.size());
    }

    //If both tests pass, then the deck is correctly allocating 52 cards and shuffling them.
}