package client.resources;

import java.util.*;

import client.base.*;
import client.model.*;
import java.util.Random;
import client.model.player.*;

import javax.annotation.Resource;

/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;
	private GameManager manager = GameManager.getInstance();
	
	public ResourceBarController(IResourceBarView view) {
		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
		manager.addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {
		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	private void updateValues() {
        if (manager.getActiveGame() != null
				&& manager.getActivePlayer() != null
				&& manager.getActivePlayer().getResources() != null) {
			System.out.println("Updating Values");
			Player player = manager.getActivePlayer();
			ResourceList resources = player.getResources();

			getView().setElementAmount(ResourceBarElement.WOOD, resources.getWood());
			getView().setElementAmount(ResourceBarElement.BRICK, resources.getBrick());
			getView().setElementAmount(ResourceBarElement.SHEEP, resources.getSheep());
			getView().setElementAmount(ResourceBarElement.WHEAT, resources.getWheat());
			getView().setElementAmount(ResourceBarElement.ORE, resources.getOre());
			getView().setElementAmount(ResourceBarElement.ROAD, player.getRoadsRemaining());
			getView().setElementAmount(ResourceBarElement.SETTLEMENT, player.getSettlementsRemaining());
			getView().setElementAmount(ResourceBarElement.CITY, player.getCitiesRemaining());
			getView().setElementAmount(ResourceBarElement.SOLDIERS, player.getSoldiersPlayed());
		}
	}

	private void updateClickability() {
		if (manager.getActiveGame() != null
				&& manager.getActivePlayer() != null
				&& manager.getActivePlayer().getResources() != null) {
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, ModelProxy.playerCanBuildSettlement());
			getView().setElementEnabled(ResourceBarElement.CITY, ModelProxy.playerCanBuildCity());
			getView().setElementEnabled(ResourceBarElement.ROAD, ModelProxy.playerCanBuildRoad());
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, ModelProxy.canBuyDevCard());

			if (!ModelProxy.isPlayerTurn()) {
				for (ResourceBarElement e : ResourceBarElement.values()) {
					getView().setElementEnabled(e, false);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);

		updateValues();
		updateClickability();
	}
}

