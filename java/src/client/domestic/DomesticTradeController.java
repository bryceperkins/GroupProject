package client.domestic;

import client.misc.*;
import shared.definitions.*;
import client.base.*;
import client.data.PlayerInfo;
import shared.commands.*;
import client.communication.*;
import client.model.*;
import client.model.player.*;
import client.server.*;
import java.util.*;
import client.model.map.*;

/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private GameManager manager;
	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private int player_index;
	private int wood, wheat, brick, ore, sheep;
	private boolean send_wood, send_wheat, send_brick, send_ore, send_sheep;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		manager = GameManager.getInstance();
		manager.addObserver(this);
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		player_index = 0;
		wood=0;
		wheat=0;
		brick=0;
		ore=0;
		sheep=0;
		send_wood=false;
		send_sheep=false;
		send_wheat=false;
		send_brick=false;
		send_ore=false;
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	public void update(Observable ob, Object o){
		Game game = manager.getActiveGame();
		getTradeView().enableDomesticTrade(ModelProxy.isPlayerTurn() && game.getState().canTrade());
	}
	
	@Override
	public void startTrade() {
		getTradeOverlay().showModal();
		getTradeOverlay().reset();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Set the trade you want to make");
		Player player = manager.getActivePlayer();
		Game game = manager.getActiveGame();
		//Creates list of who you can trade with
		List<PlayerInfo> players_info = new ArrayList<PlayerInfo>();
		for (Player p: game.getPlayers()){
			if (p.getPlayerIndex().getIndex() != player.getPlayerIndex().getIndex()){
				PlayerInfo info = new PlayerInfo();
				info.setId(p.getPlayerID());
				info.setPlayerIndex(p.getPlayerIndex().getIndex());
				info.setName(p.getName());
				info.setColor(p.getColor());
				players_info.add(info);
			}
		}
		PlayerInfo[] info_array = Arrays.copyOf(players_info.toArray(), players_info.toArray().length, PlayerInfo[].class);
		getTradeOverlay().setPlayers(info_array);
		
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		if (resource==ResourceType.WOOD){
			wood--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			if (wood <= 0){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,true,false);
			}
		}else if (resource==ResourceType.WHEAT){
			wheat--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			if (wheat() <= 0){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,true,false);
			}
		}else if (resource==ResourceType.BRICK){
			brick--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			if (brick <= 0){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,true,false);
			}
		}else if (resource==ResourceType.SHEEP){
			sheep--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			if (sheep <= 0){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,true,false);
			}
		}else if (resource==ResourceType.ORE){
			ore--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			if (ore <= 0){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,true,false);
			}
		}
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		Player player = manager.getActivePlayer();
		ResourceList resources = player.getResources();
		if (resource==ResourceType.WOOD){
			wood++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			if (resources.getWood() <= wood){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,false,true);
			}
		}else if (resource==ResourceType.WHEAT){
			wheat++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			if (resources.getWheat() <= wheat){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,false,true);
			}
		}else if (resource==ResourceType.BRICK){
			brick++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			if (resources.getBrick() <= brick){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,false,true);
			}
		}else if (resource==ResourceType.SHEEP){
			sheep++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			if (resources.getSheep() <= sheep){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,false,true);
			}
		}else if (resource==ResourceType.ORE){
			ore++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			if (resources.getOre() <= ore){
				getTradeOverlay().setResourceAmountChangeEnabled(resource,false,true);
			}
		}
	}

	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		unsetResouce(ResourceType.WOOD);
		unsetResouce(ResourceType.BRICK);
		unsetResouce(ResourceType.SHEEP);
		unsetResouce(ResourceType.WHEAT);
		unsetResouce(ResourceType.ORE);
		player_index=playerIndex;
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		Player player = manager.getActivePlayer();
		ResourceList resources = player.getResources();
		boolean canIncrease = true;
		if (resource==ResourceType.WOOD){
			wood=0;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			if (resources.getWood() <= wood){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.WHEAT){
			wheat=0;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			if (resources.getWheat() <= wheat){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.BRICK){
			brick=0;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			if (resources.getBrick() <= brick){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.SHEEP){
			sheep=0;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			if (resources.getSheep() <= sheep){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.ORE){
			ore=0;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			if (resources.getOre() <= ore){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		
	}

	@Override
	public void unsetResource(ResourceType resource) {
		getTradeOverlay().setResourceAmountChangeEnabled(resource,false,false);
		if (resource==ResourceType.WOOD){
			wood=0;
		}else if (resource==ResourceType.WHEAT){
			wheat=0;
		}else if (resource==ResourceType.BRICK){
			brick=0;
		}else if (resource==ResourceType.SHEEP){
			sheep=0;
		}else if (resource==ResourceType.ORE){
			ore=0;
		}
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {

		getAcceptOverlay().closeModal();
	}

}