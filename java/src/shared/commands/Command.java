package shared.commands;

import client.server.iCommand;
import server.ServerFacade;

public abstract class Command implements iCommand{
    transient String endpoint;
    transient String method;
    ServerFacade facade;

    public Command(){};

    public String getEndPoint(){
        return endpoint;   
    }

    public String getMethod(){
        return method;
    }
    public void execute(){};

    public void setFacade(ServerFacade facade){}
}
