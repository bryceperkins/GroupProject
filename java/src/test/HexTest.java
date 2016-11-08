import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.model.map.Hex;

import shared.locations.HexLocation;
import shared.definitions.*;

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
		resource = ResourceType.WOOD;
		testHex = new Hex(hexLocation,hasRobber,value,resource);
	}
	
	@After
	public void tearDown()
	{
		hexLocation = new HexLocation(1,1);	
		hasRobber = true;
		value = 5;
		resource = ResourceType.WOOD;
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



