package shared.commands;

import server.ServerFacade;
import shared.locations.HexLocation;
import client.model.PlayerIndex;

public class RobPlayer extends MoveCommand{
    private int victimIndex;
    private HexLocation location;

    public RobPlayer(PlayerIndex index, PlayerIndex victim, HexLocation location){
        super("robPlayer", index);
        this.victimIndex = victim.getIndex();
        this.location = location;
    };

    public void serverExecute(ServerFacade facade){}
}
