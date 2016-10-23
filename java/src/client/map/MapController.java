package client.map;

import java.util.*;

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
		// Random rand = new Random();

		// for (int x = 0; x <= 3; ++x) {
			
		// 	int maxY = 3 - x;			
		// 	for (int y = -3; y <= maxY; ++y) {				
		// 		int r = rand.nextInt(HexType.values().length);
		// 		HexType hexType = HexType.values()[r];
		// 		HexLocation hexLoc = new HexLocation(x, y);
		// 		getView().addHex(hexLoc, hexType);
		// 		getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
		// 				CatanColor.RED);
		// 		getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
		// 				CatanColor.BLUE);
		// 		getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
		// 				CatanColor.ORANGE);
		// 		getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
		// 		getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
		// 	}
			
		// 	if (x != 0) {
		// 		int minY = x - 3;
		// 		for (int y = minY; y <= 3; ++y) {
		// 			int r = rand.nextInt(HexType.values().length);
		// 			HexType hexType = HexType.values()[r];
		// 			HexLocation hexLoc = new HexLocation(-x, y);
		// 			getView().addHex(hexLoc, hexType);
		// 			getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
		// 					CatanColor.RED);
		// 			getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
		// 					CatanColor.BLUE);
		// 			getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
		// 					CatanColor.ORANGE);
		// 			getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
		// 			getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
		// 		}
		// 	}
		// }
		
		// PortType portType = PortType.BRICK;
		// getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		// getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		// getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		// getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		// getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		// getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);
		
		// getView().placeRobber(new HexLocation(0, 0));
		
		// getView().addNumber(new HexLocation(-2, 0), 2);
		// getView().addNumber(new HexLocation(-2, 1), 3);
		// getView().addNumber(new HexLocation(-2, 2), 4);
		// getView().addNumber(new HexLocation(-1, 0), 5);
		// getView().addNumber(new HexLocation(-1, 1), 6);
		// getView().addNumber(new HexLocation(1, -1), 8);
		// getView().addNumber(new HexLocation(1, 0), 9);
		// getView().addNumber(new HexLocation(2, -2), 10);
		// getView().addNumber(new HexLocation(2, -1), 11);
		// getView().addNumber(new HexLocation(2, 0), 12);
		
		// //place water hexes if they are not received from server
		// getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		// getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		// getView().addHex(new HexLocation(-3, 1), HexType.WATER);
		// getView().addHex(new HexLocation(-3, 0), HexType.WATER);		
		// getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		// getView().addHex(new HexLocation(-2, 3), HexType.WATER);		
		// getView().addHex(new HexLocation(-1, -2), HexType.WATER);
		// getView().addHex(new HexLocation(-1, 3), HexType.WATER);		
		// getView().addHex(new HexLocation(0, 3), HexType.WATER);
		// getView().addHex(new HexLocation(0, -3), HexType.WATER);
		// getView().addHex(new HexLocation(1, -3), HexType.WATER);
		// getView().addHex(new HexLocation(1, 2), HexType.WATER);		
		// getView().addHex(new HexLocation(2, -3), HexType.WATER);
		// getView().addHex(new HexLocation(2, 1), HexType.WATER);		
		// getView().addHex(new HexLocation(3, -3), HexType.WATER);
		// getView().addHex(new HexLocation(3, -2), HexType.WATER);
		// getView().addHex(new HexLocation(3, -1), HexType.WATER);
		// getView().addHex(new HexLocation(3, 0), HexType.WATER);		
	}

	protected void initFromModel(boolean idk) {
		getView().resetMap();

		Game game = manager.getActiveGame();

		if (game == null){
			System.out.println("------------it is a null game-------");
			return;
		}

		List<Hex> hexes = game.getMap().getHexes();
		System.out.println("hexes size: " + hexes.size());
		for(Hex hex: hexes){
			//System.out.println("hex number: " +hex.getNumber());
			//System.out.println("hex robber: " +hex.hasRobber());
			getView().addHex(hex.getLocation(), hex.getHexType());
			if(hex.getNumber() !=0){
				getView().addNumber(hex.getLocation(), hex.getNumber());
			}
		}

		List<Port> ports = game.getMap().getPorts();
		//System.out.println("ports: " + ports.size());
		if(ports != null && ports.size() > 0)
			for(Port port: ports){
				EdgeLocation edge = new EdgeLocation(port.getLocation(), port.getDirection());
				getView().addPort(edge, port.getType());
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
		System.out.println("settlements: " + settlements.size());
		if(settlements != null)
			for(Settlement settlement: settlements){
				CatanColor color = players.get(settlement.getOwner().getIndex()).getColor();
				getView().placeSettlement(settlement.getLocation(), color);
			}

		List<City> cities = game.getMap().getCities();
		System.out.println("cities: " + cities.size());
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

		if(game.getMap().canBuildCity(manager.getActivePlayer(), convertVertexLocationToItemLocation(vertLoc)))
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
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		State state = game.getState();

		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {

		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		getView().startDrop(PieceType.ROAD, manager.getActivePlayer().getColor(), true);
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}

	public ItemLocation convertVertexLocationToItemLocation(VertexLocation vertLoc){
		ItemLocation itemLoc = new ItemLocation(vertLoc.getHexLoc(), vertLoc.getDir());
		return itemLoc;
	} 

    public void update(Observable o, Object arg){

    	this.game = manager.getActiveGame();

    	if (game != null){
    		System.out.println("received a game");
 			initFromModel(true);
    	}
    	else
    		System.out.println("received a null game");
  
    }

	
}

