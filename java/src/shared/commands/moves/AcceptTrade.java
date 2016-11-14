package shared.commands;

import shared.model.*;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class AcceptTrade extends MoveCommand{
    private Boolean willAccept;
    /**
     *  Accept the proposed trade.
     *
     *  @param willAccept - accept the trade, True/False
     *
     *  @pre trade has been offered
     *  @pre have resources
     *
     *  @post resources exchanged
     *  @post trade offere removed
     */
    public AcceptTrade(PlayerIndex index, Boolean accept){
        super("acceptTrade", index);
        this.willAccept = accept;
    };

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.acceptTrade(getIndex(), willAccept);
    }
}
