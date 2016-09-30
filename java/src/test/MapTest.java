<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5
/*package test;
=======
package test;
>>>>>>> add tests

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5
import client.model.*;
import client.model.TradeOffer.TradeOfferResourceList;
import client.model.player.*;

public class PlayerTest {
    private Player testPlayer;
    private ResourceList superList;
=======
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

>>>>>>> add tests
    
    @Before
    public void setUp()
    {
<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5
        testPlayer = new Player("Alice", Color.puce, 1);
        superList = new ResourceList();
        superList.setBrick(1000);
        superList.setWheat(1000);
        superList.setWood(1000);
        superList.setSheep(1000);
        superList.setOre(1000);
=======
        testPlayer = new Player("Alice", "green", 1);
        hexLocation = new HexLocation(1,1);
        direction = Direction.N;
        itemLocation = new ItemLocation(hexLocation,direction);
        
>>>>>>> add tests
    }
    
    @After
    public void tearDown()
    {
<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5
        testPlayer = new Player("Alice", Color.puce, 1);
    }
    
    @Test
    public void test_canBuildCity_expect_false()
    {
        assertEquals(false, testPlayer.canBuildCity());
=======
        testPlayer = new Player("Alice", "green", 1);
        hexLocation = new HexLocation(1,1);
        direction = Direction.N;
        itemLocation = new ItemLocation(hexLocation,direction);
    }
    
    @Test
    public void test_canBuildCity_expect_true()
    {
        assertEquals(true, testMap.canBuildCity(testPlayer,itemLocation));
>>>>>>> add tests
    }
    
    @Test
    public void test_canBuildSettlement_expect_true()
    {
<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5
        testPlayer.addResources(superList);
        assertEquals(true, testPlayer.canBuildCity());
    }
    
    @Test
    public void test_canBuildRoad_expect_false()
    {
        assertEquals(false, testPlayer.canBuildRoad());
    }
    
    @Test
    public void test_canBuyDevCard_expect_true()
    {
        testPlayer.addResources(superList);
        assertEquals(true, testPlayer.canBuyDevCard());
    }
    
    @Test
    public void test_canMakeTrade_expect_true()
    {
        testPlayer.addResources(superList);
        TradeOffer trade_offer = new TradeOffer();

        ResourceList emptyList = new ResourceList();
        trade_offer.setOffer(emptyList);
        
        assertEquals(true, testPlayer.canMakeTrade(trade_offer));
    }
    
}*/
=======
        assertEquals(true, testMap.canBuildSettlement(testPlayer,itemLocation));
    }
    
    @Test
    public void test_canBuildRoad_expect_true()
    {
        assertEquals(true, testMap.canBuildRoad(testPlayer,itemLocation));
    } 
}
>>>>>>> add tests
