import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<Card> cardDeck = new ArrayList<Card>();   // The deck - an ArrayList of 52 cards.
    Card tempCard, firstCard;


    // When the constructor is called, calls setDeck() which generates then sorts a deck.
    public Deck()
    {
        setDeck();
    }


    // Generates a sorted deck, then shuffles.
    public void setDeck()
    {
        for (int i = 0; i < 52; i++)
        {
            tempCard = new Card();  // Calls card constructor
            cardDeck.add(tempCard.getCard());   // Get card just returns the card created from the constructor
        }

        shuffleDeck();
    }


    // Shuffles the deck.
    public void shuffleDeck()
    {
        Collections.shuffle(cardDeck);
    }


                                        /*--------------- Anchors ----------------*/


    // Returns the first card in the deck. If the deck is empty, reset the deck.
    public Card drawCard()
    {
        if(cardDeck.size() == 0)
            resetDeck();

        firstCard = cardDeck.get(0);

        cardDeck.remove(0);

        return firstCard;
    }


    // Resets the deck.
    public void resetDeck()
    {
        cardDeck = new ArrayList<Card>();
        tempCard.resetInitializer();
        setDeck();
    }


    // Can be used to display the current cardDeck arrayList. Really only used for testing.
    public void displayDeck()
    {
        for (int i = 0; i < cardDeck.size();i++)
            System.out.println(cardDeck.get(i).getRank() + " of " + cardDeck.get(i).getSuit());
    }
}
