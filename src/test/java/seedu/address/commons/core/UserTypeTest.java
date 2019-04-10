package seedu.address.commons.core;

import org.junit.Assert;
import org.junit.Test;

public class UserTypeTest {
    @Test
    public void testMonthString() {
        Assert.assertNotEquals(UserType.ADMIN, UserType.GENERAL);
        Assert.assertTrue(UserType.ADMIN == UserType.ADMIN);
        Assert.assertTrue(UserType.GENERAL == UserType.GENERAL);
        Assert.assertNotEquals(UserType.ADMIN, null);
        Assert.assertNotEquals(UserType.GENERAL, null);
    }

}
