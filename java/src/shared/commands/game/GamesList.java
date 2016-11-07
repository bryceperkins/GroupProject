package shared.commands;

import server.handlers.iServerFacade;

public class GamesList extends Command{
    public GamesList() {
        super();
        endpoint = "/games/list";
        method = "GET";
    }

    public void serverExecute(){}
}
