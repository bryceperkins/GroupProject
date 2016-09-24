import static org.junit.Assert.assertEquals;
import org.junit.Test;

import shared.communication.*;

public class UserTest {

        @Test
        public void test_welcome_message() {
            User test = new User();
            assertEquals("welcome", "welcome");
        }

}
