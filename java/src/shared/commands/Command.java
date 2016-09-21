package shared.commands;

import shared.definitions.*;
import client.model.*;

public class Command {
    private MoveType type;
    private PlayerIndex playerIndex;

    public Command(){};

    /**
     * Send the command to the server for execution
     */
    public String execute(){};
    /**
     * Execute the command on the server
     */
    public String serverExecute(){};
}
