package shared.commands;

import shared.definitions.*;

public class Monopoly extends MoveCommand{
    private String resource;
    /**
     *  Monopoly
     *
     *  Play the monopoly DevCard
     *
     *  @param resource - The resource you are collecting from the other players
     *
     *  @post You gain the amount of specified resource the other players lost
     */
    public Monopoly(int index, ResourceType resource){
        super("Monopoly", index);
        this.resource = resource.toString();
    };
}
