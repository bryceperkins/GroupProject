package shared.commands;

public abstract class Command {
    private transient String endpoint;

    public Command(){};

    public String getEndPoint(){
        return this.endpoint;
    };
}
