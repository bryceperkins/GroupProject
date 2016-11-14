package shared.commands;

import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class RollNumber extends MoveCommand {
    private int number;

    public RollNumber(PlayerIndex index, int number){
        super("rollNumber", index);
        this.number = number;
    };

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.rollNumber(getIndex(), number);
    }
}
