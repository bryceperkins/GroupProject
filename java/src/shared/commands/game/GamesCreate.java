package shared.commands;

import server.ServerFacade;

public class GamesCreate extends Command{
    private String name;
    private boolean randomTiles;
    private boolean randomPorts;
    private boolean randomNumbers;

    public GamesCreate(String name, boolean tiles, boolean ports, boolean numbers) {
        super();
        endpoint = "/games/create";
        method = "POST";

        this.name = name;
        this.randomTiles = tiles;
        this.randomPorts = ports;
        this.randomNumbers = numbers;
    }

    public void serverExecute(ServerFacade facade){}
}
