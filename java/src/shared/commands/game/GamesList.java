package shared.commands;

import server.facades.GamesFacade;
import server.handlers.iServerFacade;

public class GamesList extends Command{
    public GamesList() {
        super();
        endpoint = "/games/list";
        method = "GET";
    }

    public String serverExecute(iServerFacade f){
        GamesFacade facade = (GamesFacade) f;
        return facade.list();
    }
}
