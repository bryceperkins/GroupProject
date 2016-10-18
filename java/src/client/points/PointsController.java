package client.points;

import client.base.*;
import client.*;
import client.model.player.*;
import client.model.*;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

    private GameManager manager = GameManager.getInstance();
	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		initFromModel();
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

	private void initFromModel() {
        if(manager.getActivePlayer() != null){
            int victoryPoints = manager.getActivePlayer().getVictoryPoints();
            getPointsView().setPoints(victoryPoints);
        }
	}

	public void setEndGameWinner(){//probably pass in winner's ID or have some way of getting it
		//will need some way to check if current player is the winner
		Game game = manager.getActiveGame();
		//getFinishedView().setWinner(game.getPlayer(game.getWinner()).getName());
		getFinishedView().showModal();
	}

}

