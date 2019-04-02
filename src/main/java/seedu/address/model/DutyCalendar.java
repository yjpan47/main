package seedu.address.model;
import seedu.address.commons.util.CalendarUtil;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutySettings;
import seedu.address.model.duty.DutyStorage;

/**
 * Represents a Calendar that contains duties for the current and the following months
 */
public class DutyCalendar {

    private int  currentYear;
    private int currentMonthIndex;

    private DutyMonth currentMonth;
    private DutyMonth nextMonth;

    private DutySettings dutySettings;
    private DutyStorage dutyStorage;

    /**
     * Default constructor with no data contained within.
     */
    public DutyCalendar() {
        this.currentYear = CalendarUtil.getCurrentYear();
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();
        this.currentMonth = new DutyMonth(this.currentYear, this.currentMonthIndex,
                CalendarUtil.dayOfFirstDayOfMonth(this.currentMonthIndex));
        this.currentMonth = new DutyMonth((this.currentMonthIndex == 12)
                ? this.currentYear + 1 : this.currentYear,
                this.currentMonthIndex + 1,
                CalendarUtil.dayOfFirstDayOfMonth(this.currentMonthIndex + 1));
    }

    public DutyCalendar(DutyMonth currentMonth, DutyMonth nextMonth) {
        this.currentMonth = currentMonth;
        this.nextMonth = nextMonth;
    }

    public int getCurrentMonthIndex() {
        return this.currentMonthIndex;
    }

    public DutyMonth getCurrentMonth() {
        return currentMonth;
    }

    public DutyMonth getNextMonth() {
        return nextMonth;
    }

    public DutySettings getDutySettings() {
        return dutySettings;
    }

    public DutyStorage getDutyStorage() {
        return dutyStorage;
    }

    public void setDutyCalendar(DutyCalendar dutyCalendar) {
        if (dutyCalendar.getCurrentMonthIndex() == CalendarUtil.getCurrentMonth()) {
            this.currentMonth = dutyCalendar.getCurrentMonth();
            this.nextMonth = dutyCalendar.getNextMonth();
        } else {
            this.rollover(dutyCalendar);
        }
    }

    /**
     * Replace currentMonth with nextMonth and create a new nextMonth class
     * @param dutyCalendar the dutyCalendar from the storage
     */
    private void rollover(DutyCalendar dutyCalendar) {
        this.currentYear = CalendarUtil.getCurrentYear();
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();
        this.currentMonth = dutyCalendar.getNextMonth();
        this.nextMonth = new DutyMonth(this.currentYear, this.currentMonthIndex + 1,
                CalendarUtil.dayOfFirstDayOfMonth(this.currentMonthIndex + 1));
    }

}
