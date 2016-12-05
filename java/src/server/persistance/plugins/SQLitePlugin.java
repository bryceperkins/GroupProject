package server.persistance;

import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;

public class SQLitePlugin extends BasePlugin implements iPlugin {
    
    public SQLitePlugin(){
        super("sqlite");
    }
    public void addUser(User user){}
    
    public HashMap<String, User> getUsers(){
        HashMap<String, User> users  = new HashMap<String, User>();
        return users;
    }
    
    public ArrayList<Game> getGames(){
        ArrayList<Game> games  = new ArrayList<Game>();
        return games;
    }

    public void clearGames(){}
    
    public void addCommand(int gameid, Command command){}
    
    public void clearCommands(int gameId){}

    public ArrayList getCommands(int gameId){
        return new ArrayList<Command>();
    }
}
