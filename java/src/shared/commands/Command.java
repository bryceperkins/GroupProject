public class Command {
    private MoveType type;
    private PlayerIndex playerIndex;

    public Command(MoveType type, PlayerIndex playerIndex);

    /**
     * Send the command to the server for execution
     */
    public void execute(){};
    /**
     * Execute the command on the server
     */
    public void serverExecute(){};
}
