package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import client.model.*;
import client.model.TradeOffer.TradeOfferResourceList;
import client.model.player.*;

public class PlayerTest {
	private Player testPlayer;
	private ResourceList superList;
	
	@Before
	public void setUp()
	{
		testPlayer = new Player("Alice", Color.puce, 1);
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
		testPlayer = new Player("Alice", Color.puce, 1);
	}
	
	@Test
	public void test_canBuildCity_expect_false()
	{
		assertEquals(false, testPlayer.canBuildCity());
	}
	
	@Test
	public void test_canBuildSettlement_expect_true()
	{
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
	
}
