package server.persistance;

import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;

public interface iPlugin {
    public String getName();

    public void clearAll();

    public GameDAO getGameDAO();
    
    public UserDAO getUserDAO();
    
    public CommandDAO getCommandDAO();
    
}
