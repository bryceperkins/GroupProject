package shared.communication;
/**
 * A container for a User.
 */
import client.data.*;

public class User{
    private String name;
    private String password;
    private int playerID;
    private PlayerInfo player;

    public User() {}
    /**
     * @param name - the users name used to log in to the server
     * @param password - the users password used to log in to the server
         *
     */
    public User(String name, String password) {
        setUserName(name);
        setPassword(password);
        this.player = new PlayerInfo();
        this.player.setName(getUserName());
    }
    
    public void setUserName(String name) {
        try {
            this.name = new UserName(name).toString();
        } catch (Exception err) {
            System.err.println("Unable to setUserName: " + err.getMessage());
            this.name = null;
        }
    }

    public String getUserName() {
        return this.name.toString();
    }
    
    public void setPassword(String password) {
        try {
            this.password = new Password(password).toString();
        } catch (Exception err) {
            System.err.println("Unable to setPassword: " + err.getMessage());
            this.password = null;
        }
    }

    public  String getPassword() {
        return this.password;
    }

    public int getPlayerID(){
        return this.playerID;
    }
    
    public void setPlayerID(int id){
        this.playerID = id;
    }

    public PlayerInfo getPlayerInfo(){
        return this.player;
    }
}
