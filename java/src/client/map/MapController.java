package client.map;

import java.util.*;

import client.server.iCommand;
import shared.commands.*;
import shared.commands.BuildCity;
import shared.commands.BuildRoad;
import shared.commands.BuildSettlement;
import shared.commands.Soldier;
import shared.commands.moves.*;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import client.model.map.*;
import client.model.*;
import client.model.player.*;

import static com.sun.tools.javac.util.Assert.error;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController,Observer {
	
	private IRobView robView;
	private GameManager manager = GameManager.getInstance();
	private Game game;

	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
		initFromModel();
		manager.addObserver(this);
		
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void initFromModel() {

	}

	protected void initFromModel(boolean idk) {
		getView().resetMap();

		// //place water hexes if they are not received from server
		 getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		 getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		 getView().addHex(new HexLocation(-3, 1), HexType.WATER);
		 getView().addHex(new HexLocation(-3, 0), HexType.WATER);
		 getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		 getView().addHex(new HexLocation(-2, 3), HexType.WATER);
		 getView().addHex(new HexLocation(-1, -2), HexType.WATER);
		 getView().addHex(new HexLocation(-1, 3), HexType.WATER);
		 getView().addHex(new HexLocation(0, 3), HexType.WATER);
		 getView().addHex(new HexLocation(0, -3), HexType.WATER);
		 getView().addHex(new HexLocation(1, -3), HexType.WATER);
		 getView().addHex(new HexLocation(1, 2), HexType.WATER);
		 getView().addHex(new HexLocation(2, -3), HexType.WATER);
		 getView().addHex(new HexLocation(2, 1), HexType.WATER);
		 getView().addHex(new HexLocation(3, -3), HexType.WATER);
		 getView().addHex(new HexLocation(3, -2), HexType.WATER);
		 getView().addHex(new HexLocation(3, -1), HexType.WATER);
		 getView().addHex(new HexLocation(3, 0), HexType.WATER);

		Game game = this.manager.getActiveGame();

		if (game == null){
			System.out.println("------------it is a null game-------");
			return;
		}

		List<Hex> hexes = game.getMap().getHexes();
		System.out.println("hexes size: " + hexes.size());
		for(Hex hex: hexes){
			getView().addHex(hex.getLocation(), HexType.fromResourceType(hex.getResource()));
			if(hex.getNumber() !=0){
				getView().addNumber(hex.getLocation(), hex.getNumber());
			}
		}

		List<Port> ports = game.getMap().getPorts();
		if(ports != null && ports.size() > 0)
			for(Port port: ports){
				EdgeLocation edge = new EdgeLocation(port.getLocation(), port.getDirection());
				getView().addPort(edge, PortType.fromResourceType(port.getResource()));
			}

		List<Player> players = game.getPlayers();
		List<Road> roads = game.getMap().getRoads();
		if(roads != null){
			for(Road road: roads){
				CatanColor color = players.get(road.getOwner().getIndex()).getColor();
				getView().placeRoad(road.getLocation(), color);
			}
		}

		List<Settlement> settlements = game.getMap().getSettlements();
		if(settlements != null)
			for(Settlement settlement: settlements){
				CatanColor color = players.get(settlement.getOwner().getIndex()).getColor();
				getView().placeSettlement(settlement.getLocation(), color);
			}

		List<City> cities = game.getMap().getCities();
		if(cities != null)
			for(City city: cities){
				CatanColor color = players.get(city.getOwner().getIndex()).getColor();
				getView().placeCity(city.getLocation(), color);
			}

		if(game.getMap().getRobber() != null)
			getView().placeRobber(game.getMap().getRobber());
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		State state = game.getState();
		if(game.getMap().canBuildRoad(this.manager.getActivePlayer(),edgeLoc, state.isFirstRound() || state.isSecondRound()))
			return true;
		else
			return false;			
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {

		if(game.getMap().canBuildSettlement(this.manager.getActivePlayer(), convertVertexLocationToItemLocation(vertLoc)))
			return true;
		else
			return false;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {

		if(game.getMap().canBuildCity(this.manager.getActivePlayer(), convertVertexLocationToItemLocation(vertLoc)))
			return true;
		else
			return false;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {

		if(game.getMap().canPlaceRobber(hexLoc)){
			return true;
		}else
			return false;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		if(canPlaceRoad(edgeLoc)){
			System.out.println("yes we can place a road here....");
			String result = manager.getServer().execute(new BuildRoad(this.manager.getActivePlayerIndex(), edgeLoc, game.getState().isFirstRound() ||game.getState().isSecondRound()));
			if (!result.equals("Failed")) {
				getView().placeRoad(edgeLoc, this.manager.getActivePlayer().getColor());
			} else {
				System.out.println("some error");
				//error("failed to join game");
			}
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		if(canPlaceSettlement(vertLoc)){
			System.out.println("yes we can place a settlement here....");
			String result = manager.getServer().execute( new BuildSettlement(this.manager.getActivePlayerIndex(), vertLoc, game.getState().isFirstRound() ||game.getState().isSecondRound()));
			if (!result.equals("Failed")) {
				getView().placeSettlement(vertLoc, this.manager.getActivePlayer().getColor());
			} else {
				System.out.println("some error");
				//error("failed to join game");
			}
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		String result = manager.getServer().execute(new BuildCity(this.manager.getActivePlayerIndex(),vertLoc));
		if (!result.equals("Failed")) {
			getView().placeCity(vertLoc, this.manager.getActivePlayer().getColor());
		} else {
			System.out.println("some error");
			//error("failed to join game");
		}
		//this.manager.getPoller().setCommand((iCommand) new BuildCity(this.manager.getActivePlayerIndex(),vertLoc));
		//getView().placeCity(vertLoc, this.manager.getActivePlayer().getColor());
	}

	public void placeRobber(HexLocation hexLoc) {

		getView().placeRobber(hexLoc);
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, this.manager.getActivePlayer().getColor(), true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {
		//should we send this command with parameters or just send command
		//without parameters to remove card and initiate a robbing.
		//this.manager.getPoller().setCommand((iCommand) new Soldier());
		getView().startDrop(PieceType.ROBBER, this.manager.getActivePlayer().getColor(),false);
	}
	
	public void playRoadBuildingCard() {	
		getView().startDrop(PieceType.ROAD, this.manager.getActivePlayer().getColor(), true);
		getView().startDrop(PieceType.ROAD, this.manager.getActivePlayer().getColor(), true);
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}

	public ItemLocation convertVertexLocationToItemLocation(VertexLocation vertLoc){
		ItemLocation itemLoc = new ItemLocation(vertLoc.getHexLoc(), vertLoc.getDir());
		return itemLoc;
	} 

    public void update(Observable o, Object arg){

    	this.game = this.manager.getActiveGame();
		Player player = manager.getActivePlayer();
    	if (this.game != null){
    		System.out.println("received a game");
 			initFromModel(true);
			System.out.println(this.game.getTurnTracker().getStatus());
			if(this.game.getTurnTracker().getCurrentTurn() == this.manager.getActivePlayerIndex()){
				State state = game.getState();
				if(state.isFirstRound() || state.isSecondRound()){
					if (player.getRoadsRemaining() == 15) {
						getView().startDrop(PieceType.ROAD, manager.getActivePlayer().getColor(), false);
					}
					else if (player.getRoadsRemaining() == 14 && player.getSettlementsRemaining() == 5) {
						getView().startDrop(PieceType.SETTLEMENT, manager.getActivePlayer().getColor(), false);
					}
					else if (player.getRoadsRemaining() == 14 && player.getSettlementsRemaining() == 4 && state.isFirstRound()) {
						String response = manager.getServer().execute(new shared.commands.FinishTurn(manager.getActivePlayerIndex()));

						if (response.equals("Failed")) {
							System.out.println("Could not finish turn");
						} else {
							//getView().updateGameState("Waiting for other Players", false);
						}
					}
					else if (player.getRoadsRemaining() == 14 && player.getSettlementsRemaining() == 4 && state.isSecondRound()) {
						getView().startDrop(PieceType.ROAD, manager.getActivePlayer().getColor(), false);
					}
					else if (player.getRoadsRemaining() == 13 && player.getSettlementsRemaining() == 4 && state.isSecondRound()) {
						getView().startDrop(PieceType.SETTLEMENT, manager.getActivePlayer().getColor(), false);
					}
					else if (player.getRoadsRemaining() == 13 && player.getSettlementsRemaining() == 3 && state.isSecondRound()) {
						String response = manager.getServer().execute(new shared.commands.FinishTurn(manager.getActivePlayerIndex()));

						if (response.equals("Failed")) {
							System.out.println("Could not finish turn");
						} else {
							//getView().updateGameState("Waiting for other Players", false);
						}
					}
					//end turn
				}else if (state.isRobbing()){
					startMove(PieceType.ROBBER, false, false);
					//end turn
				}
			}

    	}else
    		System.out.println("received a null game");
  
    }

	
}

