package shared.commands;

import shared.definitions.*;

public class YearOfPlenty extends MoveCommand {
    private String resource1;
    private String resource2;
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
    public YearOfPlenty(int index, ResourceType resource1, ResourceType resource2){
        super("Year_of_Plenty", index);
        this.resource1 = resource1.toString();
        this.resource2 = resource2.toString();
    };
}
