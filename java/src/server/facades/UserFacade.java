package server.facades;

import server.handlers.iServerFacade;
import shared.communication.User;
import shared.model.Game;
import shared.model.GameManager;


public class UserFacade extends BaseFacade{
    private GameManager manager = GameManager.getInstance();
    /**
     * Logs caller into the server and sets their catan.user HTTP cookie
     * @pre username is not null
     * @pre password is not null
     * @post server returns HTTP 200, SUCCESS in body
     * @param username
     * @param password
     **/
    public String login(String username, String password){
        String success = "Failed";
        User user = new User(username, password); 
        int answer = manager.login(user);
        if(answer >= 0){
            success = "Success"; 
            user.setPlayerID(answer);
            setUser(user);
        }
        return success;
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
    public String register(String username, String password){
        String success = "Failed";
        User user = manager.register(new User(username, password)); 
        if(user != null){
            setUser(user);
            System.out.println("Register: " + username + " " + password);
            success = "Success"; 
        }
        return success;
    }
}
