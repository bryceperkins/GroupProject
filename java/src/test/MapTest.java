import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.locations.*;
import shared.definitions.*;
import shared.definitions.CatanColor;
import client.model.map.*;
import client.model.*;
import client.model.player.*;
import client.model.ResourceList;

public class MapTest {
    private Map testMap;
    private ResourceList superList;
    private Player testPlayer;
    private HexLocation hexLocation;
    private VertexDirection direction;
    private ItemLocation itemLocation;
    private GameManager manager = GameManager.getInstance();
    
    @Before
    public void setUp()
    {
        testPlayer = new Player(CatanColor.GREEN,"Alice", 1, PlayerIndex.Player1 ,1);
        hexLocation = new HexLocation(1,1);
        direction = VertexDirection.West;
        itemLocation = new ItemLocation(hexLocation,direction);
        testMap = new Map(); 
    }
    
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void test_canBuildCity_expect_false()
    {
    	//put a city down somewhere
        assertTrue(!testMap.canBuildCity(testPlayer,itemLocation));
    }
    
    @Test
    public void test_canBuildSettlement_expect_true()
    {
        State state = new State(TurnTracker.GameStatus.Playing);
        assertFalse(testMap.canBuildSettlement(testPlayer,itemLocation,state));
    }
    
/*    
    @Test
    public void test_CanBuildCity_expect_true()
    {
    	List<Hex> h = testMap.getHexes();
    	
    	h.get(0).setLocation(new HexLocation(1,1));
    	
    	testMap.setHexes(h);
    	
    	assertTrue(testMap.canBuildCity(testPlayer, itemLocation));
    }

    @Test
    public void test_canBuildRoad_expect_false()
    {
        assertTrue(!testMap.canBuildRoad(testPlayer,itemLocation));
    } 
*/
}
