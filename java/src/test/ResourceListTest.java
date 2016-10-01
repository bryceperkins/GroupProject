import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.*;

import client.model.Game;
import client.model.PlayerIndex;
import client.model.player.*;
import client.model.ResourceList;


public class ResourceListTest {
	
	ResourceList test_list;
	ResourceList compare_list_true;
	ResourceList compare_list_false;

    @Before
    public void setUp() {
		test_list = new ResourceList(1,1,1,1,1);
		compare_list_true = new ResourceList(1,0,0,1,0);
		compare_list_false = new ResourceList(1,0,0,0,2);
    }

    @After
    public void tearDown() {
		test_list = new ResourceList(1,1,1,1,1);
		compare_list_true = new ResourceList(1,0,0,1,0);
		compare_list_false = new ResourceList(1,0,0,0,2);
	}
	
	@Test
	public void true_test(){
		assertTrue(true);
	}

    @Test
    public void test_hasresources_false() {
		System.out.println("1");
        assertFalse(test_list.hasResources(compare_list_false));
    }
	
	@Test
	public void test_gameover_true() {
        assertTrue(test_list.hasResources(compare_list_true));
    } 
	
	@Test
    public void test_addresources_false() {
		System.out.println("3");
		test_list.addResources(compare_list_false);
		assertEquals(test_list.getBrick(), 2);
		assertEquals(test_list.getOre(), 1);
		assertEquals(test_list.getSheep(), 1);
		assertEquals(test_list.getWheat(), 1);
		assertEquals(test_list.getWood(), 3);
    }

}
