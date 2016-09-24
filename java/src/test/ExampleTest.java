import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class ExampleTest {

    private boolean testTrue;
    private int testInt;

    @Before
    public void setUp() {
        testTrue = true;
        testInt = 6;
    }

    @After
    public void tearDown() {
        testTrue = false;
        testInt = 0;

        assertFalse(testTrue);
        assertEquals(testInt, 0);
    }

    @Test
    public void test_true() {
        assertTrue(testTrue);
    }

    @Test
    public void test_equal() {
        assertEquals(testInt, 6);
    }
}
