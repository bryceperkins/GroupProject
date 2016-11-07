package shared.commands;

import server.ServerFacade;
import shared.definitions.CatanColor;

public class GamesJoin extends Command{
    private int id;
    private CatanColor color;

    public GamesJoin(int id, CatanColor c) {
        super();
        endpoint = "/games/join";
        method = "POST";

        this.id = id;
        this.color = c;
    }

    public void serverExecute(ServerFacade facade){}
}
