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
	}

	//public MaritimeTrade(PlayerIndex index, int ratio, ResourceType inputResource, ResourceType outputResource)
	@Override
	public void makeTrade() {
		Game game = manager.getActiveGame();
		ModelProxy proxy = new ModelProxy();
		if (proxy.isPlayerTurn()){
			Player player = manager.getActivePlayer();
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
		
	}
	
	@Override
	public void setGetResource(ResourceType resource) {

	}

	@Override
	public void setGiveResource(ResourceType resource) {

	}

	@Override
	public void unsetGetValue() {

	}

	@Override
	public void unsetGiveValue() {

	}

}

