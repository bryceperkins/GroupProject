package client.join;

import java.util.*;
import shared.definitions.*;
import client.base.*;
import client.data.*;
import client.model.*;
import shared.commands.*;
import com.google.gson.*;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {
    private int required = 4;
    private boolean full = false;
    private GameManager manager = GameManager.getInstance();
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
        this.active = true;
        Gson gson = new Gson();
        this.manager.addObserver(this);
        manager.getPoller().setCommand(new GameModel());
        String response = this.manager.getServer().execute(new GameListAI());
        String[] ais = gson.fromJson(response, String[].class);
        getView().setAIChoices(ais);
	}

	@Override
	public void addAI() {
        this.manager.getServer().execute(new GameAddAI(AIType.valueOf(getView().getSelectedAI())));
		getView().closeModal();
	}

    public void update(Observable ob, Object o){
        Game game = this.manager.getActiveGame();
        PlayerInfo[] players = new PlayerInfo[game.getPlayers().size()];

        for(int i=0; i < game.getPlayers().size(); i++){
            players[i] = game.getPlayers().get(i).toPlayerInfo();
        }

        getView().setPlayers(players);
        if(this.active){
            getView().showModal();
        }
    }
}

