package shared.commands;

import shared.definitions.AIType;

import server.handlers.iServerFacade;
import server.facades.GameFacade;


public class GameAddAI extends Command{
    private AIType AIType;

    public GameAddAI(AIType aitype) {
        super();
        endpoint = "/game/addAI";
        method = "POST";

        this.AIType = aitype;
    }

    public String serverExecute(iServerFacade f){
        return "";
    }
}
