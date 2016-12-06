package shared.model.map;

import java.io.Serializable;
import shared.model.PlayerIndex;
import shared.locations.VertexLocation;

public class City extends Piece implements Serializable {

	public City(PlayerIndex index, VertexLocation location) {
		super(index, location);
	}

}
