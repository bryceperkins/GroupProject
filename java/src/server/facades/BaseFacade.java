package server.facades;

import com.google.gson.Gson;
import server.handlers.iServerFacade;
import shared.communication.User;
import shared.model.Game;

public class BaseFacade implements iServerFacade {
    private Game game;
    private User user;

    public BaseFacade(Game game, User user){
        this.game = game;
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getModel(){

        Gson gson = new Gson();
        String json = gson.toJson(game);

        return json;
    }
}
