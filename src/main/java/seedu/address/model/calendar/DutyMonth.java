package seedu.address.model.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Represents a month in DutyCalendar containing duty details of each day
 */
public class DutyMonth {

    private String month;
    private int monthIndex;
    private int firstDayIndex;
    private int numOfDays;
    private List<DutyDate> dates;

    public DutyMonth(int monthIndex, int firstDayIndex) {
        if (monthIndex >= 1 && monthIndex <= 12 && firstDayIndex >= 0 && firstDayIndex <= 7)  {
            this.monthIndex = monthIndex;
            this.month = this.getMonth();
            this.firstDayIndex = firstDayIndex;
            this.numOfDays = getNumOfDays();
            this.dates = new ArrayList<>();
        } else {
            throw new InputMismatchException("Invalid Month Index or first Day Index");
        }
    }
    /**
     * Gets current month
     */
    public String getMonth() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July"
                , "August", "September", "October", "November", "December"};
        return months[this.monthIndex - 1];
    }
    /**
     * Returns current month
     */
    public boolean isCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        return (this.monthIndex == currentMonth);
    }
    /**
     * Gets the number of days in the current month
     */
    private int getNumOfDays() {
        if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(this.monthIndex)) {
            return 31;
        } else if (Arrays.asList(4, 6, 9, 11).contains(this.monthIndex)) {
            return 30;
        } else if (this.monthIndex == 2) {
            return 28;
        } else {
            throw new InputMismatchException("Invalid Month Index.");
        }
    }

}
