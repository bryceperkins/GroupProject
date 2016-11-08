package client.communication;

import java.util.*;
import client.base.*;

import shared.model.*;
import shared.model.player.*;

/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	private GameManager manager;

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		manager = GameManager.getInstance();
		manager.addObserver(this);
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	public void update(Observable ob, Object o){
		if (manager.getActiveGame() != null){
			Game game = manager.getActiveGame();
			Log log = game.getLog();
			List<MessageLine> lines = log.getLines();
			List<LogEntry> log_entries = new ArrayList<LogEntry>();
			for (int i = 0; i < lines.size(); i++){
				MessageLine line = lines.get(i);
				Player player = game.getPlayerByName(line.getSource());
				LogEntry entry = new LogEntry(player.getColor(),line.getMessage());
				log_entries.add(entry);
			}
			getView().setEntries(log_entries);
		}
	}
	
	private void initFromModel() {
		if (manager.getActiveGame() != null){
			Game game = manager.getActiveGame();
			Log log = game.getLog();
			List<MessageLine> lines = log.getLines();
			List<LogEntry> log_entries = new ArrayList<LogEntry>();
			for (int i = 0; i < lines.size(); i++){
				MessageLine line = lines.get(i);
				Player player = game.getPlayerByName(line.getSource());
				LogEntry entry = new LogEntry(player.getColor(),line.getMessage());
				log_entries.add(entry);
			}
			getView().setEntries(log_entries);
		}
	}
	
}

