package client.model;

/**
 * Contains a ResourceList whose values may not increase above 19.
 */

public class ResourceBank {
	
	ResourceList resources;
	
	public ResourceBank(){
		resources = new ResourceList(19,19,19,19,19);
	}
	
}
