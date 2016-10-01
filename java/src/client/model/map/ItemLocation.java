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

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public HexLocation getHexLocation() {

		return hexLocation;
	}

	public void setHexLocation(HexLocation hexLocation) {
		this.hexLocation = hexLocation;
	}
}


