package client.model;

/**
 * Handles a trade offer between two players
 */
public class TradeOffer {

    private PlayerIndex sender;
    private PlayerIndex reciever;
    private TradeOfferResourceList offer;

    /**
     * Extension of ResourceList which allows negative resource values
     */
    private class TradeOfferResourceList extends ResourceList {

    }

	public PlayerIndex getSender() {
		return sender;
	}

	public PlayerIndex getReciever() {
		return reciever;
	}

	public TradeOfferResourceList getOffer() {
		return offer;
	}
    
    

}
