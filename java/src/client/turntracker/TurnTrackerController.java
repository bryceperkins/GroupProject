package client.turntracker;

import shared.model.GameManager;
import shared.model.ModelProxy;
import shared.model.PlayerIndex;
import shared.model.TurnTracker;
import shared.model.player.Player;
import client.points.GameFinishedView;
import shared.commands.FinishTurn;
import shared.definitions.CatanColor;
import client.base.*;

import java.util.Observable;
import java.util.Observer;

import static com.sun.tools.javac.util.Assert.error;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	GameManager manager = GameManager.getInstance();

    private final int NUM_PLAYERS = 4;
	private boolean setup = false;

	public TurnTrackerController(ITurnTrackerView view) {
		super(view);
		
		manager.addObserver(this);
	}
	
	@Override
	public ITurnTrackerView getView() {
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		String response = manager.getServer().execute(new FinishTurn(manager.getActivePlayerIndex()));

		if (response.equals("Failed")) {
			error("Could not finish turn");
		} else {
			getView().updateGameState("Waiting for other Players", false);
		}
	}

	private void updatePlayers() {
		initPlayers();
        for (int i = 0; i < NUM_PLAYERS; i++) {
			PlayerIndex index = PlayerIndex.valueOf(i);
			int points = ModelProxy.getPlayerPoints(index);
			boolean highlight = index.equals(ModelProxy.getCurrentTurn());
			boolean longestRoad = index.equals(ModelProxy.getLongestRoadOwner());
			boolean largestArmy = index.equals(ModelProxy.getLargestArmyOwner());
            getView().updatePlayer(i, points, highlight, largestArmy, longestRoad);
		}
	}

	private void initPlayers() {
		for (int i = 0; i < NUM_PLAYERS; i++) {
			PlayerIndex index = PlayerIndex.valueOf(i);
			String name = ModelProxy.getPlayerName(index);
			CatanColor color = ModelProxy.getPlayerColor(index);
			getView().initializePlayer(i, name, color);
		}
	}
	
	private void initFromModel() {
        PlayerIndex playerIndex = manager.getActivePlayerIndex();
        CatanColor color;
		if (!playerIndex.equals(PlayerIndex.None)) {
			color = ModelProxy.getPlayerColor(playerIndex);
		} else {
			color = CatanColor.WHITE;
		}
		getView().setLocalPlayerColor(color);

		initPlayers();
        setup = true;

		update(null, null);
	}

	/**
	 * @pre A game is in progress
	 * @pre There is an active player
	 * @pre The model is fully setup (has a TurnTracker)
	 */
	private void updateGameState() {
		PlayerIndex currentTurn = ModelProxy.getCurrentTurn();
		if (currentTurn != null) {
			TurnTracker.GameStatus status = ModelProxy.getGameStatus();
			if (currentTurn.equals(manager.getActivePlayerIndex())) {
				getView().updateGameState(status.getPrompt(), status.isButtonEnabled());
			} else {
				getView().updateGameState("Waiting for other Players", false);
			}
			getView().setStatusPanelColored(status.getPrompt().equals("Finish Turn"));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (manager.getActiveGame() != null && manager.getActiveGame().getPlayers().size() == 4) {
            Player p = manager.getActiveGame().getPlayerByName(manager.getCurrentPlayerInfo().getName());
			if (setup) {
                updateGameState();
				updatePlayers();

				PlayerIndex winnerInd = manager.getActiveGame().getWinner();
				if (!winnerInd.equals(PlayerIndex.None)) {
					GameFinishedView gameFinishedView = new GameFinishedView();
					gameFinishedView.setWinner(ModelProxy.getPlayerName(winnerInd), winnerInd.equals(manager.getActivePlayerIndex()));
					gameFinishedView.showModal();
				}

                if (manager.getActivePlayerIndex() != null){
                    getView().setLocalPlayerColor(p.getColor());
                }
			} else {
				initFromModel();
			}
		}
	}
}

