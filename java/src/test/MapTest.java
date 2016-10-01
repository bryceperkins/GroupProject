import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import client.model.map.*;
import client.model.map.Map;
import client.model.map.ItemLocation;
import client.model.map.Direction;
import client.model.*;
import client.model.player.*;
import client.model.ResourceList;

public class MapTest {
    private Map testMap;
    private ResourceList superList;
    private Player testPlayer;
    private HexLocation hexLocation;
    private Direction direction;
    private ItemLocation itemLocation;

    
    @Before
    public void setUp()
    {
        testPlayer = new Player(Color.green,"Alice", 1, PlayerIndex.Player1 ,1);
        hexLocation = new HexLocation(1,1);
        direction = Direction.N;
        itemLocation = new ItemLocation(hexLocation,direction);
        testMap = new Map(); 
    }
    
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void test_canBuildCity_expect_true()
    {
        assertEquals(true, testMap.canBuildCity(testPlayer,itemLocation));
    }
    
    @Test
    public void test_canBuildSettlement_expect_true()
    {
        assertEquals(true, testMap.canBuildSettlement(testPlayer,itemLocation));
    }
    
    @Test
    public void test_canBuildRoad_expect_true()
    {
        assertEquals(true, testMap.canBuildRoad(testPlayer,itemLocation));
    } 
}
