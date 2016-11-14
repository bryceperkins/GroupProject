package shared.model.player;

import shared.definitions.*;

public class DevCardList {

	private int monopoly;
	private int monument;
	private int roadBuilding;
	private int soldier;
	private int yearOfPlenty;

	public int getMonopoly() {
		return monopoly;
	}

	public int getMonument() {
		return monument;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public int getSoldier() {
		return soldier;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	
	public void setMonopoly(int num)
	{
		this.monopoly = num;
	}

	public void setMonument(int num)
	{
		this.monument = num;
	}
	
	public void setRoadBuilding(int num)
	{
		this.roadBuilding = num;
	}
	
	public void setSoldier(int num)
	{
		this.soldier = num;
	}

	public void setYearOfPlenty(int num)
	{
		this.yearOfPlenty = num;
	}

	public void addCard(DevCardType card)
	{
		
		if(card == DevCardType.MONOPOLY)
			this.monopoly++;
		else if(card == DevCardType.MONUMENT)
			this.monument++;
		else if(card == DevCardType.ROAD_BUILD)
			this.roadBuilding++;
		else if(card == DevCardType.SOLDIER)
			this.soldier++;
		else if(card == DevCardType.YEAR_OF_PLENTY)
			this.yearOfPlenty++;
	}
	
	public void removeCard(DevCardType card)
	{
		if(card == DevCardType.MONOPOLY)
			this.monopoly--;
		else if(card == DevCardType.MONUMENT)
			this.monument--;
		else if(card == DevCardType.ROAD_BUILD)
			this.roadBuilding--;
		else if(card == DevCardType.SOLDIER)
			this.soldier--;
		else if(card == DevCardType.YEAR_OF_PLENTY)
			this.yearOfPlenty--;
	}
}
