package client.server;

public class Poller {
    /**
     * Polls the Server on a regular basis, retrieving a new model.
     */
    private int interval;
    private int version;

    public Poller(){
    }
    /**
     * @param interval - how often the server should be polled.
     */
    public Poller(int interval){
    }
    /**
     * poll
     *
     * Polls the server for the current version of the model.  Updates version if different.
     */
    public void poll(){
    }
}
