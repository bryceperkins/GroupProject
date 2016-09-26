package shared.communication.servers;

import java.util.*;
import java.io.*;
import java.net.*;
import com.google.gson.*;

import client.server.*;
import shared.commands.*;

/**
 *  Handles all Post requests from the Client to the Server
 */
public class Mock implements iServer {
    public Mock(){ }

    public String submit(Command command) {
        Gson gson = new Gson();
        String json = gson.toJson(command);
        System.out.println(json);
        return gson.toJson(command);
    }
}
