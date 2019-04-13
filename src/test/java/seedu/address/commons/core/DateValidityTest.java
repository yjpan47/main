package seedu.address.commons.core;

import org.junit.Assert;
import org.junit.Test;

public class DateValidityTest {

    @Test
    public void execute() {
        Assert.assertTrue(DateValidity.isValidDate(2001, 1, 12));
        Assert.assertFalse(DateValidity.isValidDate(10000, 1, 12));
        Assert.assertFalse(DateValidity.isValidDate(2001, 2, 30));
        Assert.assertFalse(DateValidity.isValidDate(2001, 22, 30));
        Assert.assertFalse(DateValidity.isValidDate(2001, 2, 33));
        Assert.assertTrue(DateValidity.isValidDate(2004, 2, 29));
        Assert.assertFalse(DateValidity.isValidDate(2005, 2, 29));
    }
}
