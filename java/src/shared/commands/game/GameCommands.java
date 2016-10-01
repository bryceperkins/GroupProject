package shared.commands;

import java.util.*;


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
}
