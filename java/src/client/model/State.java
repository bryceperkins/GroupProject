package client.model;

import client.model.Game;
import client.model.PostProcessor;
import shared.locations.*;

public class State {
	
	private GameManager manager = GameManager.getInstance();

	private boolean canBuildCity;
	private boolean canBuildSettlement;
	private boolean canBuildRoad;

	private boolean isRoadFree;
	private boolean isSettlementFree;

	private boolean canBuyDevCard;
	private boolean canTrade;

	private boolean firstRound;
	private boolean secondRound;
	private boolean robbing;

	public State(TurnTracker.GameStatus gameStatus) {
		updateState(gameStatus);

	}

	public boolean canBuildCity() {
		return canBuildCity;
	}

	public void setCanBuildCity(boolean canBuildCity) {
		this.canBuildCity = canBuildCity;
	}

	public boolean canBuildSettlement() {
		return canBuildSettlement;
	}

	public void setCanBuildSettlement(boolean canBuildSettlement) {
		this.canBuildSettlement = canBuildSettlement;
	}

	public boolean canBuildRoad() {
		return canBuildRoad;
	}

	public void setCanBuildRoad(boolean canBuildRoad) {
		this.canBuildRoad = canBuildRoad;
	}

	public boolean isRoadFree() {
		return isRoadFree;
	}

	public void setRoadFree(boolean roadFree) {
		isRoadFree = roadFree;
	}

	public boolean isSettlementFree() {
		return isSettlementFree;
	}

	public void setSettlementFree(boolean settlementFree) {
		isSettlementFree = settlementFree;
	}

	public boolean canBuyDevCard() {
		return canBuyDevCard;
	}

	public void setCanBuyDevCard(boolean canBuyDevCard) {
		this.canBuyDevCard = canBuyDevCard;
	}

	public boolean canTrade() {
		return canTrade;
	}

	public void setCanTrade(boolean canTrade) {
		this.canTrade = canTrade;
	}

	public boolean isFirstRound() {
		return firstRound;
	}

	public void setFirstRound(boolean firstRound) {
		this.firstRound = firstRound;
	}

	public boolean isSecondRound() {
		return secondRound;
	}

	public void setSecondRound(boolean secondRound) {
		this.secondRound = secondRound;
	}

	public boolean isRobbing() {
		return robbing;
	}

	public void setRobbing(boolean robbing) {
		this.robbing = robbing;
	}

	public void allFalse(){
		this.canBuildCity = false;
		this.canBuildRoad = false;
		this.canBuildSettlement = false;

		this.isRoadFree = false;
		this.isSettlementFree = false;

		this.canTrade = false;
		this.canBuyDevCard = false;

		this.firstRound = false;
		this.secondRound = false;
		this.robbing = false;
	}

	public void updateState(TurnTracker.GameStatus gameStatus){
		switch(gameStatus){
			case Rolling:
				allFalse();
				break;
			case Robbing:
				this.canBuildCity = false;
				this.canBuildRoad = false;
				this.canBuildSettlement = false;

				this.isRoadFree = false;
				this.isSettlementFree = false;

				this.canTrade = false;
				this.canBuyDevCard = false;

				this.firstRound = false;
				this.secondRound = false;
				this.robbing = true;
				break;
			case Playing:
				this.canBuildCity = true;
				this.canBuildRoad = true;
				this.canBuildSettlement = true;

				this.isRoadFree = false;
				this.isSettlementFree = false;

				this.canTrade = true;
				this.canBuyDevCard = true;

				this.firstRound = false;
				this.secondRound = false;
				this.robbing = false;

				break;
			case Discarding:
				allFalse();

				break;
			case FirstRound:
				this.canBuildCity = false;
				this.canBuildRoad = false;
				this.canBuildSettlement = false;

				this.isRoadFree = true;
				this.isSettlementFree = true;

				this.canTrade = false;
				this.canBuyDevCard = false;

				this.firstRound = true;
				this.secondRound = false;
				this.robbing = false;
				break;
			case SecondRound:
				this.canBuildCity = false;
				this.canBuildRoad = false;
				this.canBuildSettlement = false;

				this.isRoadFree = true;
				this.isSettlementFree = true;

				this.canTrade = false;
				this.canBuyDevCard = false;

				this.firstRound = false;
				this.secondRound = true;
				this.robbing = false;
				break;
			default:
				allFalse();
				break;
			
		}
	}
}
