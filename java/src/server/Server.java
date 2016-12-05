package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;
import org.apache.commons.cli.*;

import server.persistance.Persistor;
import server.servers.*;

import shared.model.GameManager;

/**
 *  This is the Server.  Nothing special or magical takes places here.  Sets up
 *  the handlers and the default handler.  If a port is specified on the command
 *  line it uses it.
 */

public class Server {
    private static iServer server;
    private static Persistor persist = Persistor.getInstance();
    private static GameManager manager = GameManager.getInstance();

    public static void main(String[] args) throws Exception {
        int port = 8081;
        int checkpoint = 10;
        String plugin_type = "file";

        Options options = new Options();

        options.addOption("p", true, "Set the plugin type to use");
        options.addOption("t", true, "How often should the server sync");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);

        if(cmd.hasOption("t")){
            checkpoint = Integer.parseInt(cmd.getOptionValue("t"));
        } else {
            System.out.println("Using default value of " + checkpoint + " commands per sync");
        }

        if(cmd.hasOption("p")){
            plugin_type = cmd.getOptionValue("p");
        } else {
            System.out.println("Using default plugin " + plugin_type);
        }

        manager.setCheckpoint(checkpoint);
        persist.setPlugin(plugin_type);

        server = new Http();
        System.out.println("Server running on http://localhost:" + port);
        server.start();
    }
    
    /**
     *  @param server - The type of server that should be used.
     */
    public void setServer(iServer server){

    }
}
