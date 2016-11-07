package shared.commands;

import client.model.PlayerIndex;

public class BuyDevCard extends MoveCommand{

    public BuyDevCard(PlayerIndex index){
        super("buyDevCard", index);
    };

    public void serverExecute(){}
}
