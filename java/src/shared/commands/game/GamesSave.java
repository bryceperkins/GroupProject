package shared.commands;

import shared.communication.*;

public class GamesSave extends Command{
    private int id;
    private String name;

    public GamesSave(int id, FileName name) {
        super();
        endpoint = "/games/save";
        method = "POST";

        this.id = id;
        this.name = name.toString();
    }
}
