package shared.commands;

import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class GameListAI extends Command{
    public GameListAI() {
        super();
        endpoint = "/game/listAI";
        method = "GET";
    }

    public String serverExecute(iServerFacade f){
        return "";
    }
}
