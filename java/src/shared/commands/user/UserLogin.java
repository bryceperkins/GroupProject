package shared.commands;

import shared.communication.*;

public class UserLogin extends Command{
    private String username;
    private String password;

    public UserLogin(String name, String password) {
        super();
        endpoint = "/user/login";
        method = "POST";

        User tmp = new User(name, password);
        this.username = tmp.getUserName();
        this.password = tmp.getPassword();
    }

    public void serverExecute(){}
}
