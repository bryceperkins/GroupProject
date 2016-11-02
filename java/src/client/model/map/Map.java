package client.model.map;

import java.util.*;

import client.model.player.*;
import client.model.*;
import shared.locations.*;
import client.model.map.*;

public class Map implements PostProcessor {

    private List<Hex> hexes;
    private List<Port> ports;
	private List<Road> roads;
    private List<Settlement> settlements;
    private List<City> cities;
    private int radius;
    private Robber robber;
    
    

    public Map() {
    	
		super();
		this.hexes = new ArrayList<Hex>();
		this.ports = new ArrayList<Port>();
		this.settlements = new ArrayList<Settlement>();
		this.cities = new ArrayList<City>();
		this.radius = 3;
		this.robber = new Robber(3, 3);
        this.roads = new ArrayList<Road>();

	}

	public boolean canBuildRoad(Player player, EdgeLocation edgeLocation, State state){
        EdgeLocation normalizedEdgeLocation = edgeLocation.getNormalizedLocation(); //when do i ever not normalize before using an edge....

        for (Road road : roads) {
            if (road.getLocation().getNormalizedLocation().equals(normalizedEdgeLocation)) {
                return false;
            }
        }

        HexLocation neighborLoc = normalizedEdgeLocation.getHexLoc().getNeighborLoc(normalizedEdgeLocation.getDir());
        Hex hex = getHexByHexLocation((normalizedEdgeLocation.getHexLoc()));
        Hex neighborHex = getHexByHexLocation(neighborLoc);

        if((!hexes.contains(hex) && !isWaterEdge(normalizedEdgeLocation.getHexLoc()))
                || (!hexes.contains(neighborHex) && !isWaterEdge(neighborLoc))
                || (isWaterEdge(neighborLoc) && isWaterEdge(normalizedEdgeLocation.getHexLoc()))){
            return false;
        }

        if(state.isFirstRound()||state.isSecondRound()){
            //check possible city positions
            ItemLocation v1;
            ItemLocation v2;
            List<Road> possibleRoads = new ArrayList<Road>(roads);
            Road road = new Road(player.getPlayerIndex(),normalizedEdgeLocation);
            possibleRoads.add(road);

            switch(normalizedEdgeLocation.getDir()) {
                case NorthEast:
                    v1 = new ItemLocation(normalizedEdgeLocation.getHexLoc(), VertexDirection.NorthEast);
                    v2 = new ItemLocation(normalizedEdgeLocation.getHexLoc(), VertexDirection.East);
                    return (checkBuildSettlement(player, v1, state, possibleRoads) || checkBuildSettlement(player, v2, state, possibleRoads));
                case North:
                    v1 = new ItemLocation(normalizedEdgeLocation.getHexLoc(), VertexDirection.NorthEast);
                    v2 = new ItemLocation(normalizedEdgeLocation.getHexLoc(), VertexDirection.NorthWest);
                    return (checkBuildSettlement(player, v1, state, possibleRoads) || checkBuildSettlement(player, v2, state, possibleRoads));
                case NorthWest:
                    v1 = new ItemLocation(normalizedEdgeLocation.getHexLoc(), VertexDirection.NorthWest);
                    v2 = new ItemLocation(normalizedEdgeLocation.getHexLoc(), VertexDirection.West);
                    return (checkBuildSettlement(player, v1, state, possibleRoads) || checkBuildSettlement(player, v2, state, possibleRoads));
                default:
                    return false;
            }
        }else {
            switch (normalizedEdgeLocation.getDir()) {
                case North:
                    return checkNorthEdge(normalizedEdgeLocation, player);
                case NorthEast:
                    return checkNorthEastEdge(normalizedEdgeLocation, player);
                case NorthWest:
                    return checkNorthWestEdge(normalizedEdgeLocation, player);
                default:
                    return false;
            }
        }
    }

    public Hex getHexByHexLocation(HexLocation hexLoc){

        for (Hex hex: hexes){
            if ( hex.getLocation().equals(hexLoc))
                return hex;
        }
        return null;
    }

    public boolean canBuildSettlement(Player player, ItemLocation location, State state) {
        return checkBuildSettlement(player, location, state, roads);
    }

    public boolean checkBuildSettlement(Player player, ItemLocation itemLocation, State state, List<Road> roadsMod){
    	/*
    	 * does vertex have settlement or city on it
    	 * does the vertex right next to it have a city/settlement on it
    	 */
        VertexLocation normalizedVertLoc = itemLocation.toVertexLocation().getNormalizedLocation();

        for (Settlement settlement : settlements) {
            if (settlement.getLocation().getNormalizedLocation().equals(normalizedVertLoc)) {
                return false;
            }
        }
        for (City city : cities) {
            if (city.getLocation().getNormalizedLocation().equals(normalizedVertLoc)) {
                return false;
            }
        }

        EdgeDirection edgeDir;
        if (normalizedVertLoc.getDir() == VertexDirection.NorthEast) {
            edgeDir = EdgeDirection.NorthEast;
        }
        else {
            edgeDir = EdgeDirection.NorthWest;
        }

        HexLocation sideNeighborLoc = normalizedVertLoc.getHexLoc().getNeighborLoc(edgeDir);
        HexLocation northNeighborLoc = normalizedVertLoc.getHexLoc().getNeighborLoc(EdgeDirection.North);

        Hex northNeighborHex = getHexByHexLocation(northNeighborLoc);
        Hex sideNeighborHex = getHexByHexLocation(sideNeighborLoc);

        if ((!hexes.contains(northNeighborHex) && !isWaterEdge(northNeighborLoc))
                || (!hexes.contains(sideNeighborHex) && !isWaterEdge(sideNeighborLoc))
                || (isWaterEdge(normalizedVertLoc.getHexLoc())
                && isWaterEdge(northNeighborLoc)
                && isWaterEdge(sideNeighborLoc))) {
            return false;
        }
        switch(normalizedVertLoc.getDir()) {
            case NorthEast:
                return checkNorthEastVertex(normalizedVertLoc, player, roadsMod);
            case NorthWest:
                return checkNorthWestVertex(normalizedVertLoc, player, roadsMod);
            default:
                return false;
        }

    }

    public boolean isWaterEdge(HexLocation hexLoc){
        int x = hexLoc.getX();
        int y = hexLoc.getY();

        if(x == -3 && y == 3){return true;}
        if(x == -3 && y == 2){return true;}
        if(x == -3 && y == 1){return true;}
        if(x == -3 && y == 0){return true;}
        if(x == -2 && y == -1){return true;}
        if(x == -2 && y == 3){return true;}
        if(x == -1 && y == -2){return true;}
        if(x == -1 && y == 3){return true;}
        if(x == 0 && y == 3){return true;}
        if(x == 0 && y == -3){return true;}
        if(x == 1 && y == -3){return true;}
        if(x == 1 && y == 2){return true;}
        if(x == 2 && y == -3){return true;}
        if(x == 2 && y == 1){return true;}
        if(x == 3 && y == -3){return true;}
        if(x == 3 && y == -2){return true;}
        if(x == 3 && y == -1){return true;}
        if(x == 3 && y == 0){return true;}

        return false;
    }

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}

	public boolean canBuildCity(Player player, ItemLocation itemLocation){

    	for(int i = 0; i < settlements.size(); i++)
    	{
    		if(settlements.get(i).getLocation().equals(itemLocation.getLocation()) && 
    				settlements.get(i).getLocation().getDirection() == itemLocation.getDirection())
    		{
    			if(settlements.get(i).getOwner() == player.getPlayerIndex())
    				return true;
    			else
    				return false;
    		}
    	}
    	
    	return false;
    }

    public boolean canPlaceRobber(HexLocation hexLoc){
        Robber robber = getRobber();

        if(!hexLoc.equals(robber) && hexLoc != null)
            return true;
        else
            return false;
    }

    public void setHexes(List<Hex> hexes) {
        this.hexes = hexes;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRobber(Robber robber) {
        this.robber = robber;
    }

	public List<Hex> getHexes() {
		return hexes;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public List<Settlement> getSettlements() {
		return settlements;
	}

	public List<City> getCities() {
		return cities;
	}

	public int getRadius() {
		return radius;
	}

	public Robber getRobber() {
		return robber;
	}

	@Override
	public void postDeserializationSetup(Game game) {
		for (Hex h : hexes) {
			h.postDeserializationSetup(game);
		}
		for (City c : cities) {
			c.postDeserializationSetup(game);
		}
		for (Settlement s : settlements) {
			s.postDeserializationSetup(game);
		}
		for (Road r : roads) {
            r.postDeserializationSetup(game);
        }
	}

    private boolean checkNorthEastVertex(VertexLocation normVerLoc, Player player, List<Road> roadsMod) {
        VertexLocation vertex1 = new VertexLocation(normVerLoc.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), VertexDirection.NorthWest);
        VertexLocation vertex2 = new VertexLocation(normVerLoc.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast), VertexDirection.NorthWest);
        VertexLocation vertex3 = new VertexLocation(normVerLoc.getHexLoc(), VertexDirection.NorthWest);

        for (Settlement settlement : settlements) {

            if (settlement.getLocation().getNormalizedLocation().equals(vertex1) ||
                    settlement.getLocation().getNormalizedLocation().equals(vertex2) ||
                    settlement.getLocation().getNormalizedLocation().equals(vertex3)) {
                return false;
            }
        }
        for (City city : cities) {

            if (city.getLocation().getNormalizedLocation().equals(vertex1) ||
                    city.getLocation().getNormalizedLocation().equals(vertex2) ||
                    city.getLocation().getNormalizedLocation().equals(vertex3)) {
                return false;
            }
        }

        EdgeLocation edge1 = new EdgeLocation(normVerLoc.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest);
        EdgeLocation edge2= new EdgeLocation(normVerLoc.getHexLoc(), EdgeDirection.North);
        EdgeLocation edge3 = new EdgeLocation(normVerLoc.getHexLoc(), EdgeDirection.NorthEast);

        for (Road road : roadsMod) {
            if ((road.getLocation().getNormalizedLocation().equals(edge1) && player.getPlayerIndex() == road.getOwner()) ||
                    (road.getLocation().getNormalizedLocation().equals(edge2) && player.getPlayerIndex() == road.getOwner()) ||
                    (road.getLocation().getNormalizedLocation().equals(edge3) && player.getPlayerIndex() == road.getOwner())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNorthWestVertex(VertexLocation normVerLoc, Player player, List<Road> roadsMod) {
        VertexLocation vertex1 = new VertexLocation(normVerLoc.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), VertexDirection.NorthEast);
        VertexLocation vertex2 = new VertexLocation(normVerLoc.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest), VertexDirection.NorthEast);
        VertexLocation vertex3 = new VertexLocation(normVerLoc.getHexLoc(), VertexDirection.NorthEast);

        for (Settlement settlement : settlements) {

            if (settlement.getLocation().getNormalizedLocation().equals(vertex1) ||
                    settlement.getLocation().getNormalizedLocation().equals(vertex2) ||
                    settlement.getLocation().getNormalizedLocation().equals(vertex3)) {
                return false;
            }
        }

        for (City city : cities) {

            if (city.getLocation().getNormalizedLocation().equals(vertex1) ||
                    city.getLocation().getNormalizedLocation().equals(vertex2) ||
                    city.getLocation().getNormalizedLocation().equals(vertex3)) {
                return false;
            }
        }

        EdgeLocation edge1 = new EdgeLocation(normVerLoc.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast);
        EdgeLocation edge2= new EdgeLocation(normVerLoc.getHexLoc(), EdgeDirection.North);
        EdgeLocation edge3 = new EdgeLocation(normVerLoc.getHexLoc(), EdgeDirection.NorthWest);

        for (Road road : roadsMod) {
            if ((road.getLocation().getNormalizedLocation().equals(edge1) && player.getPlayerIndex() == road.getOwner()) ||
                    (road.getLocation().getNormalizedLocation().equals(edge2) && player.getPlayerIndex() == road.getOwner()) ||
                    (road.getLocation().getNormalizedLocation().equals(edge3) && player.getPlayerIndex() == road.getOwner())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNorthEastEdge(EdgeLocation normEdgeLocation, Player player) {
        HexLocation thisHexLoc = normEdgeLocation.getHexLoc();
        HexLocation NEHexLoc = normEdgeLocation.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
        HexLocation SEHexLoc = normEdgeLocation.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast);

        for (Road road : roads) {
            if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(thisHexLoc, EdgeDirection.North))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(NEHexLoc, EdgeDirection.NorthWest))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(SEHexLoc, EdgeDirection.North))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(SEHexLoc, EdgeDirection.NorthWest))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNorthEdge(EdgeLocation normEdgeLocation, Player player) {
        HexLocation thisHexLoc = normEdgeLocation.getHexLoc();
        HexLocation NEHexLoc = normEdgeLocation.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
        HexLocation NWHexLoc = normEdgeLocation.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);

        for (Road road : roads) {
            if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(thisHexLoc, EdgeDirection.NorthEast))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(thisHexLoc, EdgeDirection.NorthWest))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(NEHexLoc, EdgeDirection.NorthWest))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(NWHexLoc, EdgeDirection.NorthEast))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNorthWestEdge(EdgeLocation normEdgeLocation, Player player) {
        HexLocation thisHexLoc = normEdgeLocation.getHexLoc();
        HexLocation NWHexLoc = normEdgeLocation.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);
        HexLocation SWHexLoc = normEdgeLocation.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest);

        for (Road road : roads) {
            if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(thisHexLoc, EdgeDirection.North))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(NWHexLoc, EdgeDirection.NorthEast))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(SWHexLoc, EdgeDirection.North))) {
                return true;
            }
            else if (road.getOwner() == player.getPlayerIndex() && road.getLocation().getNormalizedLocation().equals(new EdgeLocation(SWHexLoc, EdgeDirection.NorthEast))) {
                return true;
            }
        }
        return false;
    }
}
