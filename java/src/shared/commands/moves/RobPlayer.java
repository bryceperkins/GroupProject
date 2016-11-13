package shared.commands;

import shared.locations.HexLocation;
import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class RobPlayer extends MoveCommand{
    private int victimIndex;
    private HexLocation location;

    public RobPlayer(PlayerIndex index, PlayerIndex victim, HexLocation location){
        super("robPlayer", index);
        this.victimIndex = victim.getIndex();
        this.location = location;
    };

    public String serverExecute(iServerFacade f){
        return "";
    }
}
