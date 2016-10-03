package shared.commands;

import shared.communication.*;

public abstract class MoveCommand extends Command {
    private String type;
    private int playerIndex;

    public MoveCommand(String type, int index){
        super();
        endpoint = "/moves/" + type;
        method = "POST";
        this.type = type;
        this.playerIndex = index;
    }
}
