import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;



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
        for(int i = 0; i < 3; i++){
            PlayerInGame n = new PlayerInGame(/*temp, */10000);
            players.add(n);
        }
        dealer = new Deck();
        winner = false;
        
        dealerButton = 0;
        mainPot = 0;
        curBlind = 0;
        smallBlind = blindSchedule[curBlind];
        blindClock.scheduleAtFixedRate(changeClock, 1000 * 60 * 15, 1000 * 60 * 15);


        while(winner == false){         //*****winner being t/f needs to be a test case*****/
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



    }

    private static void deal(){
        /****test cases to make sure cards are coming out in order to the right players***/
        //function for dealing players their cards
        //System.out.println("deals cards");

        for(int i = 0; i < (2 * players.size()); i++){
            players.get(i % players.size()).receiveCard(dealer.drawCard());
        }

        for(int j = 0; j < players.size(); j++){
            LinkedList<Card> tem = players.get(j).getTwoCardHand();
            System.out.print(tem.get(0).toString());
            System.out.println(tem.get(1).toString());

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
        do{
            getTurn(players.size() - 1);
            getTurn(0);
            if(players.size() == 3){
                getTurn(1);
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
        
        biggestBet = 0;

        do{
            getTurn(0);
            getTurn(1);
            if(players.size() == 3){
                getTurn(2);
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
        
        biggestBet = 0;
        do{
            getTurn(0);
            getTurn(1);
            if(players.size() == 3){
                getTurn(2);
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
        biggestBet = 0;
        do{
            getTurn(0);
            getTurn(1);
            if(players.size() == 3){
                getTurn(2);
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
                     ){
                highestIndex = compareKickers(i, highestIndex);
            }else if(players.get(i).getHandRank() < highest){
                players.get(i).setInHand(false);
            }
        }
        System.out.println("adds pot to winning players chip pool \n");

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
        if(players.get(a).getKickerOne() > players.get(b).getKickerOne()){
            return a;
        } else if(players.get(a).getKickerOne() < players.get(b).getKickerOne()){
            return b;
        }

        if(players.get(a).getKickerTwo() > players.get(b).getKickerTwo()){
            return a;
        } else if(players.get(a).getKickerTwo() < players.get(b).getKickerTwo()){
            return b;
        }

        if(players.get(a).getKickerThree() > players.get(b).getKickerThree()){
            return a;
        } else if(players.get(a).getKickerThree() < players.get(b).getKickerThree()){
            return b;
        }

        if(players.get(a).getKickerFour() > players.get(b).getKickerFour()){
            return a;
        } else if(players.get(a).getKickerFour() < players.get(b).getKickerFour()){
            return b;
        }

        if(players.get(a).getKickerFive() > players.get(b).getKickerFive()){
            return a;
        } else if(players.get(a).getKickerFive() < players.get(b).getKickerFive()){
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
        if(handwon()){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    players.get(i).addChips(mainPot);
                    return 1;
                }
            }
        }
        turn();
        if(handwon()){
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getInHand()){
                    players.get(i).addChips(mainPot);
                    return 1;
                }
            }
        }
        river();
        if(handwon()){
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
        return true;
    }

    private static void getTurn(int action){
        Scanner sc = new Scanner(System.in);
        int userIn;

        while(players.get(action).getInHand()){
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
                    }
                    else{
                        mainPot += players.get(action).call(biggestBet);
                    }
                    break;
                case 2:
                    mainPot += players.get(action).bet(biggestBet);
                    biggestBet = players.get(action).getTotalInvest();
                    break;
                case 3:
                    players.get(action).fold();
                    break;
                default:
                    break;
            }
        }
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
}