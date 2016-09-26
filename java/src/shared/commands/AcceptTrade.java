package shared.commands;

import client.server.*;

public class AcceptTrade extends Command{
    private String type = "acceptTrade";
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
    public AcceptTrade(Boolean accept){
        this.willAccept = accept;
    };

    public String execute() {
        return ""; 
    };
    public String serverExecute() {
        return ""; 
    };
}
