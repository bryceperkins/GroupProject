package shared.commands;


public class GameAddAI extends Command{
    private String AIType;

    public GameAddAI(String aitype) {
        super();
        endpoint = "/game/addai";
        method = "POST";

        this.AIType = aitype;
    }
}
