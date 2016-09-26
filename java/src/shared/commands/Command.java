package shared.commands;

public abstract class Command {
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
