package shared.model;

import java.util.*;
import java.io.Serializable;

/**
 * Contains all chat messages and their sources
 */

public class Chat implements Serializable{
	
	private List<MessageLine> lines;
	
	public Chat(){
		lines = new ArrayList<MessageLine>();
	}
	/**
	 * Adds a on object containing a message and its source to Chat.
	 * @param message_line an object containing a message and its source
	 * @pre message_line must contain a source and message
	 * @post The message and its source will be stored in Chat
	 */
	public void createMessage(MessageLine message_line){
		lines.add(message_line);
	}

    public List<MessageLine> getLines() {
        return lines;
    }

    public void setLines(List<MessageLine> lines) {
		this.lines = lines;
	}
}
