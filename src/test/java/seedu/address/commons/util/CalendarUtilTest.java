package seedu.address.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class CalendarUtilTest {

    @Test
    public void testMonthString() {
        Assert.assertEquals(CalendarUtil.getMonthString(0), "January");
        Assert.assertEquals(CalendarUtil.getMonthString(5), "June");
        Assert.assertEquals(CalendarUtil.getMonthString(11), "December");
    }

}
