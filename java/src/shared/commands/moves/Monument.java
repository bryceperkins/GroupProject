package shared.commands;

import shared.model.PlayerIndex;

public class Monument extends MoveCommand{
    /**
     *  Play the monument DevCard
     *
     *  @pre Playing monument cards will give you enough victory points to win the game
     *
     *  @post You gain a victory point
     */
    public Monument(PlayerIndex index){
        super("Monument", index);
    };

    public String serverExecute(){
        return "";
    }
}
