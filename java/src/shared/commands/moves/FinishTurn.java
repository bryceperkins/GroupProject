package shared.commands;

import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class FinishTurn extends MoveCommand {

    public FinishTurn(PlayerIndex index){
        super("finishTurn", index);
    };

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.finishTurn(getIndex());
    }
}
