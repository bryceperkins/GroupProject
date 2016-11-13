package shared.commands;

import shared.communication.*;
import server.handlers.iServerFacade;
import server.facades.GameFacade;

public class GamesLoad extends Command{
    private String name;

    public GamesLoad(FileName name) {
        super();
        endpoint = "/games/load";
        method = "POST";

        this.name = name.toString();
    }

    public String serverExecute(iServerFacade f){
        return "";
    }
}
