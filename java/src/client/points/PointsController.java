package client.points;

import client.base.*;
import client.*;
import client.model.player.*;
import client.model.*;
import java.util.*;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

    private GameManager manager;
	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		manager = GameManager.getInstance();
		manager.addObserver(this);
		initFromModel();
		setFinishedView(finishedView);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	public void update(Observable ob, Object o){
		Player player = manager.getActivePlayer();
		Game game = manager.getActiveGame();
		if(player != null && game != null){
            int victory_points = player.getVictoryPoints();
			if (game.getWinner() != null && game.getWinner().getIndex() != 0){
				setEndGameWinner();
			}
			getPointsView().setPoints(victory_points);
        }
	}
	
	private void initFromModel() {
		Player player = manager.getActivePlayer();
		Game game = manager.getActiveGame();
		if(player != null && game != null){
            int victory_points = player.getVictoryPoints();
			if (game.getWinner() != null && game.getWinner().getIndex() != 0){
				setEndGameWinner();
			}
			getPointsView().setPoints(victory_points);
        }
	}

	public void setEndGameWinner(){
		Game game = manager.getActiveGame();
		Player player = manager.getActivePlayer();
		if (game != null){
			boolean is_local_player = false;
			if (game.getWinner().equals(player.getPlayerIndex())) is_local_player = true;
			getFinishedView().setWinner(game.getPlayer(game.getWinner()).getName(), is_local_player);
			getFinishedView().showModal();
		}
	}
}

