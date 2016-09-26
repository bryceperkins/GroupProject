package shared.commands;

import shared.communication.*;

public class UserLogin extends Command{
    private transient String endpoint = "/user/login";
    private transient String method = "POST";
    private String username;
    private String password;

    public UserLogin() {};

    public UserLogin(User user) {
        setUsername(user.getUserName());
        setPassword(user.getPassword());
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEndPoint() {
        return this.endpoint;   
    }
}
