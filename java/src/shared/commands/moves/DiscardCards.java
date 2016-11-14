package shared.commands;

import shared.model.*;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class DiscardCards extends MoveCommand {
    private ResourceList discardedCards;

    public DiscardCards(PlayerIndex index, ResourceList cards){
        super("discardCards", index);
        this.discardedCards = cards;
    };

    public String  serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.discardCards(getIndex(), discardedCards);
    }
}
