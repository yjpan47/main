package seedu.address.commons.core;

import org.junit.Assert;
import org.junit.Test;
import seedu.address.commons.core.UserType;

public class UserTypeTest {
    @Test
    public void testMonthString() {
        Assert.assertNotEquals(UserType.ADMIN, UserType.GENERAL);
        Assert.assertEquals(UserType.ADMIN, UserType.ADMIN);
        Assert.assertEquals(UserType.GENERAL, UserType.GENERAL);
    }

}
