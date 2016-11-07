package shared.commands;

import client.server.*;
import client.model.*;
import server.ServerFacade;

public class DiscardCards extends MoveCommand {
    private ResourceList discardedCards;

    public DiscardCards(PlayerIndex index, ResourceList cards){
        super("discardCards", index);
        this.discardedCards = cards;
    };

    public void serverExecute(ServerFacade facade){}
}
