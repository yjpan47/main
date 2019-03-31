package seedu.address.model;
import seedu.address.commons.util.CalendarUtil;
import seedu.address.model.duty.DutyMonth;

/**
 * Represents a Calendar that contains duties for the current and the following months
 */
public class DutyCalendar {

    private int currentMonthIndex;
    private DutyMonth currentMonth;
    private DutyMonth nextMonth;

    /**
     * Default constructor with no data contained within.
     */
    public DutyCalendar() {
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();
        this.currentMonth = new DutyMonth(currentMonthIndex, CalendarUtil.dayOfFirstDayOfMonth(currentMonthIndex));
        this.nextMonth = new DutyMonth(currentMonthIndex + 1,
                CalendarUtil.dayOfFirstDayOfMonth(currentMonthIndex + 1));
    }

    public DutyCalendar(DutyMonth currentMonth, DutyMonth nextMonth) {
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();
        this.currentMonth = currentMonth;
        this.nextMonth = nextMonth;
    }

    public int getCurrentMonthIndex() {
        return currentMonthIndex;
    }

    public DutyMonth getCurrentMonth() {
        return currentMonth;
    }

    public DutyMonth getNextMonth() {
        return nextMonth;
    }

    public void setDutyCalendar(DutyCalendar dutyCalendar) {
        if (dutyCalendar.getCurrentMonthIndex() == CalendarUtil.getCurrentMonth()) {
            this.currentMonth = dutyCalendar.getCurrentMonth();
            this.nextMonth = dutyCalendar.getNextMonth();
        } else {
            rollover(dutyCalendar);
        }
    }

    /**
     * Replace currentMonth with nextMonth and create a new nextMonth class
     * @param dutyCalendar the dutyCalendar from the storage
     */
    private void rollover(DutyCalendar dutyCalendar) {
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();
        this.currentMonth = dutyCalendar.getNextMonth();
        this.nextMonth = new DutyMonth(currentMonthIndex + 1,
                CalendarUtil.dayOfFirstDayOfMonth(currentMonthIndex + 1));
    }

}
