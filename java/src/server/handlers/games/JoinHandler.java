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
        body = IOUtils.toString(request.getRequestBody(), "UTF-8");
        body = new Gson().fromJson(body, GamesJoin.class).serverExecute(new GamesFacade(getUser()));

        if(!body.equals("Failed")) {
            code = 200;
            request.getResponseHeaders().add("Set-Cookie", "catan.game=" + super.getUser().getGameID() + "; path=/");
        }
        respond(request, code, body);
        LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    }
}
