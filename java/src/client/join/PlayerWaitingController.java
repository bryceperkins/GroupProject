package client.join;

import java.util.*;
import shared.definitions.*;
import client.base.*;
import client.data.*;
import client.model.*;
import client.model.player.*;
import shared.commands.*;
import shared.deserializers.*;
import com.google.gson.*;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {
    private GameManager manager = GameManager.getInstance();
    private Game game;
    private boolean active = false;

	public PlayerWaitingController(IPlayerWaitingView view) {
		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {
		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
        Gson gson = new Gson();
        this.active = true;
        this.manager.addObserver(this);
        update(null, null);

        String response = this.manager.getServer().execute(new GameListAI());
        String[] ais = gson.fromJson(response, String[].class);
        getView().setAIChoices(ais);
        getView().showModal();
        if(this.game != null && this.game.canBeginGame()){
            startGame();
        }
	}

	@Override
	public void addAI() {
        String aitype = getView().getSelectedAI();
        this.manager.getServer().execute(new GameAddAI(AIType.valueOf(aitype)));
	}

    public void update(Observable ob, Object o){
        this.game = manager.getGame(manager.getServer().getServer().getDetails().getGameID());
        int size = this.game.getPlayers().size();
        PlayerInfo[] players = new PlayerInfo[size];

        for(int i=0; i < size; i++){
            Player p = this.game.getPlayers().get(i);
            players[i] = p.toPlayerInfo();
        }
        getView().setPlayers(players);
        getView().showModal();
        if(this.game.canBeginGame()){
            startGame();
        }
    }

    public void startGame(){
        manager.deleteObserver(this);
        manager.setActiveGame(manager.getServer().getServer().getDetails().getGameID());
        manager.getPoller().setCommand(new GameModel());
        manager.processGame(manager.getServer().execute(new GameModel()));
        getView().closeModal();
    }
}

