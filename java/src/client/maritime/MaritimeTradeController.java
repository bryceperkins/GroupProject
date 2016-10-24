package client.maritime;

import shared.definitions.*;
import client.base.*;
import shared.commands.*;
import client.communication.*;
import client.model.*;
import client.model.player.*;
import client.server.*;
import java.util.*;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private GameManager manager = GameManager.getInstance();
	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		getTradeOverlay().showModal();
		getTradeOverlay().reset();
		getTradeOverlay().setStateMessage("Choose what to give up");
		Player player = manager.getActivePlayer();
		ResourceType[] resource_types;
		if (ModelProxy.playerCanMakeMaritimeTrade(WOOD)){
			resource_types.add(WOOD);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,0,0,0,3))){
			resource_types.add(WOOD);
		} else if (player.hasResources(new ResourceList(0,0,0,0,4))){
			resource_types.add(WOOD);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(BRICK)){
			resource_types.add(BRICK);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(3,0,0,0,0))){
			resource_types.add(BRICK);
		} else if (player.hasResources(new ResourceList(4,0,0,0,0))){
			resource_types.add(BRICK);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(ORE)){
			resource_types.add(ORE);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,3,0,0,0))){
			resource_types.add(ORE);
		} else if (player.hasResources(new ResourceList(0,4,0,0,0))){
			resource_types.add(ORE);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(SHEEP)){
			resource_types.add(SHEEP);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,0,3,0,0))){
			resource_types.add(SHEEP);
		} else if (player.hasResources(new ResourceList(0,0,4,0,0))){
			resource_types.add(SHEEP);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(WHEAT)){
			resource_types.add(WHEAT);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,0,0,3,0))){
			resource_types.add(WHEAT);
		} else if (player.hasResources(new ResourceList(0,0,0,4,0))){
			resource_types.add(WHEAT);
		}
		getTradeOverlay().showGiveOptions(resource_types);
	}

	//public MaritimeTrade(PlayerIndex index, int ratio, ResourceType inputResource, ResourceType outputResource)
	@Override
	public void makeTrade() {
		Game game = manager.getActiveGame();
		if (ModelProxy.isPlayerTurn()){
			Player player = manager.getActivePlayer();
			pt = getTradeOverlay().
			if (ModelProxy.canMakeMaritimeTrade()
			
			MaritimeTrade trade = new MaritimeTrade(player.getPlayerIndex(), 
		}
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
	}

	public void update(Observable ob, Object o){
		Game game = manager.getActiveGame();
		getTradeView().enableMaritimeTrade(ModelProxy.isPlayerTurn() && game.getState().canTrade());
	}
	
	@Override
	public void setGetResource(ResourceType resource) {
		getTradeOverlay().selectGetOption(resource, 1);
		getTradeOverlay().setStateMessage("Trade!")
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		Player player = manager.getActivePlayer();
		List<Port> ports = player.getPorts();
		int amount = 4;
		for (int i = 0; i < ports.size(); i++){
			if (ports.get(i).getType() == THREE){
				amount = 3;
			} else if (ports.get(i).getType() == resource) {
				amount = 2;
			}
		}
		getTradeOverlay().selectGiveOption(resource, amount);
		getTradeOverlay().setStateMessage("Choose what to get");
		
	}

	@Override
	public void unsetGetValue() {
		
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().reset();
		getTradeOverlay().hideGetValues();
		getTradeOverlay().setStateMessage("Choose what to give up");
		Player player = manager.getActivePlayer();
		ResourceType[] resource_types;
		if (ModelProxy.playerCanMakeMaritimeTrade(WOOD)){
			resource_types.add(WOOD);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,0,0,0,3))){
			resource_types.add(WOOD);
		} else if (player.hasResources(new ResourceList(0,0,0,0,4))){
			resource_types.add(WOOD);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(BRICK)){
			resource_types.add(BRICK);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(3,0,0,0,0))){
			resource_types.add(BRICK);
		} else if (player.hasResources(new ResourceList(4,0,0,0,0))){
			resource_types.add(BRICK);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(ORE)){
			resource_types.add(ORE);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,3,0,0,0))){
			resource_types.add(ORE);
		} else if (player.hasResources(new ResourceList(0,4,0,0,0))){
			resource_types.add(ORE);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(SHEEP)){
			resource_types.add(SHEEP);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,0,3,0,0))){
			resource_types.add(SHEEP);
		} else if (player.hasResources(new ResourceList(0,0,4,0,0))){
			resource_types.add(SHEEP);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(WHEAT)){
			resource_types.add(WHEAT);
		} else if (ModelProxy.playerHasThreePort && player.hasResources(new ResourceList(0,0,0,3,0))){
			resource_types.add(WHEAT);
		} else if (player.hasResources(new ResourceList(0,0,0,4,0))){
			resource_types.add(WHEAT);
		}
		getTradeOverlay().showGiveOptions(resource_types);
	}

}

