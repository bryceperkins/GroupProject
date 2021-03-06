package client.roll;

import client.base.*;
import shared.model.*;
import shared.commands.RollNumber;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static com.sun.tools.javac.util.Assert.error;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;
	private GameManager manager = GameManager.getInstance();
    private boolean hasRolled = false;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {
		super(view);
		
		setResultView(resultView);
		manager.addObserver(this);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
        if (this.hasRolled){
            return;
        }
		if (ModelProxy.isPlayerTurn() && ModelProxy.getGameStatus().equals(TurnTracker.GameStatus.Rolling)) {
			System.out.println("rollDice");
			Random rand = new Random();
            int die1 = rand.nextInt(6) + 1;
            int die2 = rand.nextInt(6) + 1;
            int rollResult = die1 + die2;

			//int rollResult = die1 + die2;

			getRollView().closeModal();

			String result = manager.getServer().execute(new RollNumber(manager.getActiveGame().getPlayerByName(manager.getCurrentPlayerInfo().getName()).getPlayerIndex(), rollResult));
			if (!result.equals("Failed")) {
				resultView.setRollValue(rollResult);
				resultView.showModal();
                this.hasRolled = true;
			} else {
				error("failed to join game");
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
        this.hasRolled = false;

		if (ModelProxy.getCurrentTurn() != null && ModelProxy.isPlayerTurn()
				&& ModelProxy.getGameStatus().equals(TurnTracker.GameStatus.Rolling)) {
			System.out.println("in update on RollController");
			getRollView().showModal();
		}
	}
}

