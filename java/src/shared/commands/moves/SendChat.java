package shared.commands;

import shared.model.PlayerIndex;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class SendChat extends MoveCommand{
    private String content;

    public SendChat(PlayerIndex index, String content){
        super("sendChat", index);
        this.content = content;
    }

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.sendChat(super.getIndex(), this.content);
    }
}
