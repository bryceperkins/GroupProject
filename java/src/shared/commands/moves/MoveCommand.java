package shared.commands;

import shared.communication.*;
import client.model.PlayerIndex;

public abstract class MoveCommand extends Command {
    private String type;
    private int playerIndex;

    public MoveCommand(String type, PlayerIndex index){
        super();
        endpoint = "/moves/" + type;
        method = "POST";
        this.type = type;
        this.playerIndex = index.getIndex();
    }
}
