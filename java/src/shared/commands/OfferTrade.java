package shared.commands;

import client.model.*;

public class OfferTrade extends PlayingCommand{
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
    public OfferTrade(TradeOffer offer, PlayerIndex receiver){};
}
