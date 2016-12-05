package server.persistance;

import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;

public interface iPlugin {
    public String getName();
    
    public void addUser(User user);
    
    public HashMap<String, User> getUsers();
    
    public ArrayList<Game> getGames();

    public void clearGames();
    
    public void addCommand(int gameid, Command command);
    
    public void clearCommands(int gameId);

    public ArrayList<Command> getCommands(int gameId);
}
