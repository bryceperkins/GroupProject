package server.persistance;

import server.persistance.iPlugin;

import shared.model.Game;
import shared.commands.Command;

public abstract class BasePlugin implements iPlugin{
    String name;

    public BasePlugin(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
