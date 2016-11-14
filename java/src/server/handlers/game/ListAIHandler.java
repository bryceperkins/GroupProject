package server.handlers;

import java.util.logging.Level;
import shared.commands.GameListAI;
import server.facades.GameFacade;

import com.google.gson.*;
import org.apache.commons.io.*;

import java.io.*;
import com.sun.net.httpserver.*;

public class ListAIHandler extends BaseHandler{
    private String body = "Failed";
    private int code = 418;
    /**
     * Handle the Incoming request
     */
    public void handle(HttpExchange request) throws IOException{ 
        super.parseCookies(request);
        if(super.getUser() == null ){
            LOGGER.log(Level.SEVERE, "User not logged in");
        }
        else {
            body = new GameListAI().serverExecute(new GameFacade(getUser()));

            if(!body.equals("Failed")) {
                code = 200;
            }
        }
        super.respond(request, code, body);
        LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    }
}
