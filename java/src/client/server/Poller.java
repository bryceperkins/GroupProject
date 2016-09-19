package client.server

public class Poller {
    /**
     * Polls the Server on a regular basis, retrieving a new model.
     */
    private int interval;
    private int version;

    public Poller(){
    }
    public Poller(int interval){
    }
    /**
     * poll
     *
     * Calls to ServerInterface.gameModel().
     *
     * @param version The version of the model that is being requested from the server.
     */
    public void poll(){
    }
}
