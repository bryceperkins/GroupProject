package server.persistance;

import java.util.*;
import java.io.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;
import server.persistance.iPlugin;
import com.google.gson.*;
import org.apache.commons.io.*;


public class FilePlugin extends BasePlugin implements iPlugin{
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
    
    public void addUser(User user){
        HashMap<String, User> users = getUsers();
        users.put(user.getUserName(), user);

        try {
            FileWriter writer = new FileWriter(user_file);
            writer.write(gson.toJson(users));
            writer.close();
        } catch (IOException e){
            System.out.println("Could not write to file: " + user_file.toString());
        }
    }
    
    public HashMap<String, User> getUsers(){
        user_file = getFile(data_dir, "users.json");
        HashMap<String, User> users = new HashMap();
        if (user_file.length() != 0){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(user_file));
                users = gson.fromJson(reader, HashMap.class);
            } catch (FileNotFoundException e){
                System.out.println("File not found: " + user_file.toString());
            }
        }
        System.out.println(users.toString());
        return users;
    }

    public void addGame(Game game){
        File game_dir = getDir("" + game.getId());
        File game_file = getFile(game_dir, games_file);
        try {
            FileWriter writer = new FileWriter(game_file);
            writer.write(gson.toJson(game));
            writer.close();
        } catch (IOException e){
            System.out.println("Could not write to file: " + game_file.toString());
        }

    }
    
    public ArrayList<Game> getGames(){
        ArrayList<Game> games  = new ArrayList<Game>();
        for(File file: data_dir.listFiles()){ 
            if (file.isDirectory()){ 
                File game_file = getFile(file, games_file);
                if (game_file.length() != 0){
                    try{
                        BufferedReader reader = new BufferedReader(new FileReader(game_file));
                        games = gson.fromJson(reader, ArrayList.class);
                    } catch (FileNotFoundException e){
                        System.out.println("File not found: " + game_file.toString());
                    }
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
    
    public void addCommand(int gameId, Command command){
        ArrayList<Command> commands = getCommands(gameId);
        File game_dir = getDir("" + gameId);
        File c_file = getFile(game_dir, commands_file);
        commands.add(command);
        System.out.println(commands);

        try {
            FileWriter writer = new FileWriter(c_file);
            writer.write(gson.toJson(commands));
            writer.close();
        } catch (IOException e){
            System.out.println("Could not write to file: " + c_file.toString());
        }
    }
    
    public void clearCommands(int gameId){
        File game_dir = getDir("" + gameId);
        File commands = getFile(game_dir, commands_file);
        commands.delete();
    }

    public ArrayList<Command> getCommands(int gameId){
        File game_dir = getDir("" + gameId);
        File command_file = getFile(game_dir, commands_file);
        ArrayList<Command> commands = new ArrayList();
        if (command_file.length() != 0){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(command_file));
                commands = gson.fromJson(reader, ArrayList.class);
            } catch (FileNotFoundException e){
                System.out.println("File not found: " + command_file.toString());
            }
        }
        return commands;
    }
}
