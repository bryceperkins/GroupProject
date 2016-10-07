import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import client.model.*;
import client.model.map.*;
import client.model.player.Color;
import client.model.player.Player;

import shared.locations.HexLocation;
import shared.definitions.ResourceType;

public class PlayerTest {
	private Player testPlayer;
	private ResourceList superList;
	
	@Before
	public void setUp()
	{
		testPlayer = new Player(Color.puce, "Alice", 0, PlayerIndex.Player1, 0);
		superList = new ResourceList();
		superList.setBrick(1000);
		superList.setWheat(1000);
		superList.setWood(1000);
		superList.setSheep(1000);
		superList.setOre(1000);
	}
	
	@After
	public void tearDown()
	{
		testPlayer = new Player(Color.puce, "Alice", 0, PlayerIndex.Player1, 0);
	}
	
	@Test
	public void true_test(){
		assertTrue(true);
	}

	@Test
	public void test_canBuildCity_expect_false()
	{
		assertTrue(!testPlayer.canBuildCity());
	}
	
	@Test
	public void test_canBuildSettlement_expect_true()
	{
		testPlayer.setResources(superList);
		assertTrue(testPlayer.canBuildCity());
	}
/*	
	@Test
	public void test_canBuildRoad_expect_false()
	{
		assertTrue(testPlayer.canBuildRoad());
	}
*/	
	@Test
	public void test_canBuyDevCard_expect_true()
	{
		testPlayer.setResources(superList);
		assertTrue(testPlayer.canBuyDevCard());
	}
	
	@Test
	public void test_canMakeTrade_expect_true()
	{
		testPlayer.setResources(superList);
		TradeOffer trade_offer = new TradeOffer();
		
		//trade request of one sheep for one brick
		ResourceList resourceOffer = new ResourceList();
		resourceOffer.setBrick(-1);
		resourceOffer.setSheep(1);
		trade_offer.setOffer(resourceOffer);
		
		assertTrue(testPlayer.canMakeTrade(trade_offer));
	}
	
	@Test 
	public void test_canMakeMaritimeTradeWithoutPorts_false()
	{
		//set 
		ResourceList tempList = new ResourceList();
		tempList.setBrick(3);
		testPlayer.setResources(tempList);
		assertTrue(!testPlayer.canMakeMaritimeTrade(ResourceType.BRICK));
	}
	
	@Test
	public void test_canMakeMaritimeTradeWithPorts_true()
	{
		Port port = new Port(ResourceType.BRICK, new HexLocation(0,0), 3, HexDirection.N);
		testPlayer.addPort(port);
		ResourceList tempList = new ResourceList();
		tempList.setBrick(3);
		testPlayer.setResources(tempList);
		
		assertEquals(true, testPlayer.canMakeMaritimeTrade(ResourceType.BRICK));
		
		
	}

}
