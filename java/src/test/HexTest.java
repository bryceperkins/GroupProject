<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5


/*
+ hasRobber(): boolean
+ canPlaceRobber(): boolean
*/

=======
package test;

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
	private boolean hasRobber;
	private int value;
	private ResourceType resource;

	@Before
	public void setUp()
	{
		hexLocation = new HexLocation(1,1);	
		hasRobber = true;
		value = 5;
		resource = ResourceType.Wood;
		testHex = new Hex(hexLocation,hasRobber,value,resource);
	}
	
	@After
	public void tearDown()
	{
		hexLocation = new HexLocation(1,1);	
		hasRobber = true;
		value = 5;
		resource = ResourceType.Wood;
		testHex = new Hex(hexLocation,hasRobber,value,resource);
	}
	
	@Test
	public void test_hasRobber_expect_true()
	{
		assertEquals(true, testHex.hasRobber());
	}

	@Test
	public void test_canPlaceRobber_expect_true()
	{
		assertEquals(true, testHex.canPlaceRobber());
	}

}
>>>>>>> add tests



