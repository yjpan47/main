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

    @Test
    public void testNumOfDaysInMonth() {
        Assert.assertEquals(CalendarUtil.getNumOfDays(2), 31);
        Assert.assertEquals(CalendarUtil.getNumOfDays(1), 28);
        Assert.assertEquals(CalendarUtil.getNumOfDays(8), 30);
        Assert.assertEquals(CalendarUtil.getNumOfDays(11), 31);
    }

    // getDutyMatrix test to be added

}
