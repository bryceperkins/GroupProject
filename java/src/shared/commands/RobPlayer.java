public class RobPlayer extends PlayingCommand{
    /**
     *  Relocate the robber and rob another player
     *
     *  @param location - new location for the Robber
     *  @param victimIndex - player to be robber or -1 for no one
     *
     *  @pre Robber is not staying in the same locationY
     *  @pre Player being robbed has resource cards
     *  
     *  @post Robber has moved
     *  @post Player being robbed (if any) has given you their resource cards
     */
    private HexLocation location;
    private PlayerIndex victimIndex;
    public RobPlayer(HexLocation location, PlayerIndex victimIndex){};

}
