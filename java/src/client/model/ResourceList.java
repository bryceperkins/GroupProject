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
	
	/**
	 * Determines if this ResourceList has all of the resources specified
	 * @param resource_list A ResourceList containing the resources we are checking for in this ResourceList.
	 * @pre Will function given any ResourceList
	 * @post Will return true if this ResourceList contains the resources specified.
	 */
	public boolean hasResources(ResourceList resource_list){}
}
