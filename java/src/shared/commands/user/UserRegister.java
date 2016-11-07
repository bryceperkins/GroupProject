package shared.commands;

import shared.communication.*;

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

    public void serverExecute(){}
}
