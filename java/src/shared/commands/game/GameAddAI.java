package shared.commands;

import shared.definitions.AIType;


public class GameAddAI extends Command{
    private AIType AIType;

    public GameAddAI(AIType aitype) {
        super();
        endpoint = "/game/addai";
        method = "POST";

        this.AIType = aitype;
    }
}
