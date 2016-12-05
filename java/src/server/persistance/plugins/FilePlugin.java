package server.persistance;

import java.util.List;
import java.util.ArrayList;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;
import server.persistance.iPlugin;


public class FilePlugin extends BasePlugin implements iPlugin{
    
    public FilePlugin(){
        super("file");
    }
    public void addUser(User user){}
    
    public List<User> getUsers(){
        List<User> users  = new ArrayList<User>();
        return users;
    }
    
    public List<Game> getGames(){
        List<Game> games  = new ArrayList<Game>();
        return games;
    }

    public void clearGames(){}
    
    public void addCommand(Command command){}
    
    public void clearCommands(int gameId){}

    public void getCommands(int gameId){}
}
