package client.devcards;

import client.base.*;
import client.model.*;
import client.model.player.*;
import shared.definitions.*;
import shared.commands.*;
import client.server.*;
import client.model.*;
import client.model.map.*;
import shared.locations.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private SoldierAction soldierAction;
	private RoadBuildingAction roadAction;
	private ServerProxy serverProxy;

	
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
		this.soldierAction = new SoldierAction();
		this.roadAction = new RoadBuildingAction();
		this.serverProxy = GameManager.getInstance().getServer();
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
		Player player = GameManager.getInstance().getActivePlayer();
		BuyDevCard buyDevCard = new BuyDevCard(player.getPlayerIndex());
		serverProxy.execute(buyDevCard);
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

		if (ModelProxy.isPlayerTurn() && player.isPlayedDevCard() == false){ //hasn't played a dev card and 
			//set enabled
			if(oldDevCards.getMonopoly() < 1){
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
			}
			
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
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
		}


		if(ModelProxy.isPlayerTurn()){ // isPlaying-state thing
			
			if(oldDevCards.getMonument() < 1){
				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
			}
			else{
				getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
			}
			
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
		if(resource != null)
		{
			Player player = GameManager.getInstance().getActivePlayer();
			Monopoly monopoly = new Monopoly(player.getPlayerIndex(), resource);
			serverProxy.execute(monopoly);
			getPlayCardView().closeModal();
		}
	}

	@Override
	public void playMonumentCard() {
		Player player = GameManager.getInstance().getActivePlayer();
		Monument monument = new Monument(player.getPlayerIndex());
		serverProxy.execute(monument);
		getPlayCardView().reset(); 
	}

	@Override
	public void playRoadBuildCard() {
		roadAction.execute();
		getPlayCardView().closeModal();
	}

	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();

		getPlayCardView().closeModal();

	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		if(resource1 != resource2 && resource1 != null && resource2 != null)
		{
			Player player = GameManager.getInstance().getActivePlayer();
			YearOfPlenty yop = new YearOfPlenty(player.getPlayerIndex(), resource1, resource2);
			serverProxy.execute(yop);
			getPlayCardView().closeModal();
		}
	}

}

