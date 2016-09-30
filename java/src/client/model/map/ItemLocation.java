package client.model.map;

import client.model.map.HexLocation;
import client.model.map.Direction;

public class ItemLocation {
	private HexLocation hexLocation;
	private Direction direction;

	public ItemLocation(HexLocation hexLocation, Direction direction){
		this.hexLocation = hexLocation;
		this.direction = direction;
	};

}


