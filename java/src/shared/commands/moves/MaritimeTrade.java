package shared.commands;

import shared.definitions.*;
import shared.model.PlayerIndex;

public class MaritimeTrade extends MoveCommand{
    private ResourceType inputResource;
    private ResourceType outputResource;
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
    public MaritimeTrade(PlayerIndex index, int ratio, ResourceType inputResource, ResourceType outputResource){
        super("maritimeTrade", index);
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    };
}
