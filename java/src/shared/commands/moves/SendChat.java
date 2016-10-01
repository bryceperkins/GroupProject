package shared.commands;

public class SendChat extends MoveCommand{
    private String content;

    public SendChat(int index, String content){
        super("sendChat", index);
        this.content = content;
    }
}
