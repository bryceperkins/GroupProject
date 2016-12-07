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
        return games;
    }

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
