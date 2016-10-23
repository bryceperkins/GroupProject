package client.communication;

import client.base.*;
import shared.commands.*;
import client.communication.*;
import client.model.*;
import client.model.player.*;
import client.server.*;
import java.util.*;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {
	
	private GameManager manager;
	
	public ChatController(IChatView view) {
		
		super(view);
		manager = GameManager.getInstance();
		manager.addObserver(this);
	}

	public void update(Observable ob, Object o){
		if (manager.getActiveGame() != null){
			Game game = manager.getActiveGame();
		Chat chat = game.getChat();
		List<MessageLine> lines = chat.getLines();
		List<LogEntry> chat_entries = new ArrayList<LogEntry>();
		for (int i = 0; i < lines.size(); i++){
			MessageLine line = lines.get(i);
			Player player = game.getPlayerByName(line.getSource());
			LogEntry entry = new LogEntry(player.getColor(),line.getMessage());
			chat_entries.add(entry);
		}
		getView().setEntries(chat_entries);
		}
	}
	
	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		Player player = manager.getActivePlayer();
		SendChat send_chat = new SendChat(player.getPlayerIndex(), message);
		ServerProxy server = manager.getServer();
		server.execute(send_chat);
	}

}

