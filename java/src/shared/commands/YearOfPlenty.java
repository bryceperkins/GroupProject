public class YearOfPlenty extends DevCardCommand {
    /**
     *  Gain two resources from the bank
     *
     *  @param resource1 - First resource
     *  @param resource2 - Second resourceL
     *
     *  @pre The resources are in the bank
     *
     *  @post You gained the two specified resources
     */
    private ResourceType resource1;
    private ResourceType resource2;
    public YearOfPlenty(ResourceType resource1, ResourceType resource2){};
}
