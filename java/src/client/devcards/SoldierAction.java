package client.devcards;

import client.base.*;
import client.map.*;
import client.*;

public class SoldierAction implements IAction{
	

	public void execute()
	{
		MapController mc = GameController.getInstance().getMapController();
		mc.playSoldierCard();
	}
	
	
}
