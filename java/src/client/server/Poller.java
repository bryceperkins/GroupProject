package client.server;

import shared.commands.GameModel;
import client.server.ServerProxy;

public class Poller extends Thread {
    private ServerProxy server;
    /**
     * Polls the Server on a regular basis, retrieving a new model.
     */
    private int interval;
    private int version = 0;
    private int count = 0;
    private String response;
    private iCommand command;

    public Poller(){
        this(new ServerProxy("localhost", "8081"));
    }
    public Poller(ServerProxy server){
        setServer(server);
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public void setServer(ServerProxy server) {
        this.server = server;
    }
    public int getCount(){
        return this.count;
    }
    public void run() {
        try {
            poll();
        } catch (InterruptedException e) { }
    }
    public String poll() throws InterruptedException{
        while (true) {
            count++;
            if ( this.version > 0 ) {
                this.command = new GameModel(this.version);
            } else {
                this.command = new GameModel();
            }
            this.response = this.server.execute(command);
            if (this.response != "True") {
                // TODO this should send to the game manager when it's ready
                System.out.println("Got a JSON model");
            }
            this.sleep(2000);
        }
    }
}
