package shared.commands;

import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class BuyDevCard extends MoveCommand{

    public BuyDevCard(PlayerIndex index){
        super("buyDevCard", index);
    };

    public String serverExecute(iServerFacade f){
        return "";
    }
}
