package shared.commands;

import server.facades.GamesFacade;

public class GamesList extends Command{
    public GamesList() {
        super();
        endpoint = "/games/list";
        method = "GET";
    }

    public String serverExecute(){
        return new GamesFacade().list();
    }
}
