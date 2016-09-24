package shared.commands;

import client.server.*;

public abstract class PlayingCommand implements Command {
    /**
     *  Similar to a regular command, however, 'Playing' commands require it to be your turn
     *  and the clients status is 'Playing'
     *
     */
    public PlayingCommand(){};
    /**
     *  @throws TurnException
     */
    public String execute(){
        return "";
    };
    
    public String serverExecute(){
        return "";
    };
}
