package server.persistance;

import java.util.List;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;

public interface iPlugin {
    public String getName();
    
    public void addUser(User user);
    
    public List<User> getUsers();
    
    public List<Game> getGames();

    public void clearGames();
    
    public void addCommand(Command command);
    
    public void clearCommands(int gameId);

    public void getCommands(int gameId);
}
