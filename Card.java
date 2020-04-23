import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Card {

    private int rankValue;      //rankValue and suitValue are used for comparisons with other cards.
    private int suitValue;
    private String rankName;        // rankName and suitName are used for displaying the card in the console.
    private String suitName;

    static int initializer = 0;     // Used to get the next card in the text file
    Card tempCard;
    String[] tempArray; //possible error


    // Generates a card. Gets card data getCardData(), splits it into rank and suit, then creates the card.
    public Card()
    {
        String tempCardString = getCardData();

        tempArray = tempCardString.split(",");

        int tempRankValue = Integer.parseInt(tempArray[0]);
        int tempSuitValue = Integer.parseInt(tempArray[1]);
        String tempRankName = tempArray[2];
        String tempSuitName = tempArray[3];

        tempCard = new Card(tempRankValue, tempSuitValue, tempRankName, tempSuitName);
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
    public Card(int rank, int suit, String rankName, String suitName)
    {
        this.rankValue = rank;
        this.suitValue = suit;
        this.rankName = rankName;
        this.suitName = suitName;
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


    public int getRankValue() {
        return rankValue;
    }


    public int getSuitValue() {
        return suitValue;
    }


    public String getRankName() {
        return rankName;
    }

    public String getSuitName() {
        return suitName;
    }
                                            /*--------------- Anchors ----------------*/


    // Displays the rank and suit of the card.
    // I used this when I drew a card and wanted to make sure it was the first card in the deck.
    public String toString()
    {
        return (rankName + " of " + suitName);
    }
}

