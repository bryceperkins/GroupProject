package shared.commands;

import client.server.*;
import client.model.*;

public class DiscardCards extends PlayingCommand {
    /**
     *  Purchase a dev card from the Bank
     *
     *  @pre have over 7 cardsa
     *  @pre client state 'Discarding'
     *  @pre you have the cards
     *
     *  @see client.model.TurnTracker
     *
     *  @post you lost the specified resources
     *  @post after last discard model status set to 'Robbing'
     */
    private TradeOffer discardedCards;
    public DiscardCards(TradeOffer discardedCards){};
}
