package seedu.address.commons.util;

import java.security.InvalidParameterException;
/**
 * Date Utility Class to check valid dates and days
 */
public class DateUtil {

    private static final int MAX_VALID_YR = 9999;
    private static final int MIN_VALID_YR = 2000;

    private static final String[] months = {"January", "February", "March", "April",
        "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"};

    public static boolean isLeap(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }
    /**
     * Checks if an input date is valid given year,month and date
     */
    public static boolean isValidDate(int y, int m, int d) {
        if (!isValidYear(y)) {
            return false;
        }
        if (!isValidMonth(m)) {
            return false;
        }
        if (d < 1 || d > 31) {
            return false;
        }
        if (m == 1 && isLeap(y)) {
            return (d <= 29);
        }
        if (m == 1 && !isLeap(y)) {
            return (d <= 28);
        }
        if (m == 3 || m == 5 || m == 8 || m == 10) {
            return (d <= 30);
        }
        return true;
    }

    public static boolean isValidYear(int y) {
        return y >= MIN_VALID_YR && y <= MAX_VALID_YR;
    }

    public static boolean isValidMonth(int m) {
        return m >= 0 && m <= 11;
    }

    public static boolean isValidDayOfWeek(int d) {
        return d >= 1 && d <= 7;
    }

    public static String getMonth(int m) {
        return months[m];
    }

    public static String getDayOfWeek(int d) {
        return daysOfWeek[d - 1];
    }


    public static int getDayOfWeekIndex(String s) {
        for (int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];
            if (day.equalsIgnoreCase(s) || day.substring(0, 2).equalsIgnoreCase(s.substring(0, 2))) {
                return i + 1;
            }
        }
        throw new InvalidParameterException("Day of Week does not exist!");
    }

    public static int getNumOfDaysInMonth(int y, int m) {
        if (!isValidYear(y) || !isValidMonth(m)) {
            throw new IllegalArgumentException("Invalid Date");
        } else if (m == 1 && isLeap(y)) {
            return 29;
        } else if (m == 1 && !isLeap(y)) {
            return 28;
        } else if (m == 3 || m == 5 || m == 8 || m == 10) {
            return 30;
        } else {
            return 31;
        }
    }
}


