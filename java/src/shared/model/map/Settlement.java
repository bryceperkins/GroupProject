package shared.model.map;

import shared.model.PlayerIndex;
import shared.locations.VertexLocation;
import java.io.Serializable;

public class Settlement extends Piece implements Serializable{

	public Settlement(PlayerIndex index, VertexLocation location) {
		super(index, location);
	}


}
