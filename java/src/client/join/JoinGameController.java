package client.join;

import com.google.gson.*;

import java.util.*;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import client.model.*;
import client.model.player.*;
import shared.commands.*;
import shared.deserializers.*;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
    private GameManager manager = GameManager.getInstance();
    private GameInfo selected;
    private boolean active = false;
	
	
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		manager.addObserver(this);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

    public void update(Observable ob, Object o){
        GameInfo[] games = new GameInfo[manager.getGames().size()];
        for (int i=0; i < manager.getGames().size(); i++){
            games[i] = manager.getGames().get(i).toGameInfo();
        }
        getJoinGameView().setGames(games, manager.getCurrentPlayerInfo());
        if(this.active){
            getJoinGameView().showModal();
        }
    }

	@Override
	public void start() {
        this.active = true;
        this.manager.getPoller().setCommand(new GamesList());
	}

	@Override
	public void startCreateNewGame() {
        this.active = false;
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
        this.active = true;
		getNewGameView().closeModal();
	}
    
    private void error(String message, IAction action) {
        this.messageView.setTitle("Error");
        this.messageView.setMessage(message);
        this.messageView.setAction(action);
        this.messageView.showModal();
    }

	@Override
	public void createNewGame() {
        JsonParser parser = new JsonParser();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GamesCreateDeserializer());
        Gson gson = gsonBuilder.create();

        String response = manager.getServer().execute(new GamesList());
        JsonArray gamesArray = parser.parse(response).getAsJsonArray();

        String title = getNewGameView().getTitle();
        boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
        boolean randomHexes = getNewGameView().getRandomlyPlaceHexes();
        boolean randomPorts = getNewGameView().getUseRandomPorts();

        response = manager.getServer().execute(new GamesCreate(title, randomHexes, randomPorts, randomNumbers));
        if (response != "Failed"){
            Game game = gson.fromJson(response, Game.class);
            manager.getServer().execute(new GamesJoin(game.getId(), CatanColor.RED));
        }
        this.active = true;
		getNewGameView().closeModal();
	}

	@Override
	public void startJoinGame(GameInfo game) {
        this.active = false;
        this.selected = game;
        for(CatanColor color: CatanColor.values()){
            getSelectColorView().setColorEnabled(color, true);
        }
        for(PlayerInfo player: game.getPlayers()){
            if(player.getName().equals(manager.getCurrentPlayerInfo().getName())){
				manager.getCurrentPlayerInfo().setPlayerIndex(game.getPlayers().indexOf(player));
                continue;
            }
            getSelectColorView().setColorEnabled(player.getColor(), false);
        }
		getSelectColorView().showModal();
    }

	@Override
	public void cancelJoinGame() {
        this.active = true;
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
        String response = manager.getServer().execute(new GamesJoin(this.selected.getId(), color));
        manager.setActiveGame(this.selected.getId());
        getSelectColorView().closeModal();
        if (!response.equals("Failed")){
            getJoinGameView().closeModal();
            joinAction.execute();
        }
        else{
            error("Failed to join Game", new IAction() {
                @Override
                public void execute()
                {
                    start();
                }
            });
                
        }
	}
}
