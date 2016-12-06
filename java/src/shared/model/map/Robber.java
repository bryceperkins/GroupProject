package shared.model.map;

import java.io.Serializable;
import shared.locations.HexLocation;

public class Robber extends HexLocation implements Serializable {

    public Robber(int x, int y) {
        super.setX(x);
        super.setY(y);
    }

}

