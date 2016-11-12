package shared.commands;

import shared.model.PlayerIndex;

public class BuyDevCard extends MoveCommand{

    public BuyDevCard(PlayerIndex index){
        super("buyDevCard", index);
    };

    public String serverExecute(){
        return "";
    }
}
