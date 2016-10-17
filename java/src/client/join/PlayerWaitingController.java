package client.join;

import java.util.*;
import shared.definitions.*;
import client.base.*;
import client.data.*;
import client.model.*;
import client.model.player.*;
import shared.commands.*;
import com.google.gson.*;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {
    private int required = 4;
    private boolean full = false;
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
        this.game = this.manager.getActiveGame();
        this.manager.addObserver(this);
        String response = this.manager.getServer().execute(new GameListAI());
        String[] ais = gson.fromJson(response, String[].class);
        getView().setAIChoices(ais);
        getView().showModal();
        update(null, null);
        if(this.game.canBeginGame()){
            startGame();
        }
	}

	@Override
	public void addAI() {
        this.manager.getServer().execute(new GameAddAI(AIType.valueOf(getView().getSelectedAI())));
        if(this.game.canBeginGame()){
            startGame();
        }
	}

    public void update(Observable ob, Object o){
        this.game = this.manager.getActiveGame();
        PlayerInfo[] players = new PlayerInfo[this.game.getPlayers().size()];

        for(int i=0; i < this.game.getPlayers().size(); i++){
            Player p = this.game.getPlayers().get(i);
            players[i] = p.toPlayerInfo();
        }

        getView().setPlayers(players);
        if(this.active){
            getView().showModal();
        }
        if(this.game.canBeginGame()){
            startGame();
        }
    }

    public void startGame(){
        this.manager.processGame(this.manager.getServer().execute(new GameModel()));
        this.active = false;
        this.manager.getPoller().setCommand(new GameModel());
        getView().closeModal();
    }
}

