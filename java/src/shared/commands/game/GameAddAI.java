package shared.commands;

import shared.definitions.AIType;


public class GameAddAI extends Command{
    private AIType AIType;

    public GameAddAI(AIType aitype) {
        super();
        endpoint = "/game/addAI";
        method = "POST";

        this.AIType = aitype;
    }

    public String serverExecute(){
        return "";
    }
}
