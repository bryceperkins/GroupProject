package server.persistance;

import java.util.*;
import java.io.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.*;
import server.persistance.iPlugin;
import server.handlers.iServerCommand;
import com.google.gson.*;
import org.apache.commons.io.*;


public class FilePlugin extends BasePlugin implements iPlugin, GameDAO, UserDAO, CommandDAO{
    File data_dir;
    File user_file;
    String commands_file = "commands.json";
    String games_file = "game.json";
    Gson gson = new Gson();
    
    public FilePlugin(){
        super("file");
        data_dir = getDir("data");
        user_file = getFile(data_dir, "users.json");
        System.out.println("Data dir: " + data_dir.toString());
        System.out.println("Users: " + user_file.toString());
    }

    public GameDAO getGameDAO(){
        return this;
    }

    public UserDAO getUserDAO(){
        return this;
    }

    public CommandDAO getCommandDAO(){
        return this;
    }

    private File getDir(String name){
        if (data_dir != null){
            name = data_dir.toString() + System.getProperty("file.separator") + name;
        }
        File tmp = new File(name);
        if (!tmp.exists()) {
            System.out.println("Creating directory: " + name);

            try{
                tmp.mkdir();
            } 
            catch(SecurityException se){
                System.out.println("Could not create dir " + name);
            }        
        }
        return tmp;
    }
    
    private File getFile(File dir, String name){
        name = dir.toString() + System.getProperty("file.separator") + name;
        File tmp = new File(name);
        try {
            if(!tmp.exists())
                tmp.createNewFile();
        } catch (IOException e){
            System.out.println("Could not create file: " + user_file.toString());
        }
        return tmp;
    }

    public void clearAll(){
        for(File file: data_dir.listFiles()) 
            file.delete();
    }
    
    public void addUser(User user){
        HashMap<String, User> users = getUsers();
        users.put(user.getUserName(), user);
        
        writeIt(user_file, users);
    }
    
    public HashMap<String, User> getUsers(){
        user_file = getFile(data_dir, "users.json");
        HashMap<String, User> users = new HashMap();
        if (user_file.length() != 0){
            users = (HashMap<String, User>) readIt(user_file);
        }
        System.out.println(users.toString());
        return users;
    }

    public void addGame(Game game){
        File game_dir = getDir("" + game.getId());
        File game_file = getFile(game_dir, games_file);
        writeIt(game_file, game);
    }
    
    public List<Game> getGames(){
        List<Game> games  = new ArrayList<Game>();
        for(File file: data_dir.listFiles()){ 
            if (file.isDirectory()){ 
                File game_file = getFile(file, games_file);
                if (game_file.length() != 0){
                    games.add((Game) readIt(game_file));
                }
            }
        }
        return games;
    }

    public void clearGames(){
        for(File file: data_dir.listFiles()) 
            if (file.isDirectory()) 
                file.delete();
    }

    public void writeIt(File f, Object o){
        try {
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(o);
            oos.close();
            fout.close();
        } catch (IOException e){
            System.out.println("Could not write to file: " + f.toString());
        }
    }

    public Object readIt(File f){
        Object o = null;
        try{
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fin);
            o =  ois.readObject();
            ois.close();
            fin.close();
        } catch (ClassNotFoundException|IOException e){
            System.out.println("File not found: " +f.toString() + "Exception: " + e);
        }
        return o;
    }
    
    public void addCommand(int gameId, Command command){
        List<Command> commands = getCommands(gameId);
        File game_dir = getDir("" + gameId);
        File c_file = getFile(game_dir, commands_file);
        commands.add(command);
        System.out.println(commands);
        writeIt(c_file, commands);
    }
    
    public void clearCommands(int gameId){
        File game_dir = getDir("" + gameId);
        File commands = getFile(game_dir, commands_file);
        commands.delete();
    }

    public List<Command> getCommands(int gameId){
        File game_dir = getDir("" + gameId);
        File c_file = getFile(game_dir, commands_file);
        List<Command> commands = new ArrayList<Command>();
        if (c_file.length() != 0){
            commands = (ArrayList<Command>) readIt(c_file);
        }
        return commands;
    }
}
