package shared.commands;

import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class FinishTurn extends shared.commands.MoveCommand {

    public FinishTurn(PlayerIndex index){
        super("finishTurn", index);
    };

    public String serverExecute(iServerFacade f){
        return "";
    }
}
