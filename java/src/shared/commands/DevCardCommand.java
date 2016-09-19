package shared.commands;

public class DevCardCommand extends PlayingCommand{
    /**
     *  Similar to a playing command, however, you have to have the specified card, and have not played a non-monument card already.
     *
     *  @pre you have the card
     *  @pre you have not played a non-monument card
     *
     */
    private Boolean hasCard;
    private Boolean hasPlayed;
    public DevCardCommand();
    /**
     *  @throws NoCardException
     */
    public String execute() throws NoCardException{};
}
