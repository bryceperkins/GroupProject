package shared.commands;

import client.model.PlayerIndex;

public class FinishTurn extends shared.commands.MoveCommand {

    public FinishTurn(PlayerIndex index){
        super("finishTurn", index);
    };

    public void serverExecute(){}
}
