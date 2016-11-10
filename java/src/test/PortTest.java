import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.model.ResourceList;
import shared.definitions.*;
import shared.model.map.*;

import shared.locations.*;

public class PortTest {
	private Port testPort;	
	private ResourceList superList;
	private ResourceType type;
	private HexLocation location;
	private int ratio;
	private EdgeDirection direction; 

	@Before
	public void setUp()
	{
		testPort = new Port(type,location, ratio, direction);
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
		testPort = new Port(type,location, ratio, direction);
	}
	
	@Test
	public void test_canTrade_expect_True()
	{
		assertEquals(true, testPort.canTrade(superList));
	}

}
