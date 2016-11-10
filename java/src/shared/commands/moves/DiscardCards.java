package shared.commands;

import shared.model.*;

public class DiscardCards extends MoveCommand {
    private ResourceList discardedCards;

    public DiscardCards(PlayerIndex index, ResourceList cards){
        super("discardCards", index);
        this.discardedCards = cards;
    };

    public String  serverExecute(){
        return "";
    }
}
