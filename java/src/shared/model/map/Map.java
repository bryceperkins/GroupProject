package shared.model.map;

import java.util.*;

import shared.model.player.*;
import shared.model.*;
import shared.locations.*;
import shared.definitions.ResourceType;

public class Map implements PostProcessor {

    private List<Hex> hexes;
    private List<Port> ports;
	private List<Road> roads;
    private List<Settlement> settlements;
    private List<City> cities;
    private int radius;
    private Robber robber;

	//Components of Catan, used for randomization
	private final HexLocation[] portLocations = {
		new HexLocation(-3,  0),
		new HexLocation(-1, -2),
		new HexLocation( 1, -3),
		new HexLocation( 3, -3),
		new HexLocation( 3, -1),
		new HexLocation( 2,  1),
		new HexLocation( 0,  3),
		new HexLocation(-2,  3),
		new HexLocation(-3,  2),
	};

	private final EdgeDirection[] portDirections = {
		EdgeDirection.SouthEast,
		EdgeDirection.South,
		EdgeDirection.South,
		EdgeDirection.SouthWest,
		EdgeDirection.NorthWest,
		EdgeDirection.NorthWest,
		EdgeDirection.North,
		EdgeDirection.NorthEast,
		EdgeDirection.NorthEast,
	};
	
	private final ResourceType[] portTypes = {
		null,
		ResourceType.WHEAT,
		ResourceType.ORE,
		null,
		ResourceType.SHEEP,
		null,
		null,
		ResourceType.BRICK,
		ResourceType.WOOD,
	};

	private final HexLocation[] landLocations = {
		new HexLocation(-2,  0),
		new HexLocation(-2,  1),
		new HexLocation(-2,  2),
		new HexLocation(-1, -1),
		new HexLocation(-1,  0),
		new HexLocation(-1,  1),
		new HexLocation(-1,  2),
		new HexLocation( 0, -2),
		new HexLocation( 0, -1),
		new HexLocation( 0,  0),
		new HexLocation( 0,  1),
		new HexLocation( 0,  2),
		new HexLocation( 1, -2),
		new HexLocation( 1, -1),
		new HexLocation( 1,  0),
		new HexLocation( 1,  1),
		new HexLocation( 2, -2),
		new HexLocation( 2, -1),
		new HexLocation( 2,  0),
	};
	
	private final ResourceType[] hexTypes = {
		ResourceType.ORE,
		ResourceType.WHEAT,
		ResourceType.WOOD,

		ResourceType.BRICK,
		ResourceType.SHEEP,
		ResourceType.SHEEP,
		ResourceType.ORE,

		null,
		ResourceType.WOOD,
		ResourceType.WHEAT,
		ResourceType.WOOD,
		ResourceType.WHEAT,

		ResourceType.BRICK,
		ResourceType.ORE,
		ResourceType.BRICK,
		ResourceType.SHEEP,

		ResourceType.WOOD,
		ResourceType.SHEEP,
		ResourceType.WHEAT,
	};
	
	private final int[] hexNumbers = {
		5, 2, 6,
		8, 10, 9, 3,
		/*0,*/ 3, 11, 4, 8,
		4, 9, 5, 10,
		11, 12, 6
	};
	
	public Map(boolean randomTiles, boolean randomPorts, boolean randomNumbers){
		//TODO: Create a map with these attributes.
		super();
		this.hexes = new ArrayList<Hex>();
		this.ports = new ArrayList<Port>();
		this.settlements = new ArrayList<Settlement>();
		this.cities = new ArrayList<City>();
		this.radius = 3;
		this.robber = new Robber(3, 3);
        this.roads = new ArrayList<Road>();
		
		List<Integer> numbers = new ArrayList<>(this.hexNumbers.length);
		for (int i : this.hexNumbers) {
			numbers.add(i);
		}
		List<ResourceType> hexTypes = Arrays.asList(this.hexTypes);
		List<ResourceType> portTypes = Arrays.asList(this.portTypes);

		if (randomNumbers) {
			Collections.shuffle(numbers);
		}

		if (randomPorts) {
			Collections.shuffle(portTypes);
		}

		if (randomTiles) {
			Collections.shuffle(hexTypes);
		}
		
		//land tiles
		int desert_offset = 0;
		for (int i = 0; i < landLocations.length; i++) {
			Hex tempHex = new Hex();
            tempHex.setLocation(landLocations[i]);
            tempHex.setResource(hexTypes.get(i));
			tempHex.setHasRobber(false);
            if (hexTypes.get(i) == null) {
				tempHex.setHasRobber(true);
                this.robber = new Robber(landLocations[i].getX(), landLocations[i].getY());
                desert_offset = -1;
            } else {
                tempHex.setNumber(numbers.get(i + desert_offset));
            }
            this.hexes.add(tempHex);
        }
		
		//ports
		for (int i = 0; i < portLocations.length; i++) {
			Port tempPort = new Port();
			tempPort.setResource(portTypes.get(i));
			tempPort.setDirection(portDirections[i]);
			tempPort.setLocation(portLocations[i]);
			int ratio = 4;
			if (portTypes.get(i) == null){
				ratio = 3;
			} else {
				ratio = 2;
			}
			tempPort.setRatio(ratio);
			this.ports.add(tempPort);
		}
		
	}
	
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

    public List<Piece> getCitiesAndSettlements() {
        List<Piece> result = new ArrayList<>();
        result.addAll(cities);
        result.addAll(settlements);
        return result;
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

    public void distributeResources(int rollNumber) {

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

        for(Settlement settlement: settlements)
        {
            if(settlement.getLocation().getNormalizedLocation().equals(itemLocation.toVertexLocation().getNormalizedLocation()) &&
                    settlement.getLocation().getNormalizedLocation().getDirection() == itemLocation.toVertexLocation().getNormalizedLocation().getDirection())
            {
                if(settlement.getOwner() == player.getPlayerIndex())
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

    public List<Port> getPortsForTrade(Player player){
        List<Port> tradePorts = new ArrayList<Port>();
        for(Port port: ports){

            ArrayList<VertexDirection> vertLocs = getVertexDirections(port.getDirection());
            VertexLocation vertLoc1 = new VertexLocation(port.getLocation(), vertLocs.get(0));
            VertexLocation vertLoc2 = new VertexLocation(port.getLocation(), vertLocs.get(1));
            //Normalize locations
            VertexLocation vertLoc1Norm = vertLoc1.getNormalizedLocation();
            VertexLocation vertLoc2Norm = vertLoc2.getNormalizedLocation();

            for(City city: cities){
                if((city.getLocation().getNormalizedLocation().equals(vertLoc1Norm) || city.getLocation().getNormalizedLocation().equals(vertLoc2Norm))
                        && city.getOwner() == player.getPlayerIndex()){
                    tradePorts.add(port);
                    player.addPort(port);
                }
            }

            for(Settlement settlement: settlements){
                if((settlement.getLocation().getNormalizedLocation().equals(vertLoc1Norm) || settlement.getLocation().getNormalizedLocation().equals(vertLoc2Norm))
                        && settlement.getOwner() == player.getPlayerIndex()){
					System.out.println("Ratio: " + port.getRatio());
                    tradePorts.add(port);
                    player.addPort(port);
                }
            }
        }
        for(Port port1: tradePorts)
            System.out.println(port1.getResource());

        return tradePorts;
    }

    public ArrayList<VertexDirection> getVertexDirections(EdgeDirection edgeDir){
        ArrayList<VertexDirection> vertDirs = new ArrayList<VertexDirection>();
        switch(edgeDir){
            case NorthWest:
                vertDirs.add(VertexDirection.NorthWest);
                vertDirs.add(VertexDirection.West);
                break;
            case North:
                vertDirs.add(VertexDirection.NorthWest);
                vertDirs.add(VertexDirection.NorthEast);
                break;
            case NorthEast:
                vertDirs.add(VertexDirection.NorthEast);
                vertDirs.add(VertexDirection.East);
                break;
            case SouthEast:
                vertDirs.add(VertexDirection.East);
                vertDirs.add(VertexDirection.SouthEast);
                break;
            case South:
                vertDirs.add(VertexDirection.SouthEast);
                vertDirs.add(VertexDirection.SouthWest);
                break;
            case SouthWest:
                vertDirs.add(VertexDirection.SouthWest);
                vertDirs.add(VertexDirection.West);
                break;
            default:
                return null;

        }
        return vertDirs;
    }
}
