package shared.communication;
/**
 * A container for a User.
 */
import client.data.*;
import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String password;
    private int playerID;
    private transient int gameID;

    public User() {}
    /**
     * @param name - the users name used to log in to the server
     * @param password - the users password used to log in to the server
         *
     */
    public User(String name, String password) {
        setUserName(name);
        setPassword(password);
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

    public void setGameID(int id){
        this.gameID=id;
    }

    public int getGameID(){
        return this.gameID;
    }

    public PlayerInfo toPlayerInfo(){
        PlayerInfo player = new PlayerInfo();
        player.setId(this.playerID);
        player.setName(this.name);
        return player;
    }

    public boolean equals(User other){
        return this.getUserName().equals(other.getUserName()) && this.getPassword().equals(other.getPassword());
    }
}
