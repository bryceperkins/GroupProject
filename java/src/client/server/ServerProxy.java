package client.server;

import java.util.*;
import java.io.*;
import java.net.*;

import shared.communication.servers.*;

public class ServerProxy {
    private static iServer server;
    
    public ServerProxy() {
        setServer();
    }

    public ServerProxy(String host, String port) {
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
    public String execute(iCommand command)
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

    public iServer getServer() {
        return this.server;
    }
}

