package client.model.map;

import shared.locations.HexLocation;
import client.model.Game;
import client.model.GameManager;
import client.model.PostProcessor;
import client.model.map.Robber;
import shared.definitions.*;

public class Hex implements PostProcessor {
	private HexLocation location;
	private boolean hasRobber;
	private int number;
	private HexType hexType;

	public Hex() {}

	public Hex(HexLocation location, boolean hasRobber, int value, HexType hexType){
		this.location = location;
		this.hasRobber = hasRobber;
		this.number = value;
		this.hexType = hexType;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public void setHasRobber(boolean hasRobber) {
		this.hasRobber = hasRobber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public HexType getHexType() {
		return hexType;
	}

	public void setHexType(HexType hexType) {
		this.hexType = hexType;
	}

	public boolean hasRobber(){ return hasRobber; }

	public boolean canPlaceRobber(){ return true; }

	@Override
	public void postDeserializationSetup(Game game) {
		HexLocation robberLocation = game.getMap().getRobber();
		hasRobber = location.equals(robberLocation);
	}
}
