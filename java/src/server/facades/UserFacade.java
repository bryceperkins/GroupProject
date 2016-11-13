package server.facades;

import server.handlers.iServerFacade;
import shared.communication.User;
import shared.model.Game;


public class UserFacade extends BaseFacade{

    public UserFacade(Game game, User user){
        super(game,user);
    }




    /**
     * Logs caller into the server and sets their catan.user HTTP cookie
     * @pre username is not null
     * @pre password is not null
     * @post server returns HTTP 200, SUCCESS in body
     * @param username
     * @param password
     **/
    public void login(String username, String password){

    }

    /**
     * creates a new user account
     * @pre username is not null
     * @pre password is not null
     * @post server returns HTTP 200, SUCCESS in body
     * @post HTTP response headers set the catan.user cookie to contain the identity of the logged in player
     * @param username
     * @param password
     **/
    public void register(String username, String password){

    }
}
