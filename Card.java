import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Card {

    private String rank;
    private String suit;

    static int initializer = 0;     // Used to get the next card in the text file
    Card tempCard;
    String[] tempArray; //possible error


    // Generates a card. Gets card data getCardData(), splits it into rank and suit, then creates the card.
    public Card()
    {
        String tempCardString = getCardData();

        tempArray = tempCardString.split(",");

        String tempRank = tempArray[0];
        String tempSuit = tempArray[1];

        tempCard = new Card(tempRank, tempSuit);
    }


    // Gets a card's data from the text file and sends it to Card().
    public String getCardData()
    {
        try
        {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader("cardDatabase.txt"));
            String cardLineData = null;

            initializer+=1;
            for(int j = 0; j < initializer; j++)
                cardLineData = reader.readLine();

            return cardLineData; //a string
        }
        catch(IOException e)
        {
            return("error");
        }
    }


    // Used by Card() to set this cards rank and suit. Needed for getCard() to work.
    public Card(String rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
    }


    // Returns a card with a rank and suit.
    public Card getCard() {
        return tempCard;
    }


    // Used when resetting the deck.
    public void resetInitializer()
    {
        initializer = 0;
    }


    public String getRank() {
        return rank;
    }


    public String getSuit() {
        return suit;
    }


                                            /*--------------- Anchors ----------------*/


    // Displays the rank and suit of the card.
    // I used this when I drew a card and wanted to make sure it was the first card in the deck.
    public String toString()
    {
        return (rank + " of " + suit);
    }
}

