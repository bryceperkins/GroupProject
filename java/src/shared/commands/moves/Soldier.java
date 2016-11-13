package shared.commands;

import shared.locations.*;
import shared.model.*;
import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class Soldier extends MoveCommand{
    private HexLocation location;
    private int victimIndex;
    /**
     *  Relocate the robber and rob another player
     *
     *  @param location - new location for the Robber
     *  @param victimIndex - player to be robber or -1 for no one
     *
     *  @pre Robber is not staying in the same locationY
     *  @pre Player being robbed has resource cards
     *  
     *  @post Robber has moved
     *  @post Player being robbed (if any) has given you their resource cards
     *  @post Largest army awarded to player with most Solder cards
     */
    public Soldier(PlayerIndex index, PlayerIndex victimIndex, HexLocation location){
        super("Soldier", index);
        this.location = location;
        this.victimIndex = victimIndex.getIndex();
    };

    public String serverExecute(iServerFacade f){
        return "";
    }

}
