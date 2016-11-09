import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.*;

import shared.model.Game;
import shared.model.PlayerIndex;
import shared.model.player.*;
import shared.definitions.*;


public class GameTest {
    
    private Player test_player1;
    private Player test_player2;
    private Player test_player3;
    private Player test_player4;
    private Game test_game;
    private List<Player> test_players;

    @Before
    public void setUp() {
        test_players = new ArrayList<Player>();
        test_player1 = new Player(CatanColor.PUCE, "Alice", 1, PlayerIndex.Player1, 1);
        test_player2 = new Player(CatanColor.PUCE, "Alice", 2, PlayerIndex.Player2, 2);
        test_player3 = new Player(CatanColor.PUCE, "Alice", 3, PlayerIndex.Player3, 3);
        test_player4 = new Player(CatanColor.PUCE, "Alice", 4, PlayerIndex.Player4, 4);
        test_game = new Game();
        test_game.setWinner(PlayerIndex.None);
        test_players.add(test_player1);
        test_game.setPlayers(test_players);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void true_test(){
        assertTrue(true);
    }

    @Test
    public void test_gameover_false() {
        assertFalse(test_game.gameOver());
    }
    
    @Test
    public void test_gameover_true() {
        test_game.setWinner(PlayerIndex.Player1);
        assertTrue(test_game.gameOver());
    } 
    
    @Test
    public void test_canbegingame_false() {
        assertFalse(test_game.canBeginGame());
    }
    
    @Test
    public void test_canbegingame_true() {
        test_players.add(test_player2);
        test_players.add(test_player3);
        test_players.add(test_player4);
        test_game.setPlayers(test_players);
        assertTrue(test_game.canBeginGame());
    }
}
