package seedu.address.commons.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A container for Calendar specific utility functions.
 */
public class CalendarUtil {

    public static int getCurrentMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }

    public static int getNextMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int dayOfFirstDayOfMonth(int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(calendar.get(Calendar.YEAR), month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
