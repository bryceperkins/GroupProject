package shared.commands;

import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class GameReset extends Command{

    public GameReset() {
        super();
        endpoint = "/game/reset";
        method = "POST";
    }

    public String serverExecute(iServerFacade f){
        return "";
    }
}
