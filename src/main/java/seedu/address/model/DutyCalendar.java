package seedu.address.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import seedu.address.model.calendar.DutyMonth;


/**
 * Represents a Calendar that contains duties for the current and the following months
 */
public class DutyCalendar {

    private DutyMonth currentMonth;
    private DutyMonth nextMonth;

    /**
     * Default constructor with no data contained within.
     */
    public DutyCalendar() {
        this.currentMonth = new DutyMonth(getTodayMonth(), dayOfFirstDayOfMonth(getTodayMonth()));
        this.nextMonth = new DutyMonth(getTodayMonth() + 1, dayOfFirstDayOfMonth(getTodayMonth() + 1));
    }

    public void setDutyCalendar(DutyCalendar dutyCalendar) {
        this.currentMonth = dutyCalendar.currentMonth;
        this.nextMonth = dutyCalendar.nextMonth;
    }

    private int getTodayMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }

    private int dayOfFirstDayOfMonth(int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(calendar.get(Calendar.YEAR), month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
