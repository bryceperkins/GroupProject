package server.facades;

import com.google.gson.Gson;
import server.handlers.iServerFacade;
import shared.communication.User;
import shared.model.Game;
import shared.model.GameManager;

public class BaseFacade implements iServerFacade {
    private GameManager manager = GameManager.getInstance();
    private User user;
    private Game game;

    public BaseFacade(){}

    public BaseFacade(User user){
        this.user = user;
    }

    public Game getGame() {
        return manager.getGame(user.getGameID());
    }

    public void setGame(int game) {
        this.user.setGameID(game);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getModel(){

        Gson gson = new Gson();
        String json = gson.toJson(user);

        return json;
    }
}
