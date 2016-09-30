package client.model.map;

public class Robber {
	
	private HexLocation location;

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
	
	

}
