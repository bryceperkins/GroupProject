package server.handlers;

import java.util.logging.Level;
import shared.commands.GamesCreate;

import com.google.gson.*;
import org.apache.commons.io.*;

import shared.communication.User;

import java.io.*;
import com.sun.net.httpserver.*;

public class CreateHandler extends BaseHandler{
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
            body = new Gson().fromJson(body, GamesCreate.class).serverExecute();     
            
            if(!body.equals("Failed")) {
                code = 200;
            }
        }
        super.respond(request, code, body);
        LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    }
}
