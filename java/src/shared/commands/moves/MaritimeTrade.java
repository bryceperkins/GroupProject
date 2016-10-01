package shared.commands;

import shared.definitions.*;

public class MaritimeTrade extends MoveCommand{
    private String inputResource;
    private String outputResource;
    private int ratio;
    /**
     *  Perform a trade with the Bank
     *
     *  @param ratio - cards required for trade
     *  @param inputResource - resource you are sending
     *  @param outputResource - resource you are expecting
     *
     *  @pre You have enough of the specified inputResource
     *  @pre for ratios less than for you have the correct port
     *  
     *  @post trade has been performed
     */
    public MaritimeTrade(int index, int ratio, ResourceType inputResource, ResourceType outputResource){
        super("maritimeTrade", index);
        this.ratio = ratio;
        this.inputResource = inputResource.toString();
        this.outputResource = outputResource.toString();
    };
}
