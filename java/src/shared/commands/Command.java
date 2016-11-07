package shared.commands;

import client.server.iCommand;

public abstract class Command implements iCommand{
    transient String endpoint;
    transient String method;

    public Command(){};

    public String getEndPoint(){
        return endpoint;   
    }

    public String getMethod(){
        return method;
    }
    public void execute(){};
}
