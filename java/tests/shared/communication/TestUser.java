package shared.communication;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestUser {

        @Test
        public void test_welcome_message() {
            User test = new User();
            assertEquals("welcome", test.getUserName());
        }

}
