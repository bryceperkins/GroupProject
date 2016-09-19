package shared.commands;

import shared.definitions.*;

public class YearOfPlenty extends DevCardCommand {
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
    public YearOfPlenty(ResourceType resource1, ResourceType resource2){};
}
