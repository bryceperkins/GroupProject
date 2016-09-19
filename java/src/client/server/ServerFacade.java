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
     * @return Boolean
     */
    public Boolean gamesJoin(){

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
     * @return String 
     */
    public Boolean gamesSave(){

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
     * @return String 
     */
    public Boolean gamesSave(){

    }
}
