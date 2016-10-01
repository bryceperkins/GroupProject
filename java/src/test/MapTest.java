package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.locations.VertexDirection;

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

    
    @Before
    public void setUp()
    {
        testPlayer = new Player(Color.green,"Alice", 1, PlayerIndex.Player1 ,1);
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
        //assertEquals(false, testMap.canBuildCity(testPlayer,itemLocation));
    }
    
/*    
    @Test
    public void test_canBuildSettlement_expect_true()
    {
<<<<<<< HEAD
        
=======
    	
>>>>>>> master
        assertEquals(true, testMap.canBuildSettlement(testPlayer,itemLocation));
    }
    
    @Test
    public void test_canBuildRoad_expect_false()
    {
        assertEquals(false, testMap.canBuildRoad(testPlayer,itemLocation));
    } 
*/
}
