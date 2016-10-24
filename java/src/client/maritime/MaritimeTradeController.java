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
	private	ModelProxy proxy = new ModelProxy();
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
	}

	//public MaritimeTrade(PlayerIndex index, int ratio, ResourceType inputResource, ResourceType outputResource)
	@Override
	public void makeTrade() {
		Game game = manager.getActiveGame();
		if (proxy.isPlayerTurn()){
			Player player = manager.getActivePlayer();
			pt = getTradeOverlay().
			if (proxy.canMakeMaritimeTrade()
			
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
		getTradeView().enableMaritimeTrade(proxy.isPlayerTurn() && game.getState().canTrade());
	}
	
	@Override
	public void setGetResource(ResourceType resource) {
		
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		Player player = manager.getActivePlayer();
		List<Port> ports = player.getPorts();
		for (int i = 0; i < ports.size(); i++){
			if (ports.get(i).getType() == 
		}
		getTradeOverlay().selectGiveOption(resource, )
	}

	@Override
	public void unsetGetValue() {

	}

	@Override
	public void unsetGiveValue() {

	}

}

