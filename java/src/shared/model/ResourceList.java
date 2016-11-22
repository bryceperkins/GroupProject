package shared.model;

import shared.definitions.*;

import javax.annotation.Resource;

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
        if (this.brick < resource_list.getBrick() ||
		this.ore < resource_list.getOre() ||
		this.sheep < resource_list.getSheep() ||
		this.wheat < resource_list.getWheat() ||
		this.wood < resource_list.getWood()){
			return false;
		}
		return true;
    }
	
	public ResourceList reversedList(){
		ResourceList reversed_resources = new ResourceList(this.brick*-1, this.ore*-1,this.sheep*-1,this.wheat*-1,this.wood*-1);
		return reversed_resources;
	}

    public boolean hasResource(ResourceType type, int count) {
		switch (type) {
			case BRICK: return brick >= count;
			case ORE: return ore >= count;
			case SHEEP: return sheep >= count;
			case WHEAT: return wheat >= count;
			case WOOD: return wood >= count;
			default: return false;
		}
	}
	
	public boolean hasOneResource(ResourceList resource_list){
        if (this.brick < resource_list.getBrick() &&
		this.ore < resource_list.getOre() &&
		this.sheep < resource_list.getSheep() &&
		this.wheat < resource_list.getWheat() &&
		this.wood < resource_list.getWood()){
			return false;
		}
		return true;
    }

	public void addResources(ResourceList addList) {
		this.brick += addList.getBrick();
		this.ore += addList.getOre();
		this.sheep += addList.getSheep();
		this.wheat += addList.getWheat();
		this.wood += addList.getWood();
		
	}

	public void decreaseBy(ResourceType resource, int amount) {
		switch (resource) {
			case BRICK:
				brick -= amount;
				break;
			case ORE:
				ore -= amount;
				break;
			case SHEEP:
				sheep -= amount;
				break;
			case WHEAT:
				wheat -= amount;
				break;
			case WOOD:
				wood -= amount;
				break;
		}
	}

	public void increaseBy(ResourceType resource, int amount) {
		decreaseBy(resource, amount * -1);
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public void increaseBrick() {
		this.brick++;
	}

	public void decreaseBrick() {
		this.brick--;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public void increaseOre() {
		this.ore++;
	}

	public void decreaseOre() {
		this.ore--;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public void increaseSheep() {
		this.sheep++;
	}

	public void decreaseSheep() {
		this.sheep--;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public void increaseWheat() {
		this.wheat++;
	}

	public void decreaseWheat() {
		this.wheat--;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public void increaseWood() {
		this.wood++;
	}

	public void decreaseWood() {
		this.wood--;
	}

    public int count(ResourceType resource){
        int amount =0 ;
		switch (resource)
		{
		case WOOD:
            amount = getWood();
			break;
		case SHEEP:
			amount = getSheep();
			break;
		case BRICK:
			amount = getBrick();
			break;
		case ORE:
			amount = getOre();
			break;
		case WHEAT:
			amount = getWheat();
			break;
		}
        return amount;
    }

	public void removeResources(ResourceList removeList) {
		this.brick -= removeList.getBrick();
		this.ore -= removeList.getOre();
		this.sheep -= removeList.getSheep();
		this.wheat -= removeList.getWheat();
		this.wood -= removeList.getWood();
		
	}

 public void setResourceByType(ResourceType resource, int value){

	switch (resource)
		{
		case WOOD:
            		this.wood = value;
			break;
		case SHEEP:
			this.sheep = value;
			break;
		case BRICK:
			this.brick = value;
			break;
		case ORE:
			this.ore = value;
			break;
		case WHEAT:
			this.wheat = value;
			break;
		}

	}


    public int getResourceByType(ResourceType resource){

	switch (resource)
		{
		case WOOD:
            		return this.wood;
		case SHEEP:
			return this.sheep;
		case BRICK:
			return this.brick;
		case ORE:
			return this.ore;
		case WHEAT:
			return this.wheat;
		}

		return 0;
	}

    public void increase(ResourceType resource){
		switch (resource)
		{
		case WOOD:
            increaseWood();
			break;
		case SHEEP:
			increaseSheep();
			break;
		case BRICK:
			increaseBrick();
			break;
		case ORE:
			increaseOre();
			break;
		case WHEAT:
			increaseWheat();
			break;
		}
    }

	public void decrease(ResourceType resource) {
		switch (resource)
		{
		case WOOD:
			decreaseWood();
			break;
		case SHEEP:
			decreaseSheep();
			break;
		case BRICK:
			decreaseBrick();
			break;
		case ORE:
			decreaseOre();
			break;
		case WHEAT:
			decreaseWheat();
			break;
		}
        System.out.println("Decrease");
		
	}

	public void setThree(){
		this.brick =3;
		this.ore =3;
		this.sheep =3;
		this.wheat =3;
		this.wood =3;
	}
    public int total(){
		return this.brick + this.ore + this.sheep + this.wheat + this.wood;

    }
}
