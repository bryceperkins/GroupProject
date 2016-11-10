package client.maritime;

import shared.definitions.*;
import client.base.*;
import shared.commands.*;
import shared.model.*;
import shared.model.player.*;
import client.server.*;
import java.util.*;
import shared.model.map.*;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private GameManager manager;
	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType resource_given = null;
	private ResourceType resource_gotten = null;
	private int resource_given_amount = 0;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);
		manager = GameManager.getInstance();
		manager.addObserver(this);
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
		getTradeOverlay().setTradeEnabled(false);
		Player player = manager.getActivePlayer();
		System.out.println(player.getPorts().size());
		List<ResourceType> resource_type_list = new ArrayList<ResourceType>();
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WOOD)){
			resource_type_list.add(ResourceType.WOOD);
		} 
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.BRICK)){
			resource_type_list.add(ResourceType.BRICK);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.SHEEP)){
			resource_type_list.add(ResourceType.SHEEP);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WHEAT)){
			resource_type_list.add(ResourceType.WHEAT);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.ORE)){
			resource_type_list.add(ResourceType.ORE);
		}
		if (resource_type_list.size() > 0){
			getTradeOverlay().setStateMessage("Choose what to give up");
		} else {
			getTradeOverlay().setStateMessage("You dont have enough resources");
		}
		
		ResourceType[] type_array = Arrays.copyOf(resource_type_list.toArray(), resource_type_list.toArray().length, ResourceType[].class);
		getTradeOverlay().showGiveOptions(type_array);
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
		getTradeOverlay().setTradeEnabled(true);
		getTradeOverlay().setStateMessage("Trade!");
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		getTradeOverlay().setTradeEnabled(false);
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
		resource_given = resource;
		resource_given_amount = amount;
		getTradeOverlay().selectGiveOption(resource, amount);
		getTradeOverlay().setStateMessage("Choose what to get");
		List<ResourceType> resource_types = new ArrayList<ResourceType>();
		resource_types.add(ResourceType.WOOD);
		resource_types.add(ResourceType.BRICK);
		resource_types.add(ResourceType.WHEAT);
		resource_types.add(ResourceType.SHEEP);
		resource_types.add(ResourceType.ORE);
		ResourceType[] type_array = Arrays.copyOf(resource_types.toArray(), resource_types.toArray().length, ResourceType[].class);
		getTradeOverlay().showGetOptions(type_array);
		
	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().selectGiveOption(resource_given, resource_given_amount);
		getTradeOverlay().setStateMessage("Choose what to get");
		List<ResourceType> resource_types = new ArrayList<ResourceType>();
		resource_types.add(ResourceType.WOOD);
		resource_types.add(ResourceType.BRICK);
		resource_types.add(ResourceType.WHEAT);
		resource_types.add(ResourceType.SHEEP);
		resource_types.add(ResourceType.ORE);
		ResourceType[] type_array = Arrays.copyOf(resource_types.toArray(), resource_types.toArray().length, ResourceType[].class);
		getTradeOverlay().showGetOptions(type_array);
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().reset();
		Player player = manager.getActivePlayer();
		List resource_type_list = new ArrayList();
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WOOD)){
			resource_type_list.add(ResourceType.WOOD);
		} 
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.BRICK)){
			resource_type_list.add(ResourceType.BRICK);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.SHEEP)){
			resource_type_list.add(ResourceType.SHEEP);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.WHEAT)){
			resource_type_list.add(ResourceType.WHEAT);
		}
		if (ModelProxy.playerCanMakeMaritimeTrade(PortType.ORE)){
			resource_type_list.add(ResourceType.ORE);
		}
		if (resource_type_list.size() > 0){
			getTradeOverlay().setStateMessage("Choose what to give up");
		} else {
			getTradeOverlay().setStateMessage("You dont have enough resources");
		}
		ResourceType[] type_array = Arrays.copyOf(resource_type_list.toArray(), resource_type_list.toArray().length, ResourceType[].class);
		getTradeOverlay().showGiveOptions(type_array);
	}

}

