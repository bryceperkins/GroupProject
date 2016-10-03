package client.model;

import java.util.List;

/**
 * Contains all log messages and their sources
 */

public class Log{

    private List<MessageLine> lines;

    public List<MessageLine> getLines() {
        return lines;
    }

    public void setLines(List<MessageLine> lines) {
        this.lines = lines;
    }
}
