package shared.commands;

import client.model.*;
import server.ServerFacade;

public class OfferTrade extends MoveCommand{
    private int receiver;
    private ResourceList offer;
    /**
     *  Offer a trade to another player
     *
     *  @param offer - cards in the trade
     *  @param receiver - the recipient of the trade
     *
     *  @pre You have enough of the specified resources
     *  
     *  @post trade is offered to the other player
     */
    public OfferTrade(PlayerIndex index, ResourceList offer, PlayerIndex receiver){
        super("offerTrade", index);
        this.offer = offer;
        this.receiver = receiver.getIndex();
    };

    public void serverExecute(ServerFacade facade){}
}
