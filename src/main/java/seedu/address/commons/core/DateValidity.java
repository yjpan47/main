package seedu.address.commons.core;

/**
 * Helps to verify the validity of an input date given the year, month and day.
 */
class DateValidity {

    static int MAX_VALID_YR = 9999;
    static int MIN_VALID_YR = 2000;

    static boolean isLeap(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    static boolean isValidDate(int year, int month, int day) {
        if (year > MAX_VALID_YR || year < MIN_VALID_YR)
            return false;
        if (month < 1 || month > 12)
            return false;
        if (day < 1 || day > 31)
            return false;
        if (month == 2 && isLeap(year))
            return (day <= 29);
        if (month == 2 && !isLeap(year))
            return (day <= 28);
        return true;
    }
}
