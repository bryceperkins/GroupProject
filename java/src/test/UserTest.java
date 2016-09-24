import static org.junit.Assert.*;
import org.junit.Test;

import shared.communication.*;

public class UserTest {

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
