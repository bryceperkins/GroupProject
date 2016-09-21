package shared.definitions;

/**
 * The Moves that can be performed by a user.
 */
public enum MoveType
{
    AcceptTrade, BuyDevCard, BuildCity, BuildRoad, BuildSettlement, DiscardCards, FinishTurn, MaritimeTrade, Monopoly, Monument, OfferTrade, RoadBuilding, RollNumber, RobPlayer, Soldier, YearOfPlenty;
	
	private String type;
	
	static
	{
        AcceptTrade.type = "acceptTrade";
        BuyDevCard.type = "buyDevCard";
        BuildCity.type = "buildCity";
        BuildRoad.type = "buildRoad";
        BuildSettlement.type = "buildSettlement";
        DiscardCards.type = "dicardCards";
        FinishTurn.type = "finishTurn";
        MaritimeTrade.type = "maritimeTrade";
        Monopoly.type = "Monopoly";
        Monument.type = "Monument";
        OfferTrade.type = "offerTrade";
        RoadBuilding.type = "roadBuilding";
        RollNumber.type = "rollNumber";
        RobPlayer.type = "robPlayer";
        Soldier.type = "Soldier";
        YearOfPlenty.type = "YearOfPlenty";
	}
	
	public String getType(){
		return type;
	}
}
