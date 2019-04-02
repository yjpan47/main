package seedu.address.commons.util;

public class DateUtil {

    public static int MAX_VALID_YR = 9999;
    public static int MIN_VALID_YR = 2000;

    public static final String[] months = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"};

    public static boolean isLeap(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    public static boolean isValidDate(int y, int m, int d) {
        if (!isValidYear(y))
            return false;
        if (!isValidMonth(m))
            return false;
        if (d < 1 || d > 31)
            return false;
        if (m == 2 && isLeap(y))
            return (d <= 29);
        if (m == 2 && !isLeap(y))
            return (d <= 28);
        if (m == 4 || m == 6 || m == 9 || m == 11)
            return (d <= 30);
        return true;
    }

    public static boolean isValidYear(int y) {
        return y >= MIN_VALID_YR && y <= MAX_VALID_YR;
    }

    public static boolean isValidMonth(int m) {
        return m >= 1 && m <= 12;
    }

    public static boolean isValidDayOfWeek(int d) {
        return d >= 1 && d <= 7;
    }

    public static String getMonth(int m) {
        return months[m - 1];
    }

    public static String getDayOfWeek(int d) {
        return daysOfWeek[d - 1];
    }

    public static int getNumOfDaysInMonth(int y, int m) {
        if (!isValidYear(y) || !isValidMonth(m)) {
            throw new IllegalArgumentException("Invalid Date");
        } else if (m == 2 && isLeap(y)) {
            return 29;
        } else if (m == 2 && !isLeap(y)) {
            return 28;
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            return 30;
        } else {
            return 31;
        }
    }
}


