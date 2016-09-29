package client.server;

import shared.commands.*;

public interface iServer {
    public String submit(Command command);
    public String getDetails();
}
