package shared.commands;

import client.model.PlayerIndex;

public class RollNumber extends MoveCommand{
    private int number;

    public RollNumber(PlayerIndex index, int number){
        super("rollNumber", index);
        this.number = number;
    };
}
