package server.handlers;

import server.handlers.iServerFacade;

public interface iServerCommand {
    public String serverExecute();
    public void setFacade(iServerFacade facade);

}
