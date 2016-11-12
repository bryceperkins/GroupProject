package server.handlers;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import server.handlers.iServerCommand;
import server.handlers.iServerFacade;
import shared.communication.User;

import com.sun.net.httpserver.*;

import org.apache.commons.io.*;
import com.google.gson.*;

public abstract class BaseHandler implements HttpHandler{
    protected static Logger LOGGER = Logger.getLogger(BaseHandler.class.getName());
    
    /**
     * @return The game the request is associated with
     */
    public int getGame(){
        return 0;
    }
    
    /**
     * @return The user the request is associated with
     */
    public User getUser(){
        return new User();
    }
    
    protected void respond(HttpExchange request, int code, String body) throws IOException{
        if (body.equals("Success") || body.equals("Failed")){
            request.getResponseHeaders().set("Content-Type", "text/plain");
        } else {
            Gson gson = new Gson();
            body = gson.toJson(body);
            request.getResponseHeaders().set("Content-Type", "application/json");
        }
        request.sendResponseHeaders(code, 0);       
        OutputStream os = request.getResponseBody();
        os.write(body.getBytes());
        request.getResponseBody().close();
        os.close();
    }

    private void parseCookies(Map headers){
        Iterator it = headers.entrySet().iterator();
        URLDecoder d = new URLDecoder();
        while (it.hasNext()){
            Map.Entry header = (Map.Entry)it.next();
            System.out.println(header.getKey() + " = " + d.decode(header.getValue().toString()));
        }
    }
}
