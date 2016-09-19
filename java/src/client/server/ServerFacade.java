package client.server;

public class ServerFacade {
    private User user;
    private Server server;

    public ServerFacade(){

    }
    public ServerFacade(Server server){

    }
    /**
     * userLogin
     *
     * Craft the request to login is as the specified user.
     *
     * @param username: the name of the user to login as
     * @param password: the users password
     *
     * @pre username not null
     * @pre password not null
     *
     * @post catan.user cookie is set
     * @return String - the JSON response 
     */
    public String userLogin(Name username, String password){

    }

    /**
     * userRegister
     *
     * Craft the request to register a new user account.
     *
     * @param username: the name of the user to login as
     * @param password: the users password
     *
     * @pre username not null
     * @pre password not null
     * @pre username does not already exist
     *
     * @post catan.user cookie is set
     * @return String - the JSON response 
     */
    public String userRegister(Name username, String password){

    }

    /**
     * gamesList
     *
     * Craft the request to return information about current games on the server
     *
     * @param username: the name of the user to login as
     * @param password: the users password
     *
     * @return String - JSON array containing list of objects
     */
    public String gamesList(){

    }

    /**
     * gamesCreate
     *
     * Create new game on the server
     *
     * @param name: the name of the game
     * @param randomTiles: Boolean
     * @param randomNumbers: Boolean
     * @param randomPorts: Boolean
     *
     * @return String - JSON object describing new game
     */
    public String gamesCreate(){

    }

    /**
     * gamesJoin
     *
     * Join a game on the server
     *
     * @param gameID: Integer - ID of game to join
     * @param color: Color - Players in game color
     *
     * @pre user is logged in
     * @pre player currently in game or game is not full
     * @pre gameID is a valid game
     * @pre color is a valid color
     *
     * @post player is in the game with specified color
     * @post user catan.game cookie is set
     *
     * @return String - JSON server response
     */
    public String gamesJoin(){

    }

    /**
     * gamesSave
     *
     * Save a game on the server
     *
     * @param gameID: Integer - ID of game to join
     * @param filename: Name - Filename on the server
     *
     * @pre gameID is a valid game
     * @pre filename not null or empty and valid
     *
     * @post game is saved on the server
     *
     * @return String - JSON server response
     */
    public String gamesSave(){

    }

    /**
     * gamesLoad
     *
     * Load a game saved on the server
     *
     * @param filename: Name - Filename on the server
     *
     * @pre filename exists on the server
     *
     * @post game is saved on the server
     *
     * @return String - JSON server response
     */
    public String gamesLoad(){

    }

    /**
     * gameModel
     *
     * Fetch the newest model from the Server
     *
     * @param version: Integer version of the Model
     *
     * @pre user is logged in
     * @pre user has joined a game
     *
     * @return String - JSON representation of client
     */
    public String gameModel(int version){

    }

    /**
     * gameReset
     *
     * Clears out the command history of the current game
     *
     * @pre user is logged in
     * @pre user has joined a game
     *
     * @post game history has been cleared
     *
     * @return String - JSON representation of client
     */
    public String gameReset(){

    }

    /**
     * gameCommands
     *
     * Return history of commands that have been performed
     *
     * @pre user is logged in
     * @pre user has joined a game
     *
     * @return String - JSON array of commands that been run on the 
     */
    public String gameCommands(){

    }

    /**
     * gameCommands
     *
     * Executes specified commands on the server
     *
     * @pre user is logged in
     * @pre user has joined a game
     *
     * @post client model has been updated
     *
     * @return String - JSON object representing updated client model
     */
    public String gameCommands(List<Commands> commands){

    }

    /**
     * gameListAI
     *
     * Returns a list of supported AI types
     *
     * @return String - JSON object representing a list of AI types
     */
    public String gameListAI(){

    }

    /**
     * gameAddAI
     *
     * Add an AI player to the current game
     *
     * @pre user has logged in
     * @pre user has joined a game
     * @pre game is not currently full
     * @pre AI type is valid
     *
     * @post AI player has been added to the game
     *
     * @see shared.definitions.AIType
     *
     * @return String - JSON object representing response from server
     */
    public String gameAddAI(){

    }

    /**
     * utilChangeLogLevel
     *
     * Change the log level on the server
     *
     * @pre LogLevel is valid
     *
     * @post Server uses the new LogLevel
     *
     * @see shared.definitions.LogLevel
     *
     * @return String - JSON object representing response from server
     */
    public String utilChangeLogLevel(){

    }

    /**
     * move
     *
     * perform the the specified action with the game
     *
     * @pre User is logged in
     * @pre User is in a game
     *
     * @post Move has been performed
     *
     * @see shared.commands.command
     *
     * @throws InvalidMoveException if move does not have a valid type
     *
     * @return String - JSON object representing response from server
     */
    public String move(Command command){

    }
}
