package client.devcards;

import client.base.*;
import client.map.*;
import client.*;

public class RoadBuildingAction implements IAction{
	
	public void execute()
	{
		MapController mc = GameController.getInstance().getMapController();
		mc.playRoadBuildingCard();
	}
	
	
}
