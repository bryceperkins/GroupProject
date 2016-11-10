package server.handlers;

import shared.communication.User;
import com.sun.net.httpserver.*;

public abstract class BaseHandler implements HttpHandler{
    /**
     * Set the facade the handler should interact with
     */
    public void setFacade(iServerFacade facade){ }
    /**
     * @return The game the request is associated with
     */
    public int getGame(){
        return 0;
    }
    /**
     * @return The user the request is associated with
     */
    public User getUser(){
        return new User();
    }
    private void processCommand(String json){ }
    private void parseCookies(){ }
}
