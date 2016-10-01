package shared.commands;

import client.server.*;

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
    public AcceptTrade(int index, Boolean accept){
        super("acceptTrade", index);
        this.willAccept = accept;
    };
}