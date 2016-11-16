package server.handlers;

import java.net.*;
import java.util.logging.Level;

import shared.commands.UserLogin;
import server.facades.UserFacade;

import com.google.gson.*;
import org.apache.commons.io.*;

import shared.communication.User;

import java.io.*;
import com.sun.net.httpserver.*;

public class LoginHandler extends BaseHandler{
    private int code = 418;
    private String body = "Failed";
    private Gson gson = new Gson();

    /**
     * Handle the Incoming request
     */
    public void handle(HttpExchange request) throws IOException{
        body = IOUtils.toString(request.getRequestBody(), "UTF-8");
        UserFacade facade = new UserFacade();
        body = new Gson().fromJson(body, UserLogin.class).serverExecute(facade);

        if(body.equals("Success")) {
            code = 200;
            request.getResponseHeaders().add("Set-Cookie", "catan.user=" + URLEncoder.encode(gson.toJson(facade.getUser()), "UTF-8") + "; path=/");
            LOGGER.log(Level.INFO, "Setting catan.user cookie"); 
        }
        super.respond(request, code, body);
        LOGGER.log(Level.INFO, "Finished: " + request.getRequestURI()); 
    }
}
