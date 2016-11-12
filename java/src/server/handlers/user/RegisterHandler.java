package server.handlers;

import java.util.logging.Level;
import shared.commands.UserRegister;

import server.handlers.iServerCommand;
import server.facades.UserFacade;

import shared.communication.User;

import java.io.*;
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
        try{
            // TESTING
            User user = new User("test", "test");

            body = IOUtils.toString(request.getRequestBody(), "UTF-8");
            UserRegister command = new Gson().fromJson(body, UserRegister.class);     

            body = command.serverExecute();
            if (body.equals("Success")) {
                code = 200;
                request.getResponseHeaders().add("Set-Cookie", "catan.user=" + gson.toJson(user));
            }
        } catch (IOException e) { } finally {
            super.respond(request, code, body);
            LOGGER.log(Level.SEVERE, "Finished: " + request.getRequestURI()); 
        }
    }
}
