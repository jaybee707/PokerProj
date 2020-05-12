import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;



public class Game{
    private boolean winner;
    private static LinkedList<PlayerInGame> players;
    private static Deck dealer;
    private static LinkedList<Card> community;
    private static int mainPot;
    private static int smallBlind;
    private static int dealerButton;
    private static int biggestBet;
    private int[] blindSchedule = {25, 50, 75, 100, 200, 300, 500, 800, 1000, 2000, 3000, 5000};
    private int curBlind;
    Timer blindClock = new Timer();
    TimerTask changeClock = new TimerTask() {
        public void run() {
            smallBlind = blindSchedule[++curBlind];
        }
    };

    public Game(){
        //runs the game until winner == true
        //LinkedList<Card> temp = new LinkedList<Card>();
        players = new LinkedList<PlayerInGame>();
        for(int i = 0; i < 2; i++){
            PlayerInGame n = new PlayerInGame(/*temp, */10000, true);
            players.add(n);
        }
        players.add(new PlayerInGame(10000, false));
        dealer = new Deck();
        winner = false;

        dealerButton = 0;
        mainPot = 0;
        curBlind = 0;
        smallBlind = blindSchedule[curBlind];
        blindClock.scheduleAtFixedRate(changeClock, 1000 * 60 * 15, 1000 * 60 * 15);


        while(winner == false){         //*****winner being t/f needs to be a test case*****/
            System.out.println("let the games begin!");
            round();
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getChipCount() == 0){
                    players.remove(i);
                }
            }
            mainPot = 0;
            players.add(players.removeFirst());
            dealer.resetDeck();
            dealer.shuffleDeck();
            winner = tourneyWin();
        }

        if(players.get(0).getAI()){
            System.out.println("other player wins!");
        }
        else{
            System.out.println("you win!");
        }
    }

    private static void deal(){
        /****test cases to make sure cards are coming out in order to the right players***/
        //function for dealing players their cards
        //System.out.println("deals cards");

        for(int i = 0; i < (2 * players.size()); i++){
            players.get(i % players.size()).receiveCard(dealer.drawCard());
        }



    }

    private static void preflop(){
        /****test cases to make sure chip counts and pots are properly updated applies to flop, turn, etc***/
        //function for preflop betting


        for(int i = 0; i < players.size(); i++){
            players.get(i).resetTotalInvest();
        }
        mainPot += players.get(0).blind(smallBlind);
        mainPot += players.get(1).blind(smallBlind * 2);
        biggestBet = smallBlind * 2;
        deal();

        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).getAI()){
                LinkedList<Card> temp = players.get(i).getTwoCardHand();
                System.out.println(temp.get(0).toString() + temp.get(1).toString());
            }
        }

        do{
            if(players.get((players.size() - 1)).getAI()){
                AIFlopBet(players.size() - 1);
            }
            else{
                getTurn(players.size() - 1);
            }
            if(players.get(0).getAI()){
                AIFlopBet(0);
            } else {
                getTurn(0);
            }
            if(players.size() == 3){
                if(players.get(1).getAI()){
                    AIFlopBet(1);
                }
                else{
                    getTurn(1);
                }
            }
        }while(!potsRight());

    }

    private static void flop(){
        //function for flop betting
        for(int i = 0; i < players.size(); i++){
            players.get(i).resetTotalInvest();
        }
        dealer.drawCard();
        for(int i = 0; i < 3; i++){
            community.add(dealer.drawCard());
        }

        String communityPool = "";

        for(int i = 0; i < community.size(); i++){
            communityPool += community.get(i).toString() + " ";
        }
        System.out.println(communityPool);
        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).getAI()){
                LinkedList<Card> temp = players.get(i).getTwoCardHand();
                System.out.println(temp.get(0).toString() + temp.get(1).toString());
            }
        }


        biggestBet = 0;

        do{
            if(players.get(0).getAI()){
                AIPostBet(0);
            }
            else{
                getTurn(0);
            }
            if(players.get(1).getAI()){
                AIPostBet(1);
            } else {
                getTurn(1);
            }
            if(players.size() == 3){
                if(players.get(2).getAI()){
                    AIPostBet(2);
                }
                else{
                    getTurn(2);
                }
            }
        }while(!potsRight());
    }

    private static void turn(){
        //function for turn betting
        for(int i = 0; i < players.size(); i++){
            players.get(i).resetTotalInvest();
        }
        dealer.drawCard();
        community.add(dealer.drawCard());

        String communityPool = "";

        for(int i = 0; i < community.size(); i++){
            communityPool += community.get(i).toString() + " ";
        }
        System.out.println(communityPool);
        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).getAI()){
                LinkedList<Card> temp = players.get(i).getTwoCardHand();
                System.out.println(temp.get(0).toString() + temp.get(1).toString());
            }
        }

        biggestBet = 0;
        do{
            if(players.get(0).getAI()){
                AIPostBet(0);
            }
            else{
                getTurn(0);
            }
            if(players.get(1).getAI()){
                AIPostBet(1);
            } else {
                getTurn(1);
            }
            if(players.size() == 3){
                if(players.get(2).getAI()){
                    AIPostBet(2);
                }
                else{
                    getTurn(2);
                }
            }
        }while(!potsRight());
    }

    private static void river(){
        //function for river betting
        for(int i = 0; i < players.size(); i++){
            players.get(i).resetTotalInvest();
        }
        dealer.drawCard();
        community.add(dealer.drawCard());

        String communityPool = "";

        for(int i = 0; i < community.size(); i++){
            communityPool += community.get(i).toString() + " ";
        }
        System.out.println(communityPool);
        for(int i = 0; i < players.size(); i++){
            if(!players.get(i).getAI()){
                LinkedList<Card> temp = players.get(i).getTwoCardHand();
                System.out.println(temp.get(0).toString() + temp.get(1).toString());
            }
        }

        biggestBet = 0;
        do{
            if(players.get(0).getAI()){
                AIPostBet(0);
            }
            else{
                getTurn(0);
            }
            if(players.get(1).getAI()){
                AIPostBet(1);
            } else {
                getTurn(1);
            }
            if(players.size() == 3){
                if(players.get(2).getAI()){
                    AIPostBet(2);
                }
                else{
                    getTurn(2);
                }
            }
        }while(!potsRight());
    }

    private static int showdown(){
        //compares hands to see who wins

        int highest = -1;
        int highestIndex = 0;

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getHandRank() > highest &&
                    players.get(i).getInHand()){
                highest = players.get(i).getHandRank();
                highestIndex = i;
            }else if(players.get(i).getHandRank() == highest &&
                     players.get(i).getInHand()
            ){
                highestIndex = compareKickers(i, highestIndex);
            }else if(players.get(i).getHandRank() < highest){
                players.get(i).setInHand(false);
            }
        }
        if(highestIndex >= 0){
            System.out.println("player " + highestIndex + " wins the hand");
        }


        if(highestIndex == -1){
            int numWinners = 0;
            boolean winIndex[] = new boolean[3];
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    numWinners++;
                    winIndex[i] = true;
                }
            }

            for(int i = 0; i < players.size(); i++){
                if(winIndex[i] == true){
                    players.get(i).addChips(mainPot / numWinners);
                }
            }
        }
        return 1;
    }

    private static int compareKickers(int a, int b){
        LinkedList<Card> handA = players.get(a).getFiveCardHand();
        LinkedList<Card> handB = players.get(b).getFiveCardHand();

        if(handA.get(0).getRankValue() > handB.get(0).getRankValue()){
            return a;
        } else if (handB.get(0).getRankValue() > handA.get(0).getRankValue()){
            return b;
        }else if(handA.get(1).getRankValue() > handB.get(1).getRankValue()){
            return a;
        } else if (handB.get(1).getRankValue() > handA.get(1).getRankValue()){
            return b;
        } else if(handA.get(2).getRankValue() > handB.get(2).getRankValue()){
            return a;
        } else if (handB.get(2).getRankValue() > handA.get(2).getRankValue()){
            return b;
        } else if(handA.get(3).getRankValue() > handB.get(3).getRankValue()){
            return a;
        } else if (handB.get(3).getRankValue() > handA.get(3).getRankValue()){
            return b;
        } else if(handA.get(4).getRankValue() > handB.get(4).getRankValue()){
            return a;
        } else if (handB.get(4).getRankValue() > handA.get(4).getRankValue()){
            return b;
        }
        return -1;
    }

    private static boolean handWon(){
        /*****test cases for only one player left in hand or multiple****/
        //checks for winner before showdown
        int numInHand = 0;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getInHand()){
                numInHand++;
            }
        }

        if(numInHand == 1){
            return true;
        }else {
            return false;
        }

    }

    private static int round(){
        dealer.shuffleDeck();

        for(int i = 0; i < players.size(); i++){
            players.get(i).setInHand(true);
        }
        preflop();
        if(handWon()){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    players.get(i).addChips(mainPot);
                    return 1;
                }
            }
        }
        flop();
        if(handWon()){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    players.get(i).addChips(mainPot);
                    return 1;
                }
            }
        }
        turn();
        if(handWon()){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    players.get(i).addChips(mainPot);
                    return 1;
                }
            }
        }
        river();
        if(handWon()){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    players.get(i).addChips(mainPot);
                    return 1;
                }
            }
        }
        return showdown();
        /***test case to make sure proper int is being sent back****/
        /***test case to make sure money goes to correct player****/
    }

    private static boolean tourneyWin(){
        if(players.size() == 1){
            return true;
        }
        else{
            return false;
        }

    }

    private static void getTurn(int action){
        Scanner sc = new Scanner(System.in);
        int userIn;

        while(players.get(action).getInHand() && !players.get(action).getAI()){
            if(biggestBet == 0){
                System.out.print("1 for check, 2 to raise, 3 to fold");
            }
            else{
                System.out.print("1 for call, 2 to raise, 3 to fold");
            }
            userIn = sc.nextInt();

            switch (userIn) {
                case 1:
                    if(biggestBet == 0){
                        players.get(action).check();
                        System.out.println("player " + action + " checks");
                    }
                    else{
                        mainPot += players.get(action).call(biggestBet);
                        System.out.println("player " + action + " calls " + biggestBet);
                    }
                    break;
                case 2:
                    mainPot += players.get(action).bet(biggestBet);
                    biggestBet = players.get(action).getTotalInvest();
                    System.out.println("player " + action + " raised to " + biggestBet);
                    break;
                case 3:
                    players.get(action).fold();
                    System.out.println("player " + action + " folds");
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }

    private static boolean potsRight(){
        boolean pr = true;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getTotalInvest() != biggestBet && !players.get(i).isAllIn()
                    && players.get(i).getInHand()){
                pr = false;
                break;
            }
        }
        return pr;
    }

    private static void AIFlopBet(int index){
        LinkedList<Card> aiHand = players.get(index).getTwoCardHand();
        int value1 = aiHand.get(0).getRankValue();
        int value2 = aiHand.get(1).getRankValue();

        if(value1 == value2){
            if(players.get(index).getTotalInvest() == 0){
                mainPot += players.get(index).bet(biggestBet);
                biggestBet = players.get(index).getTotalInvest();
                System.out.println("player " + index + " raised to " + biggestBet);
            } else {
                mainPot += players.get(index).call(biggestBet);
                System.out.println("player " + index + " calls " + biggestBet);
            }
        } else if (((value1 == 14) || (value2 == 14)) && (value1 > 11 || value2 > 11)){
            if(players.get(index).getTotalInvest() == 0){
                mainPot += players.get(index).bet(biggestBet);
                biggestBet = players.get(index).getTotalInvest();
                System.out.println("player " + index + " raised to " + biggestBet);
            } else {
                mainPot += players.get(index).call(biggestBet);
                System.out.println("player " + index + " calls " + biggestBet);
            }
        } else if ((value1 + value2) > 15){
            players.get(index).call(biggestBet);
            System.out.println("player " + index + " calls " + biggestBet);
        } else {
            players.get(index).fold();
            System.out.println("player " + index + " folds");
        }
    }

    private static void AIPostBet(int index){
        if(players.get(index).getHandRank() > 4 && players.get(index).getTotalInvest() == 0){
            mainPot += players.get(index).bet(biggestBet);
            biggestBet = players.get(index).getTotalInvest();
            System.out.println("player " + index + " raised to " + biggestBet);
        } else if (players.get(index).getHandRank() > 4 && players.get(index).getTotalInvest() > 0) {
            mainPot += players.get(index).call(biggestBet);
            System.out.println("player " + index + " calls " + biggestBet);
        } else if(players.get(index).getHandRank() > 1 && biggestBet > 0){
            mainPot += players.get(index).call(biggestBet);
            System.out.println("player " + index + " calls " + biggestBet);
        } else if(players.get(index).getHandRank() > 1 && biggestBet == 0){
            mainPot += players.get(index).bet(biggestBet);
            biggestBet = players.get(index).getTotalInvest();
            System.out.println("player " + index + " raised to " + biggestBet);
        } else if(players.get(index).getHandRank() == 1 && biggestBet == 0){
            players.get(index).check();
            System.out.println("player " + index + " checks");
        } else {
            players.get(index).fold();
            System.out.println("player " + index + " folds");
        }
    }
}