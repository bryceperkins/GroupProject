package client.map;

import java.util.*;

import client.server.iCommand;
import shared.commands.BuildRoad;
import shared.commands.BuildSettlement;
import shared.commands.BuildCity;
import shared.commands.Soldier;
import shared.commands.moves.*;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import client.model.map.*;
import client.model.*;
import client.model.player.*;



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
				System.out.println("port Type: " +port.getType());
				EdgeLocation edge = new EdgeLocation(port.getLocation(), port.getDirection());
				getView().addPort(edge, PortType.fromResourceType(port.getType()));
			}

		List<Player> players = game.getPlayers();
		List<Road> roads = game.getMap().getRoads();
		if(roads != null){
			System.out.println("roads: " + roads.size());
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

		if(game.getMap().canBuildRoad(this.manager.getActivePlayer(),edgeLoc))
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
		this.manager.getPoller().setCommand((iCommand) new BuildRoad(this.manager.getActivePlayerIndex(), edgeLoc, game.getState().isFirstRound() ||game.getState().isSecondRound()));
		getView().placeRoad(edgeLoc, this.manager.getActivePlayer().getColor());
	}

	public void placeSettlement(VertexLocation vertLoc) {
		this.manager.getPoller().setCommand((iCommand) new BuildSettlement(this.manager.getActivePlayerIndex(), vertLoc, game.getState().isFirstRound() ||game.getState().isSecondRound()));
		getView().placeSettlement(vertLoc, this.manager.getActivePlayer().getColor());
	}

	public void placeCity(VertexLocation vertLoc) {
		this.manager.getPoller().setCommand((iCommand) new BuildCity(this.manager.getActivePlayerIndex(),vertLoc));
		getView().placeCity(vertLoc, this.manager.getActivePlayer().getColor());
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

    	if (this.game != null){
    		System.out.println("received a game");
 			initFromModel(true);
			if(this.game.getTurnTracker().getCurrentTurn() == this.manager.getActivePlayerIndex()){
				State state = game.getState();
				if(state.isFirstRound() || state.isSecondRound()){
					startMove(PieceType.SETTLEMENT, true, true);
				}else if (state.isRobbing()){
					startMove(PieceType.ROBBER, false, false);
				}
			}

    	}else
    		System.out.println("received a null game");
  
    }

	
}

