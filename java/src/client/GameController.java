package client;

import client.devcards.*;
import client.map.*;

public class GameController
{

	public static final GameController INSTANCE = new GameController();

	private static DevCardController devCardCont;
	private static MapController mapCont;

	public GameController(){}
	
	public static void setDevCardController(DevCardController devCardCont)
	{
		devCardCont = devCardCont;
	}

	public static void setMapController(MapController mapCont)
	{
		mapCont = mapCont;
	}

	public DevCardController getDevCardController()
	{
		return devCardCont;
	}

	public MapController getMapController()
	{
		return mapCont;
	}

	public static GameController getInstance() 
	{
	        return INSTANCE;
	}
}
