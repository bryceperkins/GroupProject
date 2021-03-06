package server.handlers;

import java.util.logging.Level;
import shared.commands.GamesList;
import server.facades.GamesFacade;

import com.google.gson.*;
import org.apache.commons.io.*;

import java.io.*;
import com.sun.net.httpserver.*;

public class ListHandler extends BaseHandler{
    private int code = 418;
    private String body = "Failed";

    /**
     * Handle the Incoming request
     */
    public void handle(HttpExchange request) throws IOException{
        super.parseCookies(request);
        if(super.getUser() == null ){
            LOGGER.log(Level.SEVERE, "User not logged in");
        }
        else {
            body = new GamesList().serverExecute(new GamesFacade(getUser()));

            if(!body.equals("Failed")) {
                code = 200;
            }
        }
        super.respond(request, code, body);
        //LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    }
}
