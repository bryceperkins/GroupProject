package client.model;

/**
 * Contains a ResourceList whose values may not increase above 19.
 */

public class ResourceBank {
	
	private ResourceList resources;
	
	public ResourceBank(ResourceList resources){
		this.resources.setBrick(resources.getBrick());
		this.resources.setOre(resources.getOre());
		this.resources.setSheep(resources.getSheep());
		this.resources.setWood(resources.getWood());
		this.resources.setWheat(resources.getWheat());
	}
	
	public ResourceBank(){
		resources = new ResourceList(19,19,19,19,19);
	}
	
}
