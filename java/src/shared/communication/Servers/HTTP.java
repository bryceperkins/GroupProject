package shared.communication.servers;

import java.util.*;
import java.io.*;
import java.net.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.lang.*;
import com.google.gson.*;

import client.server.*;
import shared.commands.*;

/**
 *  Handles all Post requests from the Client to the Server
 */
public class HTTP implements iServer {
    private static final String COOKIES_HEADER = "Set-cookie";
    private static CookieManager cookies = new CookieManager();

    int response_code;
    int SERVER_PORT;
    
    String SERVER_HOST;
    String URL_PREFIX;
    
    public HTTP(){
        this("localhost", "8080");
    }

    public HTTP(String host, String port){
        CookieHandler.setDefault(this.cookies);
        this.SERVER_HOST = host;
        this.SERVER_PORT = Integer.parseInt(port);
        this.URL_PREFIX = "http://" + host + ":" + port;
    }

    public String getPrefix(){
        return this.URL_PREFIX;
    }

    public String getDetails() {
        return getCookies();
    }

    public String getCookies() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < cookies.getCookieStore().getCookies().size(); i++){
            if(i > 0){
                tmp.append("; ");
            }
            tmp.append(cookies.getCookieStore().getCookies().get(i));
        }
        return tmp.toString();
    }

    public int getResponseCode(){
        return this.response_code;
    }

    /**
     *  Submit the clients request to the server.  Convert the request object
     *
     *  @return Response object from the server
     */
    public String submit(iCommand command) {
        Gson gson = new Gson();
        String response = null;
        try {
            URL url = new URL(URL_PREFIX + command.getEndPoint());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestProperty("Cookie", getCookies());    

            if(command.getMethod() == "POST"){
                connection.setDoOutput(true);
            }
            
            connection.connect();
            
            if(command.getMethod() == "POST"){
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(gson.toJson(command));
                out.close();
            }

            this.response_code = connection.getResponseCode();

            response = org.apache.commons.io.IOUtils.toString(connection.getInputStream());
            
            String cookie = connection.getHeaderField(this.COOKIES_HEADER);
            if (cookie != null) {
                this.cookies.getCookieStore().remove(null, HttpCookie.parse(cookie).get(0));
                this.cookies.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }

        }
        catch (IOException|URISyntaxException e) {
            System.out.println("Err: " + e);
        }
        return response;
    }
}
