package client.server;

import shared.communication.*;

public interface iServer {
    public String submit(iCommand command);
    public int getResponseCode();
    public User getDetails();
}
