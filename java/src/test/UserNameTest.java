import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import shared.communication.*;

public class UserNameTest {
    private UserName testUsername;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        testUsername = null;
    }

    @Test
    public void test_createValidUserName() {
        try {
            testUsername = new UserName("Steve");
        } catch (Exception e){
            fail("Error: " + e);

        }
        assertEquals("Steve", testUsername.getName());
    }
    
    @Test()
    public void test_createInvalidUserName() {
        try {
            testUsername = new UserName("'); Fail/");
        } catch (Exception e){
            assertNull(testUsername);
            return;
        }
        fail("Should've thrown an Exception for an Invalid Username");
    }
}
