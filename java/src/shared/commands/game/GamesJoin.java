package shared.commands;

import shared.definitions.CatanColor;

import server.facades.GamesFacade;

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

    public String serverExecute(){
        return new GamesFacade().join(this.id, this.color);
    }
}
