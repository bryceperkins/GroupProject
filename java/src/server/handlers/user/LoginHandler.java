package server.handlers;


import java.util.logging.Level;
import shared.commands.UserLogin;

import server.handlers.iServerCommand;
import server.facades.UserFacade;

import shared.communication.User;

import java.io.*;
import com.google.gson.*;
import org.apache.commons.io.*;
import com.sun.net.httpserver.*;

public class LoginHandler extends BaseHandler{
    private int code = 418;
    private String body = "Failed";
    private Gson gson = new Gson();

    /**
     * Handle the Incoming request
     */
    public void handle(HttpExchange request) throws IOException{
        try{
            // TESTING
            User user = new User("test", "test");

            body = IOUtils.toString(request.getRequestBody(), "UTF-8");
            UserLogin command = new Gson().fromJson(body, UserLogin.class);     

            System.out.println("here");
            body = command.serverExecute();
            System.out.println("here3");
            if(body.equals("Success")) {
                code = 200;
                request.getResponseHeaders().add("Set-Cookie", "catan.user=" + gson.toJson(user));
            }
        } catch (IOException e) { 
            System.out.println("Err: " + e);
        } 
        finally {
            super.respond(request, code, body);
            LOGGER.log(Level.SEVERE, "Finished: " + request.getRequestURI()); 
        }
    }
}
