package shared.commands;


public class GameReset extends Command{

    public GameReset() {
        super();
        endpoint = "/game/reset";
        method = "POST";
    }
}
