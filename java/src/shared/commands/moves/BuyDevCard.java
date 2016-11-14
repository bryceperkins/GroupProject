package shared.commands;

import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class BuyDevCard extends MoveCommand{

    public BuyDevCard(PlayerIndex index){
        super("buyDevCard", index);
    };

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.buyDevCard(getIndex());
    }
}
