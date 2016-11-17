package server.handlers;

import java.net.*;
import java.util.logging.Level;

import shared.commands.GamesJoin;
import server.facades.GamesFacade;

import com.google.gson.*;
import org.apache.commons.io.*;

import java.io.*;
import com.sun.net.httpserver.*;

public class JoinHandler extends BaseHandler{
    private int code = 418;
    private String body = "Failed";
    private Gson gson = new Gson();

    /**
     * Handle the Incoming request
     */
    public void handle(HttpExchange request) throws IOException{ 
        super.parseCookies(request);
        if(super.getUser() == null ){
            LOGGER.log(Level.SEVERE, "User not logged in");
        }
        else {
            body = IOUtils.toString(request.getRequestBody(), "UTF-8");
            GamesFacade facade = new GamesFacade(getUser());
            body = new Gson().fromJson(body, GamesJoin.class).serverExecute(facade);

            if(!body.equals("Failed")) {
                code = 200;
                request.getResponseHeaders().add("Set-Cookie", "catan.game=" + facade.getUser().getGameID() + "; path=/");
                LOGGER.log(Level.INFO, "Setting catan.game cookie"); 
            }
        }
        respond(request, code, body);
        LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    }
}
