import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class PlayerInGame {
	private LinkedList<Card> twoCardHand;
	private LinkedList<Card> fiveCardHand;
	private int handRank;
	private int chipCount;
	private int totalInvest;
	private boolean inHand;
	private boolean ai;
	private boolean allIn;

	private static final int STRAIGHT_FLUSH = 9;
	private static final int FOUR_OF_A_KIND = 8;
	private static final int FULL_HOUSE = 7;
	private static final int FLUSH = 6;
	private static final int STRAIGHT = 5;
	private static final int THREE_OF_A_KIND = 4;
	private static final int TWO_PAIR = 3;
	private static final int PAIR = 2;
	private static final int HIGH_CARD = 1;

	// Constructor: Sets chipCount and the two card hand the player has
	public PlayerInGame(/* LinkedList<Card> playerHand, */ int count, boolean com) {
		// twoCardHand = playerHand;
		twoCardHand = new LinkedList<Card>();
		chipCount = count;
		ai = com;
	}

	public boolean getAI(){
		return ai;
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
		// HashMap to keep track of the suits in the hand
		HashMap<String, LinkedList<Card>> suitMap = new HashMap<String, LinkedList<Card>>();
		// HashMap to keep track of the ranks in the hand
		HashMap<Integer, LinkedList<Card>> rankMap = new HashMap<Integer, LinkedList<Card>>();
		// HashMap to keep track of the 5 card hands the player can make
		HashMap<Integer, LinkedList<Card>> handMap = new HashMap<Integer, LinkedList<Card>>();

		populateHashMaps(hand, suitMap, rankMap);
		// Determine what kind of hand player has
		findStraightOrStraightFlush(rankMap, suitMap, handMap);
		// reset the hashMaps
		resetHashMaps(hand, suitMap, rankMap);
		findFlush(suitMap, handMap);
		// reset the hashMaps
		resetHashMaps(hand, suitMap, rankMap);
		findPairOrKindOrFullHouse(rankMap, handMap);
		// reset the hashMaps
		resetHashMaps(hand, suitMap, rankMap);
		findHighCard(rankMap, handMap);

		int maxRank = Collections.max(handMap.keySet());
		handRank = maxRank;
		fiveCardHand = handMap.get(maxRank);
		Collections.sort(fiveCardHand, new SortByRank());
	}

	//
	public LinkedList<Card> getFiveCardHand() {
		return fiveCardHand;
	}

	//
	public void check() {
		return;
	}

	//
	public void fold() {
		inHand = false;
		twoCardHand = null;
	}

	//
	public int bet(int biggestBet) {
		int betAmount;
		if(ai){
			if(biggestBet == 0){
				betAmount = chipCount / 10;
				chipCount -= betAmount;
				totalInvest += betAmount;
				if(chipCount <= 0 && totalInvest > 0){
					allIn = true;
				}
				return betAmount;
			} else {
				betAmount = biggestBet * 2;
				chipCount -= betAmount;
				totalInvest += betAmount;
				if(chipCount <= 0 && totalInvest > 0){
					allIn = true;
				}
				return betAmount;
			}
		}
		else{
			System.out.println("how much to bet from " + biggestBet + " to " + chipCount);
			Scanner in = new Scanner(System.in);
			betAmount = in.nextInt();
			chipCount -= betAmount;
			totalInvest += betAmount;
			if(chipCount <= 0 && totalInvest > 0){
				allIn = true;
			}
			return betAmount;
		}

	}

	//
	public int call(int biggestBet) {
		chipCount -= biggestBet - totalInvest;
		totalInvest = biggestBet;
		if(chipCount <= 0 && totalInvest > 0){
			allIn = true;
		}
		return biggestBet - totalInvest;
	}

	public int blind(int amount) {
		chipCount -= amount;
		totalInvest += amount;
		return amount;
	}

	public boolean isAllIn(){
		return allIn;
	}


	public int getTotalInvest() {
		return totalInvest;
	}

	public void resetTotalInvest() {
		totalInvest = 0;
	}

	public boolean getInHand() {
		return inHand;
	}

	public void setInHand(boolean a) {
		inHand = a;
	}

	public int getHandRank() {
		return handRank;
	}

	/************* HELPER CLASS AND FUNCTIONS **************/

	class SortByRank implements Comparator<Card> {

		@Override
		public int compare(Card o1, Card o2) {
			// TODO Auto-generated method stub
			return Integer.compare(o1.getRankValue(), o2.getRankValue());
		}

	}

	public void addRankHandToHashMap(HashMap<Integer, LinkedList<Card>> map) {
		for (Card c : twoCardHand) {
			LinkedList<Card> rankList = map.get(c.getRankValue());
			if (rankList == null) {
				map.put(c.getRankValue(), rankList = new LinkedList<Card>());
			}
			rankList.add(c);
		}
	}

	public void addSuitHandToHashMap(HashMap<String, LinkedList<Card>> map) {
		for (Card c : twoCardHand) {
			LinkedList<Card> suitList = map.get(c.getSuitName());
			if (suitList == null) {
				map.put(c.getSuitName(), suitList = new LinkedList<Card>());
			}
			suitList.add(c);
		}
	}

	public void populateHashMaps(LinkedList<Card> hand, HashMap<String, LinkedList<Card>> suitMap,
<<<<<<< HEAD
								 HashMap<Integer, LinkedList<Card>> rankMap) {
=======
	HashMap<Integer, LinkedList<Card>> rankMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		for (Card c : hand) {
			LinkedList<Card> rankList = rankMap.get(c.getRankValue());
			if (rankList == null) {
				rankMap.put(c.getRankValue(), rankList = new LinkedList<Card>());
			}
			rankList.add(c);

			LinkedList<Card> suitList = suitMap.get(c.getSuitName());
			if (suitList == null) {
				suitMap.put(c.getSuitName(), suitList = new LinkedList<Card>());
			}
			suitList.add(c);
		}
	}

	public void resetHashMaps(LinkedList<Card> hand, HashMap<String, LinkedList<Card>> suitMap,
<<<<<<< HEAD
							  HashMap<Integer, LinkedList<Card>> rankMap) {
=======
	HashMap<Integer, LinkedList<Card>> rankMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		suitMap.clear();
		rankMap.clear();
		populateHashMaps(hand, suitMap, rankMap);
	}

	/************* END OF HELPER FUNCTIONS *************/

	/************* FLUSH, STRAIGHT, AND STRAIGHT FLUSH FUNCTIONS ************/

	public void flush(HashMap<String, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap,
<<<<<<< HEAD
					  int sameSuitCount) {
=======
	int sameSuitCount) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		LinkedList<Card> temp = new LinkedList<Card>();
		// Add the cards from your 2 card hand to see if you have a flush
		if (sameSuitCount == 3 || sameSuitCount == 4) {
			addSuitHandToHashMap(map);
			findAndAddFlush(map, handMap, temp);
		}
		// if the hand already had 5 cards of the same suit
		else if (sameSuitCount == 5) {
			addSuitHandToHashMap(map);
			findAndAddFlush2(map, handMap, temp);
		}

	}

	public void straight(HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap,
<<<<<<< HEAD
						 LinkedList<Card> valueList) {
=======
	LinkedList<Card> valueList) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		Object[] arr = valueList.toArray();
		Card[] cardArr = Arrays.copyOf(arr, arr.length, Card[].class);

		LinkedList<Card> temp1 = new LinkedList<Card>();
		LinkedList<Card> temp2 = new LinkedList<Card>();
		LinkedList<Card> temp3 = new LinkedList<Card>();

		LinkedList<Card> straight = getHighestStraight(cardArr, temp1, temp2, temp3);

		if (straight != null) {
			handMap.put(STRAIGHT, straight);
		}

	}

	public void straightFlush(HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap,
<<<<<<< HEAD
							  LinkedList<Card> valueList, LinkedList<Card> suitList) {
=======
	LinkedList<Card> valueList, LinkedList<Card> suitList) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		Object[] arr = valueList.toArray();
		Card[] cardArr = Arrays.copyOf(arr, arr.length, Card[].class);

		LinkedList<Card> temp1 = new LinkedList<Card>();
		LinkedList<Card> temp2 = new LinkedList<Card>();
		LinkedList<Card> temp3 = new LinkedList<Card>();

		LinkedList<Card> straight = getHighestStraightFlush(cardArr, temp1, temp2, temp3, suitList, handMap);

		if (straight != null) {
			handMap.put(STRAIGHT, straight);
		}

	}

	public void findStraightOrStraightFlush(HashMap<Integer, LinkedList<Card>> map,
<<<<<<< HEAD
											HashMap<String, LinkedList<Card>> sMap, HashMap<Integer, LinkedList<Card>> handMap) {
=======
	HashMap<String, LinkedList<Card>> sMap, HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		boolean straightHand = true;
		addRankHandToHashMap(map);
		addSuitHandToHashMap(sMap);

		LinkedList<Card> list = new LinkedList<Card>();
		// loop through and check if any rank has multiple values, i.e has a pair, or
		// 3/4 of a kind
		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			// if you've found at least 2 cards with the same rank, you can't have a
			// straight
			if (elem.getValue().size() > 1) {
				straightHand = false;
				break;
			} else {
				list.addAll(elem.getValue());
			}
		}

		LinkedList<Card> suitList = new LinkedList<Card>();
		if (straightHand == true) {
			for (Entry<String, LinkedList<Card>> elem : sMap.entrySet()) {
				if (elem.getValue().size() >= 5) {
					suitList.addAll(elem.getValue());
				}
			}
			// possibility to be a straight flush
			if (!suitList.isEmpty()) {
				Collections.sort(suitList, new SortByRank());
				Collections.sort(list, new SortByRank());
				straightFlush(map, handMap, list, suitList);
			} else {
				Collections.sort(list, new SortByRank());
				straight(map, handMap, list);
			}
		}
	}

	public LinkedList<Card> getHighestStraight(Card[] cardArr, LinkedList<Card> temp1, LinkedList<Card> temp2,
<<<<<<< HEAD
											   LinkedList<Card> temp3) {
=======
	LinkedList<Card> temp3) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		for (int i = 0; i < cardArr.length - 2; i++) {
			if (cardArr[i].getRankValue() + 1 != cardArr[i + 1].getRankValue()) {
				// Not in sequential order
				break;
			} else {
				temp1.add(cardArr[i]);
			}
		}

		for (int j = 1; j < cardArr.length - 1; j++) {
			if (cardArr[j - 1].getRankValue() + 1 != cardArr[j].getRankValue()) {
				// Not in sequential order
				break;
			} else {
				temp2.add(cardArr[j]);
			}
		}

		for (int k = 2; k < cardArr.length; k++) {
			if (cardArr[k - 1].getRankValue() + 1 != cardArr[k].getRankValue()) {
				// Not in sequential order
				break;
			} else {
				temp3.add(cardArr[k]);
			}
		}
		if (temp3.size() == 5) {
			if (temp3.contains(twoCardHand.getFirst()) || temp3.contains(twoCardHand.getLast())) {
				return temp3;
			}
		}
		if (temp2.size() == 5) {
			if (temp2.contains(twoCardHand.getFirst()) || temp2.contains(twoCardHand.getLast())) {
				return temp2;
			}
		}
		if (temp1.size() == 5) {
			if (temp1.contains(twoCardHand.getFirst()) || temp1.contains(twoCardHand.getLast())) {
				return temp1;
			}
		}

		return null;
	}

	public LinkedList<Card> getHighestStraightFlush(Card[] cardArr, LinkedList<Card> temp1, LinkedList<Card> temp2,
<<<<<<< HEAD
													LinkedList<Card> temp3, LinkedList<Card> suitList, HashMap<Integer, LinkedList<Card>> handMap) {
=======
	LinkedList<Card> temp3, LinkedList<Card> suitList, HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		for (int i = 0; i < cardArr.length - 2; i++) {
			if (cardArr[i].getRankValue() + 1 != cardArr[i + 1].getRankValue()) {
				// Not in sequential order
				break;
			} else {
				temp1.add(cardArr[i]);
			}
		}

		for (int j = 1; j < cardArr.length - 1; j++) {
			if (cardArr[j - 1].getRankValue() + 1 != cardArr[j].getRankValue()) {
				// Not in sequential order
				break;
			} else {
				temp2.add(cardArr[j]);
			}
		}

		for (int k = 2; k < cardArr.length; k++) {
			if (cardArr[k - 1].getRankValue() + 1 != cardArr[k].getRankValue()) {
				// Not in sequential order
				break;
			} else {
				temp3.add(cardArr[k]);
			}
		}

		if (temp3.size() == 5) {
			if (suitList.containsAll(temp3)) {
				handMap.put(STRAIGHT_FLUSH, temp3);
				return null;
			}
			return temp3;
		} else if (temp2.size() == 5) {
			if (suitList.containsAll(temp2)) {
				handMap.put(STRAIGHT_FLUSH, temp2);
				return null;
			}
			return temp2;
		} else if (temp1.size() == 5) {
			if (suitList.containsAll(temp1)) {
				handMap.put(STRAIGHT_FLUSH, temp1);
				return null;
			}
			return temp1;
		}

		return null;
	}

	public void findFlush(HashMap<String, LinkedList<Card>> suit, HashMap<Integer, LinkedList<Card>> handMap) {
		for (Entry<String, LinkedList<Card>> elem : suit.entrySet()) {
			if (elem.getValue().size() == 3) {
				flush(suit, handMap, 3);
			} else if (elem.getValue().size() == 4) {
				flush(suit, handMap, 4);
				break;
			} else if (elem.getValue().size() == 5) {
				flush(suit, handMap, 5);
				break;
			}
		}
	}

	public void findAndAddFlush(HashMap<String, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap,
<<<<<<< HEAD
								LinkedList<Card> temp) {
=======
	LinkedList<Card> temp) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		for (Entry<String, LinkedList<Card>> elem : map.entrySet()) {
			// If after adding your two cards you get 5 with the same suit, you have a flush
			if (elem.getValue().size() == 5) {
				Collections.sort(elem.getValue(), new SortByRank());
				// insert into map of hands with flush hand ranking
				handMap.put(FLUSH, elem.getValue());
			}
			// If after adding your two cards, you have 6 with the same suit, you need to
			// drop the lowest rank card
			else if (elem.getValue().size() == 6) {
				temp = (LinkedList<Card>) elem.getValue().clone();
				// Sort the list of 6 cards by rank
				Collections.sort(temp, new SortByRank());
				// remove the smallest rank card
				temp.removeFirst();
				// insert into map of hands with flush hand ranking
				handMap.put(FLUSH, temp);
			}
		}
	}

	public void findAndAddFlush2(HashMap<String, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap,
<<<<<<< HEAD
								 LinkedList<Card> temp) {
=======
	LinkedList<Card> temp) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		for (Entry<String, LinkedList<Card>> elem : map.entrySet()) {
			// if after adding your cards, one suit has a flush with one of your cards + 5
			// others, need to remove one
			if (elem.getValue().size() == 6) {
				Card c = null;
				temp = (LinkedList<Card>) elem.getValue().clone();
				// Sort the list of 6 cards by rank
				Collections.sort(temp, new SortByRank());
				// if the smallest rank is your card, remove the second card in the list
				for (Card card : temp) {
					if (twoCardHand.contains(card)) {
						// do nothing
					} else {
						c = card;
						break;
					}
				}
				temp.remove(c);
				// insert into map of hands with flush hand ranking
				handMap.put(FLUSH, temp);
			}
			// if after adding your cards, one suit has a flush with both your cards + 5
			// others, need to remove 2
			else if (elem.getValue().size() == 7) {
				Card[] arr = new Card[2];
				int count = 0;
				temp = (LinkedList<Card>) elem.getValue().clone();
				// Sort the list of 7 cards by rank
				Collections.sort(temp, new SortByRank());
				for (Card card : temp) {
					if (count == 2) {
						break;
					}
					if (twoCardHand.contains(card)) {
						// do nothing
					} else {
						arr[count] = card;
						count++;
					}
				}
				for (Card c : arr) {
					temp.remove(c);
				}
				// insert into map of hands with flush hand ranking
				handMap.put(FLUSH, temp);
			}
		}
	}

	/************** END OF FLUSH, STRAIGHT, AND STRAIGHT FLUSH FUNCTIONS ***************/

	/************** 4 OF KIND, 3 OF KIND, FULL HOUSE, 2 PAIR, PAIR AND HIGH CARD FUNCTIONS **************/

	public void findPairOrKindOrFullHouse(HashMap<Integer, LinkedList<Card>> map,
<<<<<<< HEAD
										  HashMap<Integer, LinkedList<Card>> handMap) {
=======
	HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		int pairCounter = 0;
		int threeOfKind = 0;
		int fourOfKind = 0;
		boolean fromHand = false;
		LinkedList<Card> fourKindList = new LinkedList<Card>();
		LinkedList<Card> threeKindList = new LinkedList<Card>();
		LinkedList<Card> pairList = new LinkedList<Card>();
		LinkedList<Card> pairFrom2CardHand = new LinkedList<Card>();
		LinkedList<Integer> keyToRemove = new LinkedList<Integer>();

		addRankHandToHashMap(map);

		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			// pair
			if (elem.getValue().size() == 2) {
				for (Card c : elem.getValue()) {
					if (twoCardHand.contains(c)) {
						pairFrom2CardHand.addAll(elem.getValue());
						fromHand = true;
						break;
					}
				}
				if (fromHand == false) {
					pairList.addAll(elem.getValue());
				}
				keyToRemove.add(elem.getKey());
				pairCounter++;
				fromHand = false;
			}
			// 3 of kind
			else if (elem.getValue().size() == 3) {
				threeKindList.addAll(elem.getValue());
				keyToRemove.add(elem.getKey());
				threeOfKind++;
			}
			// 4 of kind
			else if (elem.getValue().size() == 4) {
				fourKindList.addAll(elem.getValue());
				keyToRemove.add(elem.getKey());
				fourOfKind++;
			}
		}

		// there are no pairs, 3/4 of kinds, or full house, only options are straight,
		// flush and high
		if (pairCounter == 0 && threeOfKind == 0 && fourOfKind == 0) {
			return;
		}

		// call functions depending on what type of hand

		// check if it's a 4 of kind
		if (fourOfKind == 1) {
			for (int elem : keyToRemove) {
				if (map.get(elem).size() == 4) {
					map.remove(elem);
				}
			}
			findFourOfKind(fourKindList, map, handMap);
		}
		// check if full house
		else if (threeOfKind > 1) {
			for (int elem : keyToRemove) {
				if (map.get(elem).size() == 3) {
					map.remove(elem);
				}
			}
			findFullHouse(threeKindList, handMap);
		}
		// full house
		else if (threeOfKind == 1 && pairCounter > 0) {
			for (int elem : keyToRemove) {
				if (map.get(elem).size() == 3 || map.get(elem).size() == 2) {
					map.remove(elem);
				}
			}
			findFullHouseWithPair(threeKindList, pairList, pairFrom2CardHand, handMap);
		}
		// 3 of kind
		else if (threeOfKind == 1) {
			for (int elem : keyToRemove) {
				if (map.get(elem).size() == 3) {
					map.remove(elem);
				}
			}
			findThreeOfKind(threeKindList, map, handMap);
		}
		// 2 pair
		else if (pairCounter > 1) {
			for (int elem : keyToRemove) {
				if (map.get(elem).size() == 2) {
					map.remove(elem);
				}
			}
			findTwoPair(pairFrom2CardHand, pairList, map, handMap);
		}
		// pair
		else if (pairCounter == 1) {
			for (int elem : keyToRemove) {
				if (map.get(elem).size() == 2) {
					map.remove(elem);
				}
			}
			findPair(pairFrom2CardHand, pairList, map, handMap);
		}
	}

	public void findFourOfKind(LinkedList<Card> fourKindList, HashMap<Integer, LinkedList<Card>> map,
<<<<<<< HEAD
							   HashMap<Integer, LinkedList<Card>> handMap) {
=======
	HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		LinkedList<Card> list = new LinkedList<Card>();
		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			list.addAll(elem.getValue());
		}

		Collections.sort(list, new SortByRank());

		fourKindList.add(list.getLast());

		Collections.sort(fourKindList, new SortByRank());
		handMap.put(FOUR_OF_A_KIND, fourKindList);
	}

	public void findThreeOfKind(LinkedList<Card> threeKindList, HashMap<Integer, LinkedList<Card>> map,
<<<<<<< HEAD
								HashMap<Integer, LinkedList<Card>> handMap) {
=======
	HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		LinkedList<Card> list = new LinkedList<Card>();
		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			list.addAll(elem.getValue());
		}

		Collections.sort(list, new SortByRank());

		threeKindList.add(list.removeLast());
		threeKindList.add(list.removeLast());

		Collections.sort(threeKindList, new SortByRank());

		handMap.put(THREE_OF_A_KIND, threeKindList);

	}

	public void findFullHouseWithPair(LinkedList<Card> threeKindList, LinkedList<Card> pairList,
<<<<<<< HEAD
									  LinkedList<Card> twoCardHandList, HashMap<Integer, LinkedList<Card>> handMap) {
=======
	LinkedList<Card> twoCardHandList, HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		if (!pairList.isEmpty() && !twoCardHandList.isEmpty()) {
			LinkedList<Card> list = new LinkedList<Card>();
			list.addAll(pairList);
			list.addAll(twoCardHandList);
			Collections.sort(list, new SortByRank());

			threeKindList.add(list.removeLast());
			threeKindList.add(list.removeLast());

		}

		else if (!pairList.isEmpty()) {
			Collections.sort(pairList, new SortByRank());

			threeKindList.add(pairList.removeLast());
			threeKindList.add(pairList.removeLast());
		} else if (!twoCardHandList.isEmpty()) {
			Collections.sort(twoCardHandList, new SortByRank());

			threeKindList.add(twoCardHandList.removeLast());
			threeKindList.add(twoCardHandList.removeLast());
		}

		Collections.sort(threeKindList, new SortByRank());
		handMap.put(FULL_HOUSE, threeKindList);

	}

	public void findFullHouse(LinkedList<Card> threeKindList, HashMap<Integer, LinkedList<Card>> handMap) {
		Collections.sort(threeKindList, new SortByRank());
		threeKindList.removeFirst();

		handMap.put(FULL_HOUSE, threeKindList);

	}

	public void findTwoPair(LinkedList<Card> twoCardHandList, LinkedList<Card> pairList,
<<<<<<< HEAD
							HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap) {
=======
	HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		LinkedList<Card> list = new LinkedList<Card>();
		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			list.addAll(elem.getValue());
		}
		Collections.sort(list, new SortByRank());
		if (twoCardHandList.size() == 4) {
			Collections.sort(twoCardHandList, new SortByRank());
			twoCardHandList.add(list.getLast());

			handMap.put(TWO_PAIR, twoCardHandList);
		} else if (twoCardHandList.size() == 2) {
			Collections.sort(pairList, new SortByRank());
			twoCardHandList.add(pairList.removeLast());
			twoCardHandList.add(pairList.removeLast());
			twoCardHandList.add(list.getLast());

			Collections.sort(twoCardHandList, new SortByRank());
			handMap.put(TWO_PAIR, twoCardHandList);
		} else if (twoCardHandList.isEmpty()) {
			Collections.sort(twoCardHand, new SortByRank());
			pairList.addAll(map.get(twoCardHand.getLast().getRankValue()));

			Collections.sort(twoCardHand, new SortByRank());
			handMap.put(TWO_PAIR, pairList);
		}

	}

	public void findPair(LinkedList<Card> twoCardHandList, LinkedList<Card> pairList,
<<<<<<< HEAD
						 HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap) {
=======
	HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap) {
>>>>>>> e5498dc90c9d96ac259d0eb4d554ce1da7ee3e00
		LinkedList<Card> list = new LinkedList<Card>();
		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			list.addAll(elem.getValue());
		}
		Collections.sort(list, new SortByRank());

		if (!twoCardHandList.isEmpty()) {
			twoCardHandList.add(list.removeLast());
			twoCardHandList.add(list.removeLast());
			twoCardHandList.add(list.removeLast());

			handMap.put(PAIR, twoCardHandList);
		} else {
			Collections.sort(twoCardHand, new SortByRank());
			pairList.addAll(map.get(twoCardHand.getLast().getRankValue()));
			list.removeFirstOccurrence(map.get(twoCardHand.getLast().getRankValue()));
			pairList.add(list.removeLast());
			pairList.add(list.removeLast());

			Collections.sort(pairList, new SortByRank());
			handMap.put(PAIR, pairList);
		}
	}

	public void findHighCard(HashMap<Integer, LinkedList<Card>> map, HashMap<Integer, LinkedList<Card>> handMap) {
		addRankHandToHashMap(map);

		LinkedList<Card> list = new LinkedList<Card>();
		for (Entry<Integer, LinkedList<Card>> elem : map.entrySet()) {
			list.addAll(elem.getValue());
		}
		Collections.sort(list, new SortByRank());

		LinkedList<Card> highHand = new LinkedList<Card>();
		int count = 0;
		int cardCount = 0;
		while (count < 5) {
			if (twoCardHand.contains(list.getLast())) {
				cardCount++;
			}
			highHand.add(list.removeLast());
			count++;
		}

		if (cardCount == 0) {
			Collections.sort(twoCardHand, new SortByRank());
			highHand.removeFirst();
			highHand.addFirst(twoCardHand.getFirst());
		}

		Collections.sort(highHand, new SortByRank());

		handMap.put(HIGH_CARD, highHand);

	}

	/************** END OF 4 OF KIND, 3 OF KIND, FULL HOUSE, 2 PAIR, PAIR AND HIGH CARD FUNCTIONS **************/

}
}