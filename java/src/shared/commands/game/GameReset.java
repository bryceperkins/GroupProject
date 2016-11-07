package shared.commands;


import server.ServerFacade;

public class GameReset extends Command{

    public GameReset() {
        super();
        endpoint = "/game/reset";
        method = "POST";
    }

    public void serverExecute(ServerFacade facade){}
}
