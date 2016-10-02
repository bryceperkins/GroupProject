package client.server;

public interface iServer {
    public String submit(iCommand command);
    public int getResponseCode();
    public String getDetails();
}
