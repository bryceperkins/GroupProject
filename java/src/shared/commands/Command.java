package shared.commands;

import client.server.iCommand;
import server.handlers.iServerCommand;

public abstract class Command implements iCommand, iServerCommand{
    transient String endpoint;
    transient String method;

    public Command(){};

    public String getEndPoint(){
        return endpoint;   
    }

    public String getMethod(){
        return method;
    }
}
