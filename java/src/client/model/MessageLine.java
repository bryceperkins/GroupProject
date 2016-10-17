package client.model;

/**
 * Contains a message body and the message's source.
 */

public class MessageLine {
	
	private String message;
	private PlayerIndex source;

	public String getMessage() {
		return message;
	}

	public PlayerIndex getSource() {
		return source;
	}

}
