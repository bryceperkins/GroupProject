package server.handlers;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

import server.handlers.iServerCommand;
import server.handlers.iServerFacade;
import shared.communication.User;

import com.sun.net.httpserver.*;

import org.apache.commons.io.*;
import com.google.gson.*;

public abstract class BaseHandler implements HttpHandler{
    protected static Logger LOGGER = Logger.getLogger(BaseHandler.class.getName());
    private User user = null;
    
    /**
     * @return The user the request is associated with
     */
    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    protected void respond(HttpExchange request, int code, String body) throws IOException{
        if (body.equals("Success") || body.equals("Failed")){
            request.getResponseHeaders().set("Content-Type", "text/plain");
        } else {
            request.getResponseHeaders().set("Content-Type", "application/json");
        }
        request.sendResponseHeaders(code, 0);       
        OutputStream os = request.getResponseBody();
        os.write(body.getBytes());
        request.getResponseBody().close();
        os.close();
    }

    protected void parseCookies(HttpExchange request){
        Map<String, List<String>> headers = request.getRequestHeaders();
        Iterator it = headers.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, List<String>> header = (Map.Entry)it.next();
            if(header.getKey().equals("Cookie")){
                for (String s: header.getValue()){
                    String[] cookies = s.split(";");
                    for (String a: cookies){
                        HttpCookie cookie = HttpCookie.parse(a).get(0);
                        if (cookie.getName().equals("catan.user")){
                            LOGGER.log(Level.INFO, "Processing catan.user " + cookie.getValue());
                            this.user = new Gson().fromJson(URLDecoder.decode(cookie.getValue()), User.class);
                        }
                        if (cookie.getName().equals("catan.game")){
                            LOGGER.log(Level.INFO, "Processing catan.game" + cookie.getValue());
                            this.user.setGameID(Integer.parseInt(URLDecoder.decode(cookie.getValue())));
                        }
                    }
                }
            }
        }
    }
}
