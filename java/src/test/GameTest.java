import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.*;

import client.model.Game;
import client.model.PlayerIndex;
import client.model.player.*;


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
		test_player1 = new Player(Color.puce,"Alice", 1, 1, 1);
		test_player2 = new Player(Color.puce, "Alice", 2, 2, 2);
		test_player3 = new Player(Color.puce,"Alice", 3, 3, 3);
		test_player4 = new Player(Color.puce, "Alice", 4, 4, 4);
        test_game = new Game();
		test_game.setWinner(PlayerIndex.None);
		test_players.add(test_player1);
		test_game.setPlayers(test_players);
    }

    @After
    public void tearDown() {
		test_players = new ArrayList<Player>();
		test_player1 = new Player(Color.puce,"Alice", 1, 1, 1);
		test_player2 = new Player(Color.puce, "Alice", 2, 2, 2);
		test_player3 = new Player(Color.puce,"Alice", 3, 3, 3);
		test_player4 = new Player(Color.puce, "Alice", 4, 4, 4);
        test_game = new Game();
		test_game.setWinner(PlayerIndex.None);
		test_players.add(test_player1);
		test_game.setPlayers(test_players);
	}
	
	@Test
	public void true_test(){
		assertTrue(true);
	}

    @Test
    public void test_gameover_false() {
		System.out.println("1");
        assertFalse(test_game.gameOver());
    }
	
	@Test
	public void test_gameover_true() {
		System.out.println("2");
		test_game.setWinner(PlayerIndex.Player1);
        assertTrue(test_game.gameOver());
    } 
	
	@Test
    public void test_canbegingame_false() {
		System.out.println("3");
		assertFalse(test_game.canBeginGame());
    }
	
	@Test
	public void test_canbegingame_true() {
		System.out.println("4");
		test_players.add(test_player2);
		test_players.add(test_player3);
		test_players.add(test_player4);
		test_game.setPlayers(test_players);
		assertTrue(test_game.canBeginGame());
    }
}
