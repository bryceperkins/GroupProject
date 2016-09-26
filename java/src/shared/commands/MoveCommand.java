package shared.commands;

import shared.communication.*;

public abstract class MoveCommand extends Command {
    private String endpoint = "/move/";

    public MoveCommand(User user){
        
    };

    public String execute(){
        return "";
    };
    
    public String serverExecute(){
        return "";
    };
}
