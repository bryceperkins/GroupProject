package client.model.map;

public class HexLocation {
	private int x;
	private int y;
	
	public HexLocation(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		
		HexLocation compareTo = (HexLocation)obj;
		if(compareTo.x == this.x && compareTo.y == this.y)
			return true;
			
			return false;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
