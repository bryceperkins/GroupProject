package shared.commands;

import client.server.iCommand;
import server.handlers.iServerCommand;
import java.io.Serializable;

public abstract class Command implements iCommand, iServerCommand, Serializable{
    protected transient static final long serialVersionUID = 1L;
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
