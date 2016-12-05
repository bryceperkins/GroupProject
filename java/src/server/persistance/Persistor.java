package server.persistance;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;
import server.persistance.iPlugin;

public class Persistor {
    private iPlugin plugin;
    private static Persistor INSTANCE;
    private ServiceLoader<iPlugin> loader;

    private Persistor () {
        loader = ServiceLoader.load(iPlugin.class);
    }

    public void setPlugin(String name) throws IOException{
        System.out.println("Setting plugin to " + name);
        File loc = new File("plugins");

        File[] flist = loc.listFiles(new FileFilter() {
            public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
        });
        URL[] urls = new URL[flist.length];
        for (int i = 0; i < flist.length; i++)
            urls[i] = flist[i].toURI().toURL();
        URLClassLoader ucl = new URLClassLoader(urls);

        ServiceLoader<iPlugin> sl = ServiceLoader.load(iPlugin.class, ucl);
        Iterator<iPlugin> plugins = sl.iterator();
        
        boolean found = false;
        while (plugins.hasNext() && !found){
            iPlugin p = plugins.next();
            if (name.equals(p.getName())){
                plugin = p;
                found = true;
            }
        }
        if (found){
            System.out.println("Using plugin: " + plugin.getName());
        }
    }

    public static Persistor getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new Persistor();
        }
        return INSTANCE;
    }
    
    public void addUser(User user) {
        plugin.addUser(user);
    }
    
    public HashMap<String, User> getUsers() {
        return plugin.getUsers();
    }
    
    public ArrayList<Game> getGames(){
        return plugin.getGames();
    }

    public void clearGames() {
        plugin.clearGames();
    }
    
    public void addCommand(int gameid, Command command){
        plugin.addCommand(gameid, command);
    }
    
    public void clearCommands(int gameId){
        plugin.clearCommands(gameId);
    }

    public ArrayList getCommands(int gameId){
        return plugin.getCommands(gameId);
    }
}
