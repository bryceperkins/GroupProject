import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import client.model.*;
import client.model.player.*;
import java.awt.Color;


public class GameTest {
	
	private Game game;
	private Player test_player1;
	private Player test_player2;
	private Player test_player3;
	private Player test_player4;
	private Game test_game;
	private List<Player> players;

    @Before
    public void setUp() {
		test_player1 = new Player("Alice", Color.puce, 1, 1, 1);
		test_player2 = new Player("Alice", Color.puce, 2, 2, 2);
		test_player3 = new Player("Alice", Color.puce, 3, 3, 3);
		test_player4 = new Player("Alice", Color.puce, 4, 4, 4);
        test_game = new Game();
		test_game.setWinner(new PlayerIndex(-1));
		test_players = new List<Player>();
		test_players.add(test_player1);
		test_game.setPlayers(test_players);
    }

    @After
    public void tearDown() {}

    @Test
    public void test_canbegingame_false() {
		assertFalse(game.canBeginGame());
    }
	
	@Test
	public void test_canbegingame_true() {
		test_players.add(test_player2);
		test_players.add(test_player3);
		test_players.add(test_player4);
		test_game.setPlayers(testPlayers);
		assertTrue(game.canBeginGame());
    }

    @Test
    public void test_gameover_false() {
        assertFalse(game.gameOver());
    }
	
	@Test
	public void test_gameover_true() {
		test_game.setWinner(new PlayerIndex(1));
        assertTrue(game.gameOver());
    } 
}
