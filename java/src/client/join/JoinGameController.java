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
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
    private GameManager manager;
	
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

    public void update(){
        JsonParser parser = new JsonParser();

        String response = this.manager.getServer().execute(new GamesList());
        JsonArray gamesArray = parser.parse(response).getAsJsonArray();

        for (JsonElement e: gamesArray){
            System.out.println(e.toString());
            manager.processGame(e.toString());
        }

        int gameSize = manager.getGames().size();
        GameInfo[] games = new GameInfo[gameSize];
        List<Game> mGames = manager.getGames();

        for (int i=0; i < gameSize; i++){
            Game game = mGames.get(i);
            games[i] = game.toGameInfo();
        }

        getJoinGameView().setGames(games, manager.getCurrentPlayerInfo());
    }

	@Override
	public void start() {
        update();
        getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
	 	
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void startJoinGame(GameInfo game) {

		getSelectColorView().showModal();
    }

	@Override
	public void cancelJoinGame() {
	
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		
		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();
	}

    public void setManager(GameManager manager){
        this.manager = manager;
    }

}
