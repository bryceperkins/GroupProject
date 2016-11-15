package server.handlers;

import java.util.logging.Level;
import shared.commands.UserRegister;

import server.handlers.iServerCommand;
import server.facades.UserFacade;

import shared.communication.User;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import com.google.gson.*;
import org.apache.commons.io.*;

public class RegisterHandler extends BaseHandler{
    private int code = 418;
    private String body = "Failed";
    private Gson gson = new Gson();

    /**
     * Handle the Incoming request
     */
    public void handle(HttpExchange request) throws IOException{
        body = IOUtils.toString(request.getRequestBody(), "UTF-8");
        UserFacade facade = new UserFacade();
        body = new Gson().fromJson(body, UserRegister.class).serverExecute(facade);     

        if (body.equals("Success")) {
            code = 200;
            request.getResponseHeaders().add("Set-Cookie", "catan.user=" + URLEncoder.encode(gson.toJson(facade.getUser()), "UTF-8") + "; path=/");
        }
        super.respond(request, code, body);
        LOGGER.log(Level.SEVERE, "Finished: " + request.getRequestURI()); 
    }
}
