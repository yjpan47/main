package seedu.address.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import seedu.address.model.calendar.DutyMonth;
import seedu.address.model.person.UniquePersonList;


/**
 * Represents a Calendar that contains duties for the current and the following months
 */
public class DutyCalendar {

    private DutyMonth currentMonth;
    private DutyMonth nextMonth;
    private UniquePersonList personList;

    /**
     * Default constructor with no data contained within.
     */
    public DutyCalendar(UniquePersonList personList) {
        this.currentMonth = new DutyMonth(personList, getTodayMonth(), dayOfFirstDayOfMonth(getTodayMonth()));
        this.nextMonth = new DutyMonth(personList, (getTodayMonth() + 1), dayOfFirstDayOfMonth(getTodayMonth() + 1));
    }

    public DutyCalendar() {
        GregorianCalendar calendar = new GregorianCalendar();
        this.currentMonth = new DutyMonth(1, 1);
        this.nextMonth = new DutyMonth(2, 2);
    }

    void setDutyCalendar(DutyCalendar dutyCalendar) {
        this.currentMonth = dutyCalendar.currentMonth;
        this.nextMonth = dutyCalendar.nextMonth;
    }

    public void rollover() {
        this.currentMonth = this.nextMonth;
        // run allocation algorithm
        int nextMonthIndex = getTodayMonth() + 1;
        this.nextMonth = new DutyMonth(nextMonthIndex, dayOfFirstDayOfMonth(nextMonthIndex));
    }

    //=========== Constructor ==================================================================================

    private int getTodayMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }

    private int dayOfFirstDayOfMonth(int month) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(calendar.get(Calendar.YEAR), month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public UniquePersonList getPersonList() {
        return personList;
    }

    public DutyMonth getCurrentMonth() {
        return currentMonth;
    }

    public DutyMonth getNextMonth() {
        return nextMonth;
    }

}
