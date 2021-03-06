package shared.commands;

import shared.definitions.*;
import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

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

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.Monopoly(getIndex(), this.resource);
    }
}
