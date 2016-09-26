package client.server;

import java.util.*;
import java.io.*;
import java.net.*;

import shared.communication.servers.*;
import shared.commands.*;

public class ServerFacade {
    private iServer server;
    
    public ServerFacade() {
        setServer();
    }

    public ServerFacade(String host, String port) {
        setServer(host, port);
    }

    /**
     * execute
     *
     * @param command - The command to be sent to the user
     * 
     * 
     * @see Server
     */
    public String execute(Command command)
    {
        String response = this.server.submit(command);
        return response;
    }
    public void setServer() {
        this.server = new Mock();
    }
    public void setServer(String host, String port) {
        this.server = new HTTP(host, port);
    }
}

