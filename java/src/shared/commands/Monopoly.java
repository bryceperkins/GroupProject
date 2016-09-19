public class Monopoly extends DevCardCommand{
    /**
     *  Play the monopoly DevCard
     *
     *  @param resource - The resource you are collecting from the other players
     *
     *  @post You gain the amount of specified resource the other players lost
     */
    private ResourceType resource;
    public Monopoly(ResourceType resource){};
}
