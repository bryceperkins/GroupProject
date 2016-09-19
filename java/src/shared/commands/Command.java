package shared.commands;

public class Command {
    private MoveType type;
    private PlayerIndex playerIndex;

    public Command(MoveType type, PlayerIndex playerIndex);

    /**
     * Send the command to the server for execution
     */
    public String execute(){};
    /**
     * Execute the command on the server
     */
    public String serverExecute(){};
}
