package shared.commands;

public class AcceptTrade extends Command{
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
    private Boolean willAccept;
    public AcceptTrade(Boolean willAccept){};
}
