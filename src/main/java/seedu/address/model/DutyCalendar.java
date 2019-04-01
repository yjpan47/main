package seedu.address.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import seedu.address.model.calendar.DutyMonth;


/**
 * Represents a Calendar that contains duties for the current and the following months
 */
public class DutyCalendar {

    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;

    private DutyMonth currentMonth;
    private DutyMonth nextMonth;
    private int currentYear;

    /**
     * Default constructor with no data contained within.
     */
    public DutyCalendar() {
        this.currentMonth = new DutyMonth(getTodayMonth(), dayOfFirstDayOfMonth(getTodayMonth()));
        this.nextMonth = new DutyMonth((getTodayMonth() + 1) % NUMBER_OF_MONTHS_IN_YEAR,
                dayOfFirstDayOfMonth(getTodayMonth() + 1));
        this.currentYear = getTodayYear();
    }

    public DutyCalendar(DutyMonth currentMonth, DutyMonth nextMonth) {
        this.currentMonth = currentMonth;
        this.nextMonth = nextMonth;
        this.currentYear = getTodayYear();
    }

    //=========== Constructor ==================================================================================

    private int getTodayYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }

    private int getTodayMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }

    private int dayOfFirstDayOfMonth(int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (month == 13) {
            calendar.set(calendar.get(Calendar.YEAR) + 1, month - 12, 1);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), month, 1);
        }
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public DutyMonth getCurrentMonth() {
        return currentMonth;
    }

    public DutyMonth getNextMonth() {
        return nextMonth;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setDutyCalendar(DutyCalendar dutyCalendar) {
        this.currentMonth = dutyCalendar.currentMonth;
        this.nextMonth = dutyCalendar.nextMonth;
    }
}
