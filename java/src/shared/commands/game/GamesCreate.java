package shared.commands;

import server.facades.GamesFacade;
import server.handlers.iServerFacade;

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

    public String serverExecute(iServerFacade f){
        GamesFacade facade = (GamesFacade) f;
        return facade.create(this.name, this.randomTiles, this.randomPorts, this.randomNumbers);
    }
}
