package shared.communication;
/**
 * A container for a User.
 */
public class User{
    private String name;
    private String password;
    private int playerID;

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
        return this.name;
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
    
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return this.playerID;
    }

}
