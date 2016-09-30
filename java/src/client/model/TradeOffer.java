package client.model;

/**
 * Handles a trade offer between two players
 */
public class TradeOffer {

    private PlayerIndex sender;
    private PlayerIndex reciever;
    private ResourceList offer;

	public PlayerIndex getSender() {
		return sender;
	}

	public PlayerIndex getReciever() {
		return reciever;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setSender(PlayerIndex sender) {
		this.sender = sender;
	}

	public void setReciever(PlayerIndex reciever) {
		this.reciever = reciever;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
    
    

}
