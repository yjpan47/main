package seedu.address.model.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
