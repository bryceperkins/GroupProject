package shared.commands;

import server.ServerFacade;
import shared.definitions.*;
import client.model.PlayerIndex;

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

    public void serverExecute(ServerFacade facade){}
}
