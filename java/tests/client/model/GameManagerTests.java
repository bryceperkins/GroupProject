package model;

import junit.framework.*;

public class GameManagerTests extends TestCase {

    /*
    public void testFunction_condition_expectedResult() {
        // Setup
        // Execution
        // Verification
    }
     */

    public void testUserCanJoinGame_whenGameIsNotFull_itReturnsTrue() {
        Game g = new Game();
        g.players.setPlayers(new HashMap<Player>());
        g.getPlayers().add("id", new Player());

        assertTrue(GameManager.userCanJoinGame(g));
    }

}