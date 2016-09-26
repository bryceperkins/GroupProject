import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.communication.*;

public class UserTest {
    private User testUser;
    private String testUsername;
    private String testPassword;

    @Before
    public void setUp() {
        try {
            testUser = new User();
            testUsername = "Steve";
            testPassword = "Password";
        } catch (Exception err) {
            System.err.println("setUp failed");
        } finally {
            assertNotNull(testUser);
        }
    }

    @After
    public void tearDown() {
        testUser = null;
        assertNull(testUser);
    }

    @Test
    public void test_createEmptyUser() {
        testUser = new User();
        assertNotNull(testUser);
    }

    @Test
    public void test_createUserValidName() {
        testUser = new User(testUsername, testPassword);
        assertEquals("Steve", testUser.getUserName());
    }
    
    @Test
    public void test_createUserValidPassword() {
        testUser = new User(testUsername, testPassword);
        assertEquals("Password", testUser.getPassword());
    }
    
    @Test()
    public void test_createUserInvalidName() {
        try {
            testUser = new User("'); fail", "password");
        } catch (Exception err) {
            assertNull(testUser.getUserName());
        }
    }
    
    @Test()
    public void test_createUserInvalidPassword() {
        try {
            testUser = new User("fail", "'):\"");
        } catch (Exception err) {
            assertNull(testUser.getPassword());
        }
    }
}
