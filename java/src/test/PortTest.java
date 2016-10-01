package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import client.model.ResourceList;
import client.model.ResourceType;
import client.model.map.Port;
import client.model.map.*;

public class PortTest {
	private Port testPort;	
	private ResourceList superList;
	private ResourceType resource;
	private HexLocation location;
	private int ratio;
	private Direction direction; 

	@Before
	public void setUp()
	{
		testPort = new Port(resource,location, ratio, direction);
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
		testPort = new Port(resource,location, ratio, direction);
	}
	
	@Test
	public void test_canTrade_expect_True()
	{
		assertEquals(true, testPort.canTrade(superList));
	}

}
