package server.facades;

import com.google.gson.Gson;
import server.handlers.iServerFacade;
import shared.communication.User;
import shared.model.Game;
import shared.model.GameManager;

public class BaseFacade implements iServerFacade {
    private GameManager manager = GameManager.getInstance();
    private User user;
    private int gameid;

    public BaseFacade(){}

    public BaseFacade(User user){
        this.user = user;
    }

    public Game getGame() {
        if (this.user != null)
            gameid = user.getGameID();
        return manager.getGame(gameid);
    }

    public void setGame(int game) {
        if (this.user != null)
            this.user.setGameID(game);
        this.gameid = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getModel(){

        Gson gson = new Gson();
        String json = gson.toJson(getGame());

        return json;
    }
}
