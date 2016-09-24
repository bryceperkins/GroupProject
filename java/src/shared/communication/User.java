package shared.communication;
/**
 * A container for a User.
 */
public class User{
    private UserName name;
    private Password password;
    private int playerID;

    public User() {}
    /**
     * @param name - the users name used to log in to the server
     * @param password - the users password used to log in to the server
     *
     */
    public User(UserName name, Password password) {
        this.name = name;
        this.password = password;
    }
    
    public void setUserName(UserName name) {
        this.name = name;
    }
    
    public UserName getUserName() {
        return this.name;
    }
    
    public void setPassword(Password password) {
        this.password = password;
    }

    public Password getPassword() {
        return this.password;
    }
    
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return this.playerID;
    }

}
