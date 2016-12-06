package shared.model;

import java.util.*;
import java.io.Serializable;

/**
 * Contains all log messages and their sources
 */

public class Log implements Serializable{

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

    public void addLine(MessageLine line) {
        lines.add(line);
    }
}
