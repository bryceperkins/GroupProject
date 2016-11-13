package server.handlers;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.*;
import com.sun.net.httpserver.*;


public class Handlers {
    public abstract static class BaseFile implements HttpHandler {
        private static Logger LOGGER = Logger.getLogger(BaseFile.class.getName());
        public BaseFile(String rootPath){ 
            this.rootPath = rootPath; 
        }
        protected String rootPath;
        protected String getRequestPath(HttpExchange exchange){
            return exchange.getRequestURI().getPath().substring(1);
        }
        protected void sendFile(HttpExchange exchange, String filepath) throws IOException{
            try {
                LOGGER.log(Level.SEVERE, "Requesting " + filepath);
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                System.out.println("Current relative path is: " + s);
                byte[] response = FileUtils.readFileToByteArray(new File(filepath));
                System.out.println("HERE");
                ArrayList<String> mimetypes = new ArrayList<String>();
                //mimetypes.add(FileUtils.getMimeType(filepath));
                mimetypes.add("text/html");
                exchange.getResponseHeaders().put("Content­type", mimetypes);
                exchange.sendResponseHeaders(200,response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } catch (IOException ioe){
                LOGGER.log(Level.SEVERE, "Failed to retrieve " + filepath);
                exchange.sendResponseHeaders(404, 1);
                OutputStream os = exchange.getResponseBody();
                os.close();
                System.out.println("Couldn't find the file " + new File(filepath).getAbsolutePath());
            }
        }
    }
    // get the file from the system
    public static class BasicFile extends BaseFile {
        public BasicFile(String rootPath) { 
            super(rootPath); 
        }
        public void handle(HttpExchange exchange) throws IOException {
            String filepath = this.rootPath + this.getRequestPath(exchange);
            this.sendFile(exchange, filepath);
        }
    }
    // appends ".json" to the request before getting the proper file from the file system
    public static class JSONAppender extends BaseFile{
        public JSONAppender(String rootPath){ 
            super(rootPath);
        }
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println( this.rootPath + " ___ " + this.getRequestPath(exchange)); 
            this.sendFile(exchange, this.rootPath + this.getRequestPath(exchange) + ".json");
        }
    }
}
