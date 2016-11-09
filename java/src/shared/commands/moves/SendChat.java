package shared.commands;

import shared.model.PlayerIndex;

public class SendChat extends MoveCommand{
    private String content;

    public SendChat(PlayerIndex index, String content){
        super("sendChat", index);
        this.content = content;
    }
}
