package shared.model;

import java.util.*;

/**
 * Contains all log messages and their sources
 */

public class Log{

    private List<MessageLine> lines;

	public Log(){
		lines = new ArrayList<MessageLine>();
	}
	
    public List<MessageLine> getLines() {
        return lines;
    }

    public void setLines(List<MessageLine> lines) {
        this.lines = lines;
    }
}
