package client.model;

import java.util.List;

/**
 * Contains all chat messages and their sources
 */

public class Chat{
	
	private List<MessageLine> lines;
	
	/**
	 * Adds a on object containing a message and its source to Chat.
	 * @param message_line an object containing a message and its source
	 * @pre message_line must contain a source and message
	 * @post The message and its source will be stored in Chat
	 */
	public void createMessage(MessageLine message_line){}

	public void setLines(List<MessageLine> lines) {
		this.lines = lines;
	}

}
