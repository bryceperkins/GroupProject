package shared.commands;

import client.model.map.HexLocation;

public class FinishTurn extends MoveCommand{

    public FinishTurn(int index){
        super("finishTurn", index);
    };
}
