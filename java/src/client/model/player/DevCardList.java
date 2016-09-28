package client.model.player;

import java.util.HashMap;

public class DevCardList {

	private HashMap<DevCard, Integer> hand;
	
	DevCardList()
	{
		hand = new HashMap<DevCard,Integer>();
		hand.put(DevCard.monument, 0);
		hand.put(DevCard.monopoly, 0);
		hand.put(DevCard.roadBuilding, 0);
		hand.put(DevCard.soldier, 0);
		hand.put(DevCard.yearOfPlenty, 0);
		//monopoly, monument, roadBuilding, solder, yearOfPlenty
	}
	
	/**
	 * Subtracts an inputed amount of a single type of card in DevCardList
	 * amount must be larger than zero
	 * @param card inputs type of card to be incremented in DevCardList
	 * @param amount amount to be subtracted from number of DevCards in DevCardList
	 */
	public void subtractDevCard(DevCard card, int amount){
		hand.put(card, hand.get(card) - 1);
	}
	
	/**
	 * adds amount of a single type of card in DevCardList
	 * amount must be larger than zero
	 * @param card inputs type of card to be incremented in DevCardList
	 * @param amount amount to be add to number of DevCards in DevCardList
	 */
	public void addDevCard(DevCard card, int amount){
		hand.put(card, hand.get(card) + 1);
	}
	
	public int getCardNumber(DevCard card)
	{
		return hand.get(card);
	}

}
