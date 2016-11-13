package shared.commands;

import java.util.*;

import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class GameCommands extends Command{
    private List<Command> commands; 

    public GameCommands() {
        super();
        endpoint = "/game/commands";
        method = "GET";
    }

    public GameCommands(List<Command> commands) {
        super();
        endpoint = "/game/commands";
        method = "POST";

        this.commands = commands;
    }
    public String serverExecute(iServerFacade f){
        return "";
    }
}
