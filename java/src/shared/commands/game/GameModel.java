package shared.commands;

public class GameModel extends Command{
    public GameModel() {
        super();
        endpoint = "/game/model";
        method = "GET";
    }
    public GameModel(int version) {
        super();
        method = "GET";

        StringBuilder tmp = new StringBuilder();
        tmp.append("/game/model?version=");
        tmp.append(version);
        endpoint = tmp.toString();
    }
}
