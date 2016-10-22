package client.roll;

import client.base.*;
import client.model.*;
import client.model.player.*;
import shared.commands.RollNumber;

import java.util.Random;

import static com.sun.tools.javac.util.Assert.error;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;
	private GameManager manager = GameManager.getInstance();

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {
		super(view);
		
		setResultView(resultView);
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
		Random rand = new Random();
		final int rollResult = rand.nextInt(11) + 2;

		getRollView().closeModal();

		String result = manager.getServer().execute(new RollNumber(manager.getCurrentPlayerInfo().getPlayerIndex(), rollResult));
		if (!result.equals("Failed")) {
			resultView.setRollValue(rollResult);
			resultView.showModal();
		} else {
			error("failed to join game");
		}

	}

}

