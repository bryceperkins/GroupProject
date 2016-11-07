package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

import server.servers.*;

/**
 *  This is the Server.  Nothing special or magical takes places here.  Sets up
 *  the handlers and the default handler.  If a port is specified on the command
 *  line it uses it.
 */

public class Server {
    private iServer server;

    public static void main(String[] args) throws Exception {
        int port = 8081;
        
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println("Server running on http://localhost:" + port);
        server.start();
    }
    
    /**
     *  @param server - The type of server that should be used.
     */
    public void setServer(iServer server){

    }
}
