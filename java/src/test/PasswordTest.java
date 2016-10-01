import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.communication.*;

public class PasswordTest {
    private Password testPassword;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        testPassword = null;
    }

    @Test
    public void test_createValidPassword() {
        try {
            testPassword = new Password("Password");
        } catch (Exception e){
            fail("Error: " + e);

        }
        assertEquals("Password", testPassword.getPassword());
    }
    
    @Test()
    public void test_createInvalidPassword() {
        try {
            testPassword = new Password("'); Fail/");
        } catch (Exception e){
            assertNull(testPassword);
            return;
        }
        fail("Should've thrown an Exception for an Invalid Password");
    }
}
