package shared.commands;

import client.server.iCommand;
import server.handlers.iServerCommand;
import server.handlers.iServerFacade;

public abstract class Command implements iCommand, iServerCommand{
    transient String endpoint;
    transient String method;
    iServerFacade facade;

    public Command(){};

    public String getEndPoint(){
        return endpoint;   
    }

    public String getMethod(){
        return method;
    }
    public void execute(){};
    
    public void serverExecute(){};

    public void setFacade(iServerFacade facade){}
}
