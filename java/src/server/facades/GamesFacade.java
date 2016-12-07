package server.facades;

import com.google.gson.*;
import java.util.*;
import java.util.stream.Collectors;

import shared.model.*;
import shared.model.player.*;
import shared.definitions.CatanColor;
import server.handlers.iServerFacade;
import shared.communication.User;
import shared.model.map.*;
import shared.model.GameManager;

public class GamesFacade extends BaseFacade{
    private GameManager manager = GameManager.getInstance();

    public GamesFacade(User user){
        super(user);
    }


    public String list(){
        return new Gson().toJson(manager.getGames());
    }

    /**
     * Creates a new game on the server
     * @pre name != null
     * @pre randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post 1. A new game with the specified properties has been created
     * @post 2. The server returns an HTTP 200 success response.
     * @post 3. The body contains a JSON object describing the newly created game
     **/
    public String create(String name, boolean tiles, boolean ports, boolean numbers){
		Game game = new Game();
		game.setName(name);
		game.setId(createId());
		shared.model.map.Map map = new shared.model.map.Map(tiles, ports, numbers);
        game.setMap(map);
		manager.addGame(game);
        join(game.getId(), CatanColor.RED);
        return getModel();
    }
	
	public int createId(){
		int id = 0;
		while (manager.getGame(id) != null){
			id++;
		}
		return id;
	}

    /**
     * Adds the player to the specified game and sets their catan.game cookie.
     * @pre 1. The user has previously logged in to the server (i.e., they have a valid catan.user HTTP cookie).
     * @pre 2. The player may join the game because
     * @pre 2.a They are already in the game, OR
     * @pre 2.b There is space in the game to add a new player
     * @pre 3. The specified game ID is valid
     * @pre 4. The specified color is valid (red, green, blue, yellow, puce, brown, white, purple, orange)
     * @post 1. The server returns an HTTP 200 success response with Success in the body.
     * @post 2. The player is in the game with the specified color (i.e. calls to /games/list method will show the player in the game with the chosen color).
     * @post 3. The server response includes the Set­cookie response header setting the catan.game HTTP cookie
     **/
    public String join(int gameid, CatanColor c) {
        String success = "Failed";
        Game game = manager.getGame(gameid);

        if (game == null){
            return "Failed";
        }
        
        for (Player p: game.getPlayers()){
            if (p.getColor() == c && !p.getName().equals(getUser().getUserName())){
                System.out.println(p.getName() + " using " + p.getColor() + " " + getUser().getUserName());
                return "Failed";
            }
        }
        
        if (game.getPlayerByName(getUser().getUserName()) != null) {
            List<Player> players = game.getPlayers();
            for (Player p: players){
                if (p.getName().equals(getUser().getUserName())){
                    p.setColor(c);
                }
            }
            
            manager.getGame(gameid).setPlayers(players);
            setGame(gameid);
            
            success = getModel();
        }
        else if(!game.canBeginGame()){
            List<Player> players = game.getPlayers();
            User user = getUser();
            Player player = new Player(c, user.getUserName(), user.getPlayerID(), PlayerIndex.createPlayerAtIndex(players.size()), user.getPlayerID());
            players.add(player);

            manager.getGame(gameid).setPlayers(players);
            setGame(gameid);

            success = getModel();
        }
        else {
            return "Failed";
        }
        manager.saveGame(game);
        return success;
    }

    /**
     * Save state of game to file
     * @pre 1. The specified game ID is valid
        2. The specified file name is valid (i.e., not null or empty)
     * @post 1. The server returns an HTTP 200 success response with Success in the body.
        2. The current state of the specified game (including its ID) has been saved to the specified file name in the server's saves/ directory
     **/
    public void save(){}

    /**
     * Load a game from a file
     * @pre 1. A previously saved game file with the specified name exists in the server's saves/ directory
     * @post 1. The server returns an HTTP 200 success response with Success in the body.
        2. The game in the specified file has been loaded into the server and its state restored (including its ID).
     **/
    public void load(){}

}
