package client.model;

/**
 * Contains a list of all the resources in Catan.
 */

public class ResourceList{
	int brick;
	int ore;
	int sheep;
	int wheat;
	int wood;
	
	public ResourceList(int brick, int ore, int sheep, int wheat, int wood){
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}
	
	public ResourceList(){
		brick = 0;
		ore = 0;
		sheep = 0;
		wheat = 0;
		wood = 0;
	}
	
	/**
	 * Determines if this ResourceList has all of the resources specified
	 * @param resource_list A ResourceList containing the resources we are checking for in this ResourceList.
	 * @pre Will function given any ResourceList
	 * @post Will return true if this ResourceList contains the resources specified.
	 */
	public boolean hasResources(ResourceList resource_list){
        return false;
        
    }

	public void addResources(ResourceList addList) {
		this.brick += addList.brick;
		this.ore += addList.ore;
		this.sheep += addList.sheep;
		this.wheat += addList.wheat;
		this.wood += addList.wood;
		
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

}
