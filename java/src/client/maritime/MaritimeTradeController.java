package client.maritime;

import shared.definitions.*;
import client.base.*;
import shared.commands.*;
import client.communication.*;
import client.model.*;
import client.model.player.*;
import client.server.*;
import java.util.*;
import client.model.map.*;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private GameManager manager = GameManager.getInstance();
	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType resource_given = null;
	private ResourceType resource_gotten = null;
	private int resource_given_amount = 0;
	
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
		ResourceType[] resource_types = null;
		int z = 0;
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WOOD)){
			resource_types[z] = ResourceType.WOOD;
			z++;
		} 
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.BRICK)){
			resource_types[z] = ResourceType.BRICK;
			z++;
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.ORE)){
			resource_types[z] = ResourceType.ORE;
			z++;
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.SHEEP)){
			resource_types[z] = ResourceType.SHEEP;
			z++;
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WHEAT)){
			resource_types[z] = ResourceType.WHEAT;
			z++;
		}
		getTradeOverlay().showGiveOptions(resource_types);
	}

	//public MaritimeTrade(PlayerIndex index, int ratio, ResourceType inputResource, ResourceType outputResource)
	@Override
	public void makeTrade() {
		if (ModelProxy.isPlayerTurn()){
			Player player = manager.getActivePlayer();
			MaritimeTrade trade = new MaritimeTrade(player.getPlayerIndex(), resource_given_amount, resource_given, resource_gotten);
			ServerProxy server = manager.getServer();
			server.execute(trade);
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
		resource_gotten = resource;
		getTradeOverlay().setStateMessage("Trade!");
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		Player player = manager.getActivePlayer();
		List<Port> ports = player.getPorts();
		int amount = 4;
		for (int i = 0; i < ports.size(); i++){
			if (ports.get(i).getResource().equals(resource)){
				if (amount > 3) amount = 3;
			} else if (ports.get(i).getResource() == resource) {
				amount = 2;
			}
		}
		getTradeOverlay().selectGiveOption(resource, amount);
		getTradeOverlay().setStateMessage("Choose what to get");
		resource_given = resource;
		resource_given_amount = amount;
	}

	@Override
	public void unsetGetValue() {
		
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().reset();
		getTradeOverlay().setStateMessage("Choose what to give up");
		Player player = manager.getActivePlayer();
		ResourceType[] resource_types = null;
		int z = 0;
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WOOD)){
			resource_types[z] = ResourceType.WOOD;
			z++;
		} 
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.BRICK)){
			resource_types[z] = ResourceType.BRICK;
			z++;
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.ORE)){
			resource_types[z] = ResourceType.ORE;
			z++;
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.SHEEP)){
			resource_types[z] = ResourceType.SHEEP;
			z++;
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WHEAT)){
			resource_types[z] = ResourceType.WHEAT;
			z++;
		}
		getTradeOverlay().showGiveOptions(resource_types);
	}

}

