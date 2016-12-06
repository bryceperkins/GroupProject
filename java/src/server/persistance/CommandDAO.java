package server.persistance;

import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;
import server.handlers.iServerCommand;

public interface CommandDAO {
    public void addCommand(int gameid, Command command);
    
    public void clearCommands(int gameId);

    public ArrayList<Command> getCommands(int gameId);
}
