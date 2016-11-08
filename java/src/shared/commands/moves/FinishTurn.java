package shared.commands;

import shared.model.PlayerIndex;

public class FinishTurn extends shared.commands.MoveCommand {

    public FinishTurn(PlayerIndex index){
        super("finishTurn", index);
    };
}
