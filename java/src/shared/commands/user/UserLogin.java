package shared.commands;

import shared.communication.*;
import server.facades.UserFacade;

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

    public String serverExecute(){
        return new UserFacade().login(this.username, this.password);
    }
}
