package server.handlers;

import java.net.*;
import java.util.logging.Level;

import shared.commands.BuildCity;
import server.facades.MovesFacade;

import com.google.gson.*;
import org.apache.commons.io.*;

import java.io.*;
import com.sun.net.httpserver.*;

public class BuildCityHandler extends BaseHandler{
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
            body = new Gson().fromJson(body, BuildCity.class).serverExecute(new MovesFacade(getUser()));

            if(!body.equals("Failed")) {
                code = 200;
            }
        }
        super.respond(request, code, body);
        LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    } 
}
