
package client.model;

import client.model.Game;
import client.model.player.Player;

public class MessageLine {
	
	private String message;
	private String source;

/**
 * Contains a message body and the message's source.
 */
	
	public MessageLine(String source, String message){
		this.source = source;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public String getSource() {
		return source;
	}

}