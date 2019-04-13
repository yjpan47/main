package seedu.address.commons.core;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.util.DateUtil;

public class DateUtilTest {

    @Test
    public void execute() {
        Assert.assertTrue(DateUtil.isValidDate(2001, 1, 12));
        Assert.assertFalse(DateUtil.isValidDate(10000, 1, 12));
        Assert.assertFalse(DateUtil.isValidDate(2001, 22, 30));
        Assert.assertFalse(DateUtil.isValidDate(2001, 2, 33));
        Assert.assertTrue(DateUtil.isValidDate(2004, 2, 29));
    }
}
