package shared.commands;

import shared.definitions.*;
import client.model.PlayerIndex;

public class Monopoly extends MoveCommand{
    private ResourceType resource;
    /**
     *  Monopoly
     *
     *  Play the monopoly DevCard
     *
     *  @param resource - The resource you are collecting from the other players
     *
     *  @post You gain the amount of specified resource the other players lost
     */
    public Monopoly(PlayerIndex index, ResourceType resource){
        super("Monopoly", index);
        this.resource = resource;
    };

    public String serverExecute(){
        return "";
    }
}
