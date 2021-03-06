package shared.model;

import java.io.Serializable;

/**
 * Handles a trade offer between two players
 */
public class TradeOffer implements Serializable{

    private PlayerIndex sender;
    private PlayerIndex receiver;
    private ResourceList offer;

	public TradeOffer(){}
	
	public TradeOffer(int sender, int reciever, ResourceList offer){
		this.sender = PlayerIndex.valueOf(sender);
		this.receiver = PlayerIndex.valueOf(reciever);
		this.offer = offer;
	}
	
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
