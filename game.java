import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.smartcardio.Card;

public class Game{
    private boolean winner;
    private static LinkedList<PlayerInGame> players;
    private static Deck dealer;
    private LinkedList<Card> community;
    private int mainPot;
    private int dealerButton;

    public Game(){
        //runs the game until winner == true
        LinkedList<Card> temp = new LinkedList<Card>();
        for(int i = 0; i < 3; i++){
            PlayerInGame n = new PlayerInGame(temp, 10000);
            players.push(n);
        }
        dealer = new Deck();
        winner = false;
        Random rand = new Random();
        dealerButton = rand.nextInt(players.size()); //pick dealer button start location

        while(winner == false){         //*****winner being t/f needs to be a test case*****/
            int roundWinner = round();
            
            //based off round winner
            //give player winnings
            //ListIterator win = players.listIterator(roundWinner);
            //PlayerInGame won = win.next();
            //won.addChips(mainPot);
            //reset pot
            //mainPot = 0;
            //reset deck
            //dealer.resetDeck();
            //move dealer button
            /*if(dealerButton == (players.size() - 1)){
                dealerButton = 0;
            }
            else{
                dealerButton++;
            }*/

            //check for tourney winner
            winner = tourneyWin();
        }

    }

    private static void deal(LinkedList<PlayerInGame> inHand){
        /****test cases to make sure cards are coming out in order to the right players***/
        //function for dealing players their cards
        System.out.println("deals cards");

        for(int i = 0; i < (2 * inHand.size()); i++){
            inHand.get(i % inHand.size()).receiveCard(dealer.drawCard());
        }

        for(int j = 0; j < inHand.size(); j++){
            LinkedList<Card> tem = inHand.get(i).getTwoCardHand();
            System.out.print(tem.get(0).toString());
            System.out.println(tem.get(1).toString());

        }
        

    }

    private static void preflop(LinkedList<PlayerInGame> inHand){
        /****test cases to make sure chip counts and pots are properly updated applies to flop, turn, etc***/
        //function for preflop betting
        System.out.println("starting with the player on the button, players take turns betting %n");
        System.out.println("round of betting ends when everyone matches highest amount bet or folds %n");

    }

    private static void flop(LinkedList<PlayerInGame> inHand){
        //function for flop betting
        System.out.println("dealer adds three cards to community pool %n");
        System.out.println("another round of betting %n");
    }

    private static void turn(LinkedList<PlayerInGame> inHand){
        //function for turn betting
        System.out.println("dealer adds a fourth card to community pool %n");
        System.out.println("another round of betting %n");
    }

    private static void river(LinkedList<PlayerInGame> inHand){
        //function for river betting
        System.out.println("dealer adds a fifth card to community pool %n");
        System.out.println("another round of betting %n");
    }

    private static int showdown(LinkedList<PlayerInGame> inHand){
        //compares hands to see who wins
        System.out.println("compares hands of players still in hand %n");
        System.out.println("adds pot to winning players chip pool %n");
    }

    private static boolean handWon(LinkedList<PlayerInGame> inHand){
        /*****test cases for only one player left in hand or multiple****/
        //checks for winner before showdown
        System.out.println("if only one player left hand is over");

    }

    private static int round(){
        dealer.shuffleDeck();
        LinkedList<PlayerInGame> playersInRound = players;
        deal(playersInRound);
        
        preflop(playersInRound);
        if(handwon(playersInRound)){
            //return int of winning player
        }
        flop(playersInRound);
        //if(handwon()){
            //return int of winning player
        //}
        turn(playersInRound);
        //if(handwon()){
            //return int of winning player
        //}
        river(playersInRound);
        //if(handwon()){
            //return int of winning player
        //}
        return showdown(playersInRound);
        /***test case to make sure proper int is being sent back****/
        /***test case to make sure money goes to correct player****/
    }

    private static boolean tourneyWin(){
        /*if(players.size() == 1){
            return true;
        }
        else{
            return false;
        }*/
        return true;
    }

}