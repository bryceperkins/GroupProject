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
	private boolean set_players;

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
		player_index = -1;
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
		set_players = false;
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
		
		if (game != null && game.getTradeOffer() != null && game.getTradeOffer().getReceiver() != null){
			TradeOffer trade_offer = game.getTradeOffer();
			Player player = manager.getActivePlayer();
			if (player.getPlayerIndex() == trade_offer.getReceiver()){
				ResourceList offer = trade_offer.getOffer();
				getAcceptOverlay().showModal();
				getAcceptOverlay().reset();
			//Set senders player name
				Player offerer = game.getPlayer(trade_offer.getSender());
				getAcceptOverlay().setPlayerName(offerer.getName());
			//Set give and get values in trade offer
				if (offer.getWood() > 0){
					getAcceptOverlay().addGetResource(ResourceType.WOOD, offer.getWood());
				} else if (offer.getWood() < 0){
					getAcceptOverlay().addGiveResource(ResourceType.WOOD, offer.getWood()*-1);
				}
				if (offer.getBrick() > 0){
					getAcceptOverlay().addGetResource(ResourceType.BRICK, offer.getBrick());
				} else if (offer.getBrick() < 0){
					getAcceptOverlay().addGiveResource(ResourceType.BRICK, offer.getBrick()*-1);
				}
				if (offer.getSheep() > 0){
					getAcceptOverlay().addGetResource(ResourceType.SHEEP, offer.getSheep());
				} else if (offer.getSheep() < 0){
					getAcceptOverlay().addGiveResource(ResourceType.SHEEP, offer.getSheep()*-1);
				}
				if (offer.getOre() > 0){
					getAcceptOverlay().addGetResource(ResourceType.ORE, offer.getOre());
				} else if (offer.getOre() < 0){
					getAcceptOverlay().addGiveResource(ResourceType.ORE, offer.getOre()*-1);
				}
				if (offer.getWheat() > 0){
					getAcceptOverlay().addGetResource(ResourceType.WHEAT, offer.getWheat());
				} else if (offer.getWheat() < 0){
					getAcceptOverlay().addGiveResource(ResourceType.WHEAT, offer.getWheat()*-1);
				}
			//Determines whether player is able to make trade
				getAcceptOverlay().setAcceptEnabled(player.canMakeTrade(trade_offer));
			} else if (player.getPlayerIndex() == trade_offer.getSender()){
				getWaitOverlay().showModal();
			} 
		} else if (game != null) {
			getWaitOverlay().closeModal();
		}
	}
	
	@Override
	public void startTrade() {
		getTradeOverlay().showModal();
		getTradeOverlay().reset();
		player_index = -1;
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
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Set the trade you want to make");
		Player player = manager.getActivePlayer();
		Game game = manager.getActiveGame();	
		
		//Creates list of who you can trade with
		if (!set_players){
			set_players = true;
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
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		boolean canIncrease=true;
		boolean canDecrease=true;
		Player player = manager.getActivePlayer();
		ResourceList resources = player.getResources();
		
		if (resource==ResourceType.WOOD){
			wood--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			if (resources.getWood() <= wood && send_wood){
				canIncrease=false;
			}
			if (wood <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.WHEAT){
			wheat--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			if (resources.getWheat() <= wheat && send_wheat){
				canIncrease=false;
			}
			if (wheat <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.BRICK){
			brick--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			if (resources.getBrick() <= brick && send_brick){
				canIncrease=false;
			}
			if (brick <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.SHEEP){
			sheep--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			if (resources.getSheep() <= sheep && send_sheep){
				canIncrease=false;
			}
			if (sheep <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.ORE){
			ore--;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			if (resources.getOre() <= ore && send_ore){
				canIncrease=false;
			}
			if (ore <= 0){
				canDecrease=false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,canDecrease);
		if (!oneSending() || !oneRecieving()){
			getTradeOverlay().setStateMessage("Set the trade you want to make");
			getTradeOverlay().setTradeEnabled(false);
		} else if (player_index == -1){
			getTradeOverlay().setStateMessage("Who would you like to trade with");
			getTradeOverlay().setTradeEnabled(false);
		} else {
			getTradeOverlay().setStateMessage("Make trade");
			getTradeOverlay().setTradeEnabled(true);
		}
	}
	
	public boolean oneSending(){
		if ((wood > 0 && !send_wood) || (brick > 0 && !send_brick) || (ore > 0 && !send_ore) || (wheat > 0 && !send_wheat) || (sheep > 0 && !send_sheep)){
			return true;
		}
		return false;
	}
	
	public boolean oneRecieving(){
		if ((wood > 0 && send_wood) || (brick > 0 && send_brick) || (ore > 0 && send_ore) || (wheat > 0 && send_wheat) || (sheep > 0 && send_sheep)){
			return true;
		}
		return false;
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		boolean canIncrease=true;
		boolean canDecrease=true;
		Player player = manager.getActivePlayer();
		ResourceList resources = player.getResources();
		
		if (resource==ResourceType.WOOD){
			wood++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			if (resources.getWood() <= wood && send_wood){
				canIncrease=false;
			}
			if (wood <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.WHEAT){
			wheat++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			if (resources.getWheat() <= wheat && send_wheat){
				canIncrease=false;
			}
			if (wheat <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.BRICK){
			brick++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			if (resources.getBrick() <= brick && send_brick){
				canIncrease=false;
			}
			if (brick <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.SHEEP){
			sheep++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			if (resources.getSheep() <= sheep && send_sheep){
				canIncrease=false;
			}
			if (sheep <= 0){
				canDecrease=false;
			}
		}else if (resource==ResourceType.ORE){
			ore++;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			if (resources.getOre() <= ore && send_ore){
				canIncrease=false;
			}
			if (ore <= 0){
				canDecrease=false;
			}
		}
		getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,canDecrease);
		if (!oneSending() || !oneRecieving()){
			getTradeOverlay().setStateMessage("Set the trade you want to make");
			getTradeOverlay().setTradeEnabled(false);
		} else if (player_index == -1){
			getTradeOverlay().setStateMessage("Who would you like to trade with");
			getTradeOverlay().setTradeEnabled(false);
		} else {
			getTradeOverlay().setStateMessage("Make trade");
			getTradeOverlay().setTradeEnabled(true);
		}
	}

	@Override
	public void sendTradeOffer() {
		if (ModelProxy.isPlayerTurn()){
			Player player = manager.getActivePlayer();
			ResourceList resources = new ResourceList();
			if (send_wood){
				resources.setWood(wood);
			} else {
				resources.setWood(wood *= -1);
			}
			if (send_wheat){
				resources.setWheat(wheat);
			} else {
				resources.setWheat(wheat *= -1);
			}
			if (send_brick){
				resources.setBrick(brick);
			} else {
				resources.setBrick(brick *= -1);
			}
			if (send_sheep){
				resources.setSheep(sheep);
			} else {
				resources.setSheep(sheep *= -1);
			}
			if (send_ore){
				resources.setOre(ore);
			} else {
				resources.setOre(ore *= -1);
			}
			OfferTrade offer = new OfferTrade(player.getPlayerIndex(),resources, PlayerIndex.valueOf(player_index));
			ServerProxy server = manager.getServer();
			server.execute(offer);
		}
		getTradeOverlay().closeModal();
		//getWaitOverlay().showModal();
		getWaitOverlay().setMessage("Waiting for trade to go through");
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		player_index=playerIndex;
		if (!oneSending() || !oneRecieving()){
			getTradeOverlay().setStateMessage("Set the trade you want to make");
			getTradeOverlay().setTradeEnabled(false);
		} else if (player_index == -1){
			getTradeOverlay().setStateMessage("Who would you like to trade with");
			getTradeOverlay().setTradeEnabled(false);
		} else {
			getTradeOverlay().setStateMessage("Make trade");
			getTradeOverlay().setTradeEnabled(true);
		}
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		Player player = manager.getActivePlayer();
		ResourceList resources = player.getResources();
		boolean canIncrease = true;
		if (resource==ResourceType.WOOD){
			wood=0;
			send_wood=false;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.WHEAT){
			wheat=0;
			send_wheat=false;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.BRICK){
			brick=0;
			send_brick=false;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.SHEEP){
			sheep=0;
			send_sheep=false;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.ORE){
			ore=0;
			send_ore=false;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		Player player = manager.getActivePlayer();
		ResourceList resources = player.getResources();
		boolean canIncrease = true;
		if (resource==ResourceType.WOOD){
			wood=0;
			send_wood=true;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wood));
			if (resources.getWood() <= wood){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.WHEAT){
			wheat=0;
			send_wheat=true;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(wheat));
			if (resources.getWheat() <= wheat){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.BRICK){
			brick=0;
			send_brick=true;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(brick));
			if (resources.getBrick() <= brick){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.SHEEP){
			sheep=0;
			send_sheep=true;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(sheep));
			if (resources.getSheep() <= sheep){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}else if (resource==ResourceType.ORE){
			ore=0;
			send_ore=true;
			getTradeOverlay().setResourceAmount(resource, String.valueOf(ore));
			if (resources.getOre() <= ore){
				canIncrease=false;
			}
			getTradeOverlay().setResourceAmountChangeEnabled(resource,canIncrease,false);
		}
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
		if (!oneSending() || !oneRecieving()){
			getTradeOverlay().setStateMessage("Set the trade you want to make");
			getTradeOverlay().setTradeEnabled(false);
		} else if (player_index == -1){
			getTradeOverlay().setStateMessage("Who would you like to trade with");
			getTradeOverlay().setTradeEnabled(false);
		} else {
			getTradeOverlay().setStateMessage("Make trade");
			getTradeOverlay().setTradeEnabled(true);
		}
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		Player player = manager.getActivePlayer();
		AcceptTrade trade = new AcceptTrade(player.getPlayerIndex(), Boolean.valueOf(willAccept));
		ServerProxy server = manager.getServer();
		server.execute(trade);
		getAcceptOverlay().closeModal();
	}

}