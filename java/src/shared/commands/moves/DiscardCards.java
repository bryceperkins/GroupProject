package shared.commands;

import client.server.*;
import client.model.*;

public class DiscardCards extends MoveCommand {
    private ResourceList discardedCards;

    public DiscardCards(int index, ResourceList cards){
        super("discardCard", index);
        this.discardedCards = cards;
    };
}
