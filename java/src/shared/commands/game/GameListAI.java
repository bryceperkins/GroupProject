package shared.commands;

public class GameListAI extends Command{
    public GameListAI() {
        super();
        endpoint = "/game/listAI";
        method = "GET";
    }

    public void serverExecute(){}
}
