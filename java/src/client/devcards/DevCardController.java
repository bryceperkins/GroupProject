package client.devcards;

import shared.definitions.ResourceType;
import client.base.*;
import client.model.*;
import client.model.player.*;
import shared.definitions.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		if(ModelProxy.canBuyDevCard()){//can buy dev card
			getBuyCardView().showModal();
		}else {
			//cannot buy card, no nothing...
		}
		
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {

		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		//check if player has played card this turn, it the players turn
		//and current state

		Player player = GameManager.getInstance().getActivePlayer();
		DevCardList oldDevCards = player.getOldDevCards();
		DevCardList newDevCards = player.getNewDevCards();

		//set numbers
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, oldDevCards.getMonopoly() + newDevCards.getMonopoly());
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, oldDevCards.getMonument() + newDevCards.getMonument());
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, oldDevCards.getRoadBuilding() + newDevCards.getRoadBuilding());
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, oldDevCards.getSoldier() + newDevCards.getSoldier());
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, oldDevCards.getYearOfPlenty() + newDevCards.getYearOfPlenty());

		if (ModelProxy.isPlayerTurn()){ //hasn't played a dev card and 
			//set enabled
			if(oldDevCards.getMonopoly() < 1){
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
			}

			//Monument can be played as many times as they have cards
			
			if(oldDevCards.getRoadBuilding() < 1){
				getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
			}
			
			if(oldDevCards.getSoldier() < 1){
				getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
			}
			
			if(oldDevCards.getYearOfPlenty() < 1){
				getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
			}
		}else{
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
		}

		//monument - can play more than one
		if(ModelProxy.isPlayerTurn()){ // isPlaying-state thing

		}else{
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
		}

		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		getPlayCardView().reset(); //reset any disabled/enabled cards... may not be necessary
		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		
	}

	@Override
	public void playMonumentCard() {
		
	}

	@Override
	public void playRoadBuildCard() {
		
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		
	}

}

