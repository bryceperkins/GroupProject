package server.persistance;

import java.util.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;

public interface GameDAO {
    public ArrayList<Game> getGames();

    public void addGame(Game game);

    public void clearGames();
}
