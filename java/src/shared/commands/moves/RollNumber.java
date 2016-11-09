package shared.commands;

import shared.model.PlayerIndex;

public class RollNumber extends shared.commands.MoveCommand {
    private int number;

    public RollNumber(PlayerIndex index, int number){
        super("rollNumber", index);
        this.number = number;
    };
}
