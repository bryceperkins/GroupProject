import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.communication.*;

public class UserTest {
    private User testUser;

    @Before
    public void setUp() {
        User testUser = new User();
    }

    @After
    public void tearDown() throws Exception{
        testUser = null;
        assertNull(testUser);
    }

    @Test
    public void test_true() {
        User test = new User();
        assertTrue(true);
    }

    @Test
    public void test_equal() {
        User test = new User();
        assertEquals(6, 6);
    }
}
