package shared.commands;

import shared.communication.*;

public class GamesLoad extends Command{
    private String name;

    public GamesLoad(FileName name) {
        super();
        endpoint = "/games/load";
        method = "POST";

        this.name = name.toString();
    }

    public void serverExecute(){}
}
