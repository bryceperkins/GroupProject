package shared.commands;

import shared.definitions.*;
import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class YearOfPlenty extends MoveCommand {
    private ResourceType resource1;
    private ResourceType resource2;
    /**
     *  Gain two resources from the bank
     *
     *  @param resource1 - First resource
     *  @param resource2 - Second resourceL
     *
     *  @pre The resources are in the bank
     *
     *  @post You gained the two specified resources
     */
    public YearOfPlenty(PlayerIndex index, ResourceType resource1, ResourceType resource2){
        super("Year_of_Plenty", index);
        this.resource1 = resource1;
        this.resource2 = resource2;
    };

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.Year_of_Plenty(getIndex(), resource1, resource2);
    }
}
