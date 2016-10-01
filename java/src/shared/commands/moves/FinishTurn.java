package shared.commands;

import client.model.PlayerIndex;

public class FinishTurn extends MoveCommand{

    public FinishTurn(PlayerIndex index){
        super("finishTurn", index);
    };
}
