package server.servers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;


public class Mock implements iServer {
    public void start() throws IOException{
        int port = 8081;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/moves", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException{
                
            }
        });
        server.setExecutor(null); // creates a default executor
    }
}
