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
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        options.addOption("p", true, "Set the plugin type to use");
        options.addOption("t", true, "How often should the server sync");
        options.addOption("h", false, "Show the this help menu");
        options.addOption("r", false, "Reset persistance");

        formatter.printHelp("catan-server", options);

        if (args.length > 0){
            System.out.println("Check the help (-h) for command line options");
        }

        try {
            cmd = parser.parse( options, args);
        } catch (Exception e){
            formatter.printHelp("catan-server", options);
            return;
        }

        if(cmd.hasOption("h")){
            formatter.printHelp("catan-server", options);
            return;
        }

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

        persist.setPlugin(plugin_type);
        if(cmd.hasOption("r")){
            persist.reset();
        }
        manager.setCheckpoint(checkpoint);
        manager.loadAll();

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
