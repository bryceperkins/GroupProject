package shared.model;

/**
 * Contains a message body and the message's source.
 */

public class MessageLine {
	
	private String message;
	private String source;

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
