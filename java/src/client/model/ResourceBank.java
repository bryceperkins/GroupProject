package client.model;

/**
 * Contains a ResourceList whose values may not increase above 19.
 */

public class ResourceBank {
	
	private ResourceList resources;
	
	public ResourceBank(ResourceList resources){
		this.resources = resources;
	}
	
	public ResourceBank(){
		resources = new ResourceList(19,19,19,19,19);
	}

	public int getBrick() {
		return resources.brick;
	}

	public void setBrick(int brick) {
        resources.brick = brick;
	}

	public int getOre() {
        return resources.ore;
	}

	public void setOre(int ore) {
        resources.ore = ore;
	}

	public int getSheep() {
        return resources.sheep;
	}

	public void setSheep(int sheep) {
        resources.sheep = sheep;
	}

	public int getWheat() {
        return resources.sheep;
	}

	public void setWheat(int wheat) {
        resources.wheat = wheat;
	}

	public int getWood() {
        return resources.wood;
	}

	public void setWood(int wood) {
        resources.wood = wood;
	}

}
