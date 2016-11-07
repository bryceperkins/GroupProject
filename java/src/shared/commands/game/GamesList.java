package shared.commands;

public class GamesList extends Command{
    public GamesList() {
        super();
        endpoint = "/games/list";
        method = "GET";
    }
}
