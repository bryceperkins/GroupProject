package client.model;

/**
 * Contains a message body and the message's source.
 */

public class MessageLine{
	
	private String message;
	private String source;
	private PlayerIndex player;

	public String getMessage() {
		return message;
	}

	public String getSource() {
		return source;
	}

	public PlayerIndex getPlayer() {
		return player;
	}
}
