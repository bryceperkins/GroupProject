package shared.commands;

import shared.communication.*;
import server.facades.UserFacade;
import server.handlers.iServerFacade;

public class UserRegister extends Command{
    private String username;
    private String password;

    public UserRegister(String name, String password) {
        super();
        endpoint = "/user/register";
        method = "POST";

        User tmp = new User(name, password);
        this.username = tmp.getUserName();
        this.password = tmp.getPassword();
    }

    public String serverExecute(iServerFacade f){
        UserFacade facade = (UserFacade) f;
        return facade.register(this.username, this.password);
    }
}
