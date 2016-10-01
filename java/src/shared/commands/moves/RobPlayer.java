package shared.commands;

import client.model.map.HexLocation;

public class RobPlayer extends MoveCommand{
    private int victimIndex;
    private HexLocation location;

    public RobPlayer(int index, int victim, HexLocation location){
        super("robPlayer", index);
        this.victimIndex = victim;
        this.location = location;
    };
}
