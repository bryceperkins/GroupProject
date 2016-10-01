package shared.commands;

public abstract class Command {
    transient String endpoint;
    transient String method;

    /**
     *  command
     *
     *  All commands inherit from this abstract class.  It provides and enforces that all commands have an endpoint and a method.
     */
    public Command(){};

    public String getEndPoint(){
        return endpoint;   
    }

    public String getMethod(){
        return method;
    }
}
