package server.persistance;

import java.util.*;
import java.io.*;

import shared.model.Game;
import shared.communication.User;
import shared.commands.Command;
import java.sql.*;
import java.io.File;
import com.google.gson.*;

import server.handlers.iServerCommand;

public class SQLitePlugin extends BasePlugin implements iPlugin, CommandDAO, UserDAO, GameDAO{

    private Database db;
    private PreparedStatement stmt;
    private ResultSet rs;
    private Gson gson = new Gson();
    
    public SQLitePlugin(){
        super("sqlite");
        try {
            db = new Database();
            createTables();
        } catch (SQLException e) {
            System.out.println("Uh oh ... sql problems");
        }
    }

    public GameDAO getGameDAO(){
        return this;
    }

    public CommandDAO getCommandDAO(){
        return this;
    }

    public UserDAO getUserDAO(){
        return this;
    }
    
    /**
     * Enters user information into database via sqlite query
     * argument is the information to be inputted based on whats inside of a User object
     * 
     * @param user user information to be added to SQLite database
     */
    public void addUser(User user){
        try {
            String query = "insert or replace into USER (id, username, password) values (?, ?, ?)";

            stmt = db.getConnection().prepareStatement(query);
            stmt.setInt(1, user.getPlayerID());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getPassword());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Could not insert" + user.toString() + ". Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
        }
    }
    
    /**
     * clears all information from database
     */
    public void clearAll(){
        try {
            Statement stmt = db.getConnection().createStatement();
            String query = "DELETE from USER";
            stmt.executeUpdate(query);
            
            query = "DELETE from COMMAND";
            stmt.executeUpdate(query);
            
            query = "DELETE from GAME";
            stmt.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.println("Could not delete users. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
        }

    }
    
    /**
     * fetches user information, returns hashmap object with username as key that maps to a user object
     *  
     *  @return HashMap of user information mapped to user objects
     */
    public HashMap<String, User> getUsers(){
        HashMap<String, User> users  = new HashMap<String, User>();
        try {
            Statement stmt = db.getConnection().createStatement();
            rs = stmt.executeQuery( "SELECT * FROM USER;" );
            while ( rs.next() ) {
                User user = new User();
                user.setPlayerID(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.put(user.getUserName(), user);
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get users. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
            db.safeClose(rs);
            return users;
        }
    }
    
    /**
     * retreives all games loaded in sqlite database, returns list of all games
     * 
     * @return list of game objects stored in sqlite database 
     */
    public List<Game> getGames(){
        List<Game> games  = new ArrayList<Game>();
        try {
            Statement stmt = db.getConnection().createStatement();
            rs = stmt.executeQuery( "SELECT * FROM GAME;" );
            while ( rs.next() ) {
                Game game = (Game) readIt(rs.getBinaryStream("game"));
                games.add(game);
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get Games. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
            db.safeClose(rs);
        }
        Collections.sort(games, new Comparator<Game>(){
            public int compare(Game g1, Game g2){
                return (g1.getId() - g2.getId());
            }
        });
        return games;
    }
    
    /**
     * adds game infomration to database, this method forms a BLOB string from game object parameter and inserts it as a query
     * 
     * @param game information object
     */
    public void addGame(Game game){
        try {
            String query = "insert or replace into GAME (gameid, game) values (?, ?)";

            stmt = db.getConnection().prepareStatement(query);
            stmt.setInt(1, game.getId());
            stmt.setBytes(2, blobIt(game));
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Could not write game. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
        }
    }

    /**
     * clears all games from SQLite database
     * 
     */
    public void clearGames(){
        try {
            Statement stmt = db.getConnection().createStatement();
            String query = "DELETE from GAME";
            stmt.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.println("Could not delete games. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
        }
    }
    
    /**
     * add command to database along with the game it's associated with. 
     * this method will create a BLOB string form the command object paramter
     * 
     * @param gameid - the game index the command is associated with
     * @param command - the command object to be inserted into the database 
     */
    public void addCommand(int gameid, Command command){
        try {
            String query = "insert into COMMAND (gameid, command) values (?, ?)";

            stmt = db.getConnection().prepareStatement(query);
            stmt.setInt(1, gameid);
            stmt.setBytes(2, blobIt(command));
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Could not write game. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
        }

    }
    
    /**
     * clears all commands from the database that are associated with the gameid parameter
     * 
     * @param gameid int associated with game index 
     */
    public void clearCommands(int gameid){
        try {
            String query = "DELETE from COMMAND where gameid = ?";
            stmt = db.getConnection().prepareStatement(query);
            stmt.setInt(1, gameid);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Could not delete commands. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
        }
    }

    /**
     * retreives all commands from database that are associated with a specific game id
     * 
     * @param gameid the game index associated with the commands the user wishes to retreive
     * @return list of all commands associated with gameid parameter
     */
    public List<Command> getCommands(int gameid){
        List<Command> commands  = new ArrayList<Command>();
        try {
            String query = "SELECT * FROM COMMAND where gameid = ?";
            stmt = db.getConnection().prepareStatement(query);
            stmt.setInt(1, gameid);

            rs = stmt.executeQuery();
            while ( rs.next() ) {
                Command command = (Command) readIt(rs.getBinaryStream("command"));
                commands.add(command);
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get Commands. Error: " + e); 
        }
        finally {
            db.safeClose(stmt);
            db.safeClose(rs);
        }
        return commands;
    }

    /**
     * this method creates all tables if they do not already exist in the database  
     */
    private void createTables(){
        try {
            Statement stmt = db.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS USER (id INT PRIMARY KEY, username TEXT, password TEXT)";
            stmt.executeUpdate(query);
            
            query = "CREATE TABLE IF NOT EXISTS GAME (gameid int PRIMARY KEY, game BLOB)";
            stmt.executeUpdate(query);
            
            query = "CREATE TABLE IF NOT EXISTS COMMAND (gameid int, command BLOB)";
            stmt.executeUpdate(query);
        }

        catch (SQLException e) {
            System.out.println("Could not createTables. Error: " + e); 
        }

        finally {
            db.safeClose(stmt);
        }

    }
    
    /**
     * this method returns a SQLite blob of parameter o
     * 
     * @param o java object to be created into a binary large object
     * @return byte array information representation of parameter o
     */
    public byte[] blobIt(Object o){
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bout);
            oos.writeObject(o);
            oos.flush();
            oos.close();
            return bout.toByteArray();
        } catch (IOException e){
            System.out.println("Could not blob it Exception " + e);
            return null;
        }
    }
    
    /** this method transforms database BLOB into program object
     * 
     * @param i inputstream to gather information for objects from
     * @return this method returns a base java object class 
     */
    public Object readIt(InputStream i){
        Object o = null;
        try{
            ObjectInputStream ois = new ObjectInputStream(i);
            o =  ois.readObject();
            ois.close();
        } catch (ClassNotFoundException|IOException e){
            System.out.println("Could not read it Exception:" + e);
        }
        return o;
    }
    


    /**
     * The class used to interact with the Database
     * Opens a connection to the database, provides methods for
     * closing the connections, statements, result sets.
     * 
     * Can only be accessed by the other objects in the Database class.
     */
    class Database {
        private final String DATABASE_DIRECTORY = "data";
        private final String DATABASE_FILE = "catan.sqlite";
        private final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY + File.separator + DATABASE_FILE;
        private Connection connection;

        Database() throws SQLException {
            try {
                final String driver = "org.sqlite.JDBC";
                Class.forName(driver);
                this.connection = DriverManager.getConnection(DATABASE_URL);
            } catch(ClassNotFoundException e) {
                System.out.println(e);
            }
        }   

        Connection getConnection() {
            return connection;
        }   

        void safeClose(Connection conn) {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                }
            }
        }   
        
        void safeClose(Statement stmt) {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                }
            }
        }   
        
        void safeClose(PreparedStatement stmt) {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                }
            }
        }   
        
        void safeClose(ResultSet rs) {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                }
            }
        }   
    }

}
