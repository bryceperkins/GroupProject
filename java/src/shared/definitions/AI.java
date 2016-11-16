package shared.definitions;

import shared.model.Game;
import shared.model.GameManager;
import shared.model.PlayerIndex;

import java.util.Observable;
import java.util.Observer;

public class AI implements Observer {

    GameManager manager = GameManager.getInstance();
    PlayerIndex playerIndex;
    Game game;

    public AI(Game game, PlayerIndex playerIndex) {
        this.playerIndex = playerIndex;
        this.game = game;

        manager.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (game.getTurnTracker().getCurrentTurn() != playerIndex) {
            return;
        }

        // TODO: handle AI turn
    }

    @Override
    public int hashCode() {
        return 7 * playerIndex.getIndex() + game.getId();
    }
}
