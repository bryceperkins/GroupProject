package shared.model;

import java.util.*;

import shared.definitions.*;
import client.data.*;
import shared.model.map.*;
import shared.model.map.Map;
import shared.model.player.*;
import shared.communication.*;
import shared.commands.*;
import server.persistance.Persistor;
import server.persistance.CommandDAO;
import server.persistance.GameDAO;
import server.facades.MovesFacade;

import org.apache.commons.lang3.tuple.Pair;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Game model class containing all local information provided by
 * the server for a given game.
 */
public class Game implements PostProcessor, Serializable {

    @SerializedName("title")
    private String name;
    private int version;
    private int id;
	private PlayerIndex winner;
    private ResourceList bank;
    private Chat chat;
    private Log log;
    private Map map;
    private List<Player> players;
    private TurnTracker turnTracker;
    private TradeOffer tradeOffer;
    private DevCardList devCardDeck;
    private transient int checkpoint = 10;
    private transient List<Command> recentCommands;
    private transient Persistor persist = Persistor.getInstance();
    private transient GameDAO gd;
    private transient CommandDAO cd;

    public Game(){ 
        //name = "Test";
        version = 1;
        winner = PlayerIndex.None;
        bank = new ResourceList(19, 19, 19, 19, 19);
        chat = new Chat();
        log = new Log();
        map = new Map();
        players = new ArrayList<Player>();
        turnTracker = new TurnTracker();
		devCardDeck = new DevCardList();
        
        devCardDeck.setMonopoly(2);
		devCardDeck.setMonument(5);
		devCardDeck.setRoadBuilding(2);
		devCardDeck.setSoldier(15);
		devCardDeck.setYearOfPlenty(2);	
		
        setUp(checkpoint);
    }

    public void setUp(int checkpoint){
        persist = Persistor.getInstance();
        gd = persist.getGameDAO();
        cd = persist.getCommandDAO();
        this.checkpoint = checkpoint;
        recentCommands = new ArrayList<Command>();
    }

    public void setCheckpoint(int checkpoint){
        this.checkpoint = checkpoint;
    }
    
    public void addCommand(Command c){
        if ((recentCommands.size() % checkpoint) == 0){
            recentCommands.clear();
            cd.clearCommands(id);
            gd.addGame(this);
        }
        recentCommands.add(c);
        cd.addCommand(id, c);
    }

    public void getCommands(){
        for (Player player : players) {
            if (player instanceof AI ) {
                ((AI) player).setUp(id, player.getName());
            }
        }
        MovesFacade m = new MovesFacade();
        m.setGame(id);
        List<Command> tmp = cd.getCommands(id);
        System.out.println(tmp.size() + " recent commands");
        for(Command c: tmp)
            c.serverExecute(m);
    }

    /**
     * @return whether the game is completed
     */
    public boolean gameOver() {
        if (winner == PlayerIndex.None){
            return false;
        }
        return true;
    }

    public DevCardType dealRandomCard()
    {
    	int total;
	int chooser;

	Random randy = new Random();
	List<DevCardType> randomizerList = new ArrayList<DevCardType>();
	DevCardType returnCard;

	for(int i = 0; i < devCardDeck.getMonopoly(); i++)
	{
		randomizerList.add(DevCardType.MONOPOLY);
	}	
	
	for(int i = 0; i < devCardDeck.getMonument(); i++)
	{
		randomizerList.add(DevCardType.MONUMENT);
	}

	for(int i = 0; i < devCardDeck.getSoldier(); i++)
	{
		randomizerList.add(DevCardType.SOLDIER);
	}	

	for(int i = 0; i < devCardDeck.getYearOfPlenty(); i++)
	{
		randomizerList.add(DevCardType.YEAR_OF_PLENTY);
	}		

	for(int i = 0; i < devCardDeck.getRoadBuilding(); i++)
	{
		randomizerList.add(DevCardType.ROAD_BUILD);
	
	}		
	returnCard = randomizerList.get(randy.nextInt(randomizerList.size()));

	devCardDeck.removeCard(returnCard);

	return returnCard;
    }

    /**
     * @return whether the game is ready to begin
     */
    public boolean canBeginGame() {
        if (players.size() < 4){
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getVersion() {
        return version;
    }

    public PlayerIndex getWinner() {
        return winner;
    }

    public void setWinner(PlayerIndex index){
        this.winner = index;
    }
    
	public void setBank(ResourceList bank){
		this.bank = bank;
	}
	
    public ResourceList getBank() {
        return bank;
    }

    public Chat getChat() {
        return chat;
    }
	
	public void setChat(Chat chat){
		this.chat = chat;
	}

    public Log getLog() {
        return log;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
	
    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(PlayerIndex id) {
        return (id.getIndex() < 0 || id.getIndex() > players.size()) ? null : players.get(id.getIndex());
    }
	
	public Player getPlayerByName(String userName) {
		for (int i = 0; i < players.size(); i++){
			if (players.get(i).getName().equals(userName)) return players.get(i);
		}
		return null;
	}

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }
    
    public void setTracker(TurnTracker t) {
        turnTracker = t;
    }

    public State getState(){
        if (getTurnTracker().getStatus() == null)
            return null;

        State state = new State(getTurnTracker().getStatus());
        return state;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }
	
	public void setTradeOffer(TradeOffer offer){
		this.tradeOffer = offer;
	}

    @Override
    public void postDeserializationSetup(Game game) {
        map.postDeserializationSetup(game);
        for (Player player : players) {
            player.postDeserializationSetup(game);
        }
    }

    public GameInfo toGameInfo(){
        GameInfo game = new GameInfo();
        game.setId(getId());
        game.setTitle(getName());
        for(Player player: getPlayers()){
            game.addPlayer(player.toPlayerInfo());
        }
        return game;
    }

}
