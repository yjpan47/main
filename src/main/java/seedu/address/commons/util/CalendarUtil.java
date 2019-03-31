package seedu.address.commons.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.TimeZone;

/**
 * A container for Calendar specific utility functions.
 */
public class CalendarUtil {

    public static int getCurrentMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return calendar.get(Calendar.MONTH);
    }

    public static int getNextMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Gets current month in String format
     */
    public static String getMonthString(int monthIndex) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
        return months[monthIndex];
    }

    /**
     * Returns the day of the week of the first day of the particular month
     * @param monthIndex the month to be passed in
     * @return the index of the day of the week of the first day
     */
    public static int dayOfFirstDayOfMonth(int monthIndex) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(calendar.get(Calendar.YEAR), monthIndex, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Gets the number of days in the current month
     */
    public static int getNumOfDays(int monthIndex) {
        monthIndex++;
        if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(monthIndex)) {
            return 31;
        } else if (Arrays.asList(4, 6, 9, 11).contains(monthIndex)) {
            return 30;
        } else if (monthIndex == 2) {
            return 28;
        } else {
            throw new InputMismatchException("Invalid Month Index.");
        }
    }


}
