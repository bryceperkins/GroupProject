package shared.commands;

import shared.definitions.CatanColor;

public class GamesJoin extends Command{
    private int id;
    private String color;

    public GamesJoin(int id, CatanColor c) {
        super();
        endpoint = "/games/join";
        method = "POST";

        this.id = id;
        this.color = c.getValue();
    }
}
