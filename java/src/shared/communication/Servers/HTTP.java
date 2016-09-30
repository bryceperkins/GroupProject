package shared.communication.servers;

import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;
import com.google.gson.*;

import client.server.*;
import shared.commands.*;

/**
 *  Handles all Post requests from the Client to the Server
 */
public class HTTP implements iServer {
    static final String COOKIES_HEADER = "Set-cookie";
    static CookieManager cookies = new CookieManager();

    int response_code;
    int SERVER_PORT;
    
    String SERVER_HOST;
    String URL_PREFIX;
    
    public HTTP(){
        this("localhost", "8080");
    }

    public HTTP(String host, String port){
        this.SERVER_HOST = host;
        this.SERVER_PORT = Integer.parseInt(port);
        this.URL_PREFIX = "http://" + host + ":" + port;
    }

    public String getPrefix(){
        return this.URL_PREFIX;
    }

    public int getResponseCode(){
        return this.response_code;
    }

    /**
     *  Submit the clients request to the server.  Convert the request object
     *
     *  @return Response object from the server
     */
    public String submit(Command command) {
        Gson gson = new Gson();
        String response = null;
        try {
            URL url = new URL(URL_PREFIX + command.getEndPoint());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            if (cookies.getCookieStore().getCookies().size() > 0) {
                StringBuilder tmp = new StringBuilder();
                for (int i = 0; i < cookies.getCookieStore().getCookies().size(); i++){
                    tmp.append(cookies.getCookieStore().getCookies().get(i));
                    tmp.append(";");
                }
                connection.setRequestProperty("Cookie", tmp.toString());    
            }

            connection.setRequestMethod(command.getMethod());
            connection.setDoOutput(true);
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(gson.toJson(command));
            out.close();
            this.response_code = connection.getResponseCode();


            response = org.apache.commons.io.IOUtils.toString(connection.getInputStream());
            //if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //    response = org.apache.commons.io.IOUtils.toString(connection.getInputStream());
            //}
            
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(this.COOKIES_HEADER);

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    cookies.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
                }               
            }

        }
        catch (IOException e) {
            System.out.println("Err: " + e);
        }
        return response;
    }
}