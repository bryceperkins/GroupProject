package server.servers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;


public class HttpServer implements iServer {
    public void start(){
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/user/login", new LoginHandler());
        server.setExecutor(null); // creates a default executor
    }
}
