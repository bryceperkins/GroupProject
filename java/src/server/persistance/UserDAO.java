package server.persistance;

import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;

public interface UserDAO {
    public void addUser(User user);
    
    public HashMap<String, User> getUsers();
}
