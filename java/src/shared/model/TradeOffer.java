package shared.model;

/**
 * Handles a trade offer between two players
 */
public class TradeOffer {

    private PlayerIndex sender;
    private PlayerIndex receiver;
    private ResourceList offer;

	public PlayerIndex getSender() {
		return sender;
	}

	public PlayerIndex getReceiver() {
		return receiver;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setSender(PlayerIndex sender) {
		this.sender = sender;
	}

	public void setReceiver(PlayerIndex receiver) {
		this.receiver = receiver;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
    
    

}
