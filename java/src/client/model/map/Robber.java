package client.model.map;

public class Robber {
	
	private HexLocation location = new HexLocation(0, 0);

	public Robber(HexLocation location) {
		super();
		this.location = location;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public int getX() {
		return location.getX();
	}

	public void setX(int x) {
		location.setX(x);
	}

	public int getY() {
		return getY();
	}

	public void setY(int y) {
		location.setY(y);
	}
}
