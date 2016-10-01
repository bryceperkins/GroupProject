import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import client.model.*;
import client.model.map.Hex;
import client.model.map.HexLocation;
import client.model.map.Robber;
import client.model.ResourceList;

public class HexTest {
	private Hex testHex;
	private HexLocation hexLocation;
	private Robber robber;
	private int value;
	private ResourceType resource;

	@Before
	public void setUp()
	{
		hexLocation = new HexLocation(1,1);	
		robber = new Robber(hexLocation);
		value = 5;
		resource = ResourceType.Wood;
		testHex = new Hex(hexLocation,robber,value,resource);
	}
	
	@After
	public void tearDown()
	{
		hexLocation = new HexLocation(1,1);	
		robber = new Robber(hexLocation);
		value = 5;
		resource = ResourceType.Wood;
		testHex = new Hex(hexLocation,robber,value,resource);
	}
	
	@Test
	public void test_hasRobber_expect_true()
	{
		assertEquals(true, testHex.hasRobber());
	}

	@Test
	public void test_canPlaceRobber_expect_false()
	{
		assertEquals(false, testHex.canPlaceRobber());
	}

}