package shared.definitions;

/**
 * The Moves that can be performed by a user.
 */
public enum LogLevel
{
    ACCEPTTRADE, BUYDEVCARD, BUILDCITY, BUILDROAD, BUILDSETTLEMENT, DISCARDCARDS, FINISHTURN, MARITIMETRADE, MONOPOLY, MONUMENT, OFFERTRADE, ROADBUILDING, ROLLNUMBER, ROBPLAYER, SOLDIER, YEAROFPLENTY;
	
	private String type;
	
	static
	{
        ACCEPTTRADE.type = "acceptTrade";
        BUYDEVCARD.type = "buyDevCard";
        BUILDCITY.type = "buildCity";
        BUILDROAD.type = "buildRoad";
        BUILDSETTLEMENT.type = "buildSettlement";
        DISCARDCARDS.type = "dicardCards";
        FINISHTURN.type = "finishTurn";
        MARITIMETRADE.type = "maritimeTrade";
        MONOPOLY.type = "Monopoly";
        MONUMENT.type = "Monument";
        OFFERTRADE.type = "offerTrade";
        ROADBUILDING.type = "roadBuilding";
        ROLLNUMBER.type = "rollNumber";
        ROBPLAYER.type = "robPlayer";
        SOLDIER.type = "Soldier";
        YEAROFPLENTY.type = "YearOfPlenty";
	}
	
	public String getType(){
		return type;
	}
}
