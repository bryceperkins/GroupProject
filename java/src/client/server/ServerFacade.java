package client.server;

import shared.communication.*;

public class ServerFacade {
    private User user;
    private Server server;

    public ServerFacade(Server server){
        this.user = new User();
    }
    /**
     * call
     *
     * Use the provided command to make a call to the server.
     *
     * @param command - The crafted command
     * @return String - the JSON response 
     */
    public String call(Command command){
        return "";
    }
}
