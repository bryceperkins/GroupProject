package shared.commands;

import client.model.PlayerIndex;
import server.ServerFacade;

public class RollNumber extends shared.commands.MoveCommand {
    private int number;

    public RollNumber(PlayerIndex index, int number){
        super("rollNumber", index);
        this.number = number;
    };

    public void serverExecute(ServerFacade facade){}
}
