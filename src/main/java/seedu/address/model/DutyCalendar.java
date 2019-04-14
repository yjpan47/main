package seedu.address.model;

import java.util.List;

import seedu.address.commons.util.CalendarUtil;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutySettings;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Person;

/**
 * Represents a Calendar that contains duties for the current and the following months
 */
public class DutyCalendar {


    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;

    private int currentYear;
    private int currentMonthIndex;

    private DutyMonth currentMonth;
    private DutyMonth nextMonth;
    private DutyMonth dummyNextMonth;

    private DutyStorage dutyStorage;

    /**
     * Default constructor with no data contained within.
     */
    public DutyCalendar() {
        this.currentYear = CalendarUtil.getCurrentYear();
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();

        this.currentMonth = new DutyMonth(this.currentYear, this.currentMonthIndex,
                CalendarUtil.dayOfFirstDayOfMonth(this.currentYear, this.currentMonthIndex));

        int yearOfNextMonth = currentMonthIndex == 11 ? currentYear + 1 : currentYear;
        this.nextMonth = new DutyMonth(yearOfNextMonth, this.currentMonthIndex + 1 ,
                CalendarUtil.dayOfFirstDayOfMonth(yearOfNextMonth, this.currentMonthIndex + 1));

        this.dutyStorage = new DutyStorage();
    }

    public DutyCalendar(DutyMonth currentMonth, DutyMonth nextMonth, DutyStorage dutyStorage) {
        this.currentYear = CalendarUtil.getCurrentYear();
        this.currentMonthIndex = CalendarUtil.getCurrentMonth();
        this.currentMonth = currentMonth;
        this.nextMonth = nextMonth;
        this.dutyStorage = dutyStorage;
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

    public DutyMonth getDummyNextMonth() {
        return dummyNextMonth;
    }

    public DutyStorage getDutyStorage() {
        return dutyStorage;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    /**
     *  Sets Duty Calendar
     */
    public void setDutyCalendar(DutyCalendar dutyCalendar) {
        if (dutyCalendar.getCurrentMonth().getMonthIndex() == CalendarUtil.getCurrentMonth()) {
            this.currentMonth = new DutyMonth(dutyCalendar.getCurrentMonth(), true);
            this.nextMonth = new DutyMonth(dutyCalendar.getNextMonth(), true);
            this.dutyStorage = new DutyStorage(dutyCalendar.getDutyStorage());
        } else {
            this.rollover(dutyCalendar);
        }
    }

    /**
     *  Sets Duty Calendar without rollover for testing purposes
     */
    public void setDutyCalendar(DutyCalendar dutyCalendar, boolean needsRollover) {
        if (!needsRollover) {
            this.currentMonth = dutyCalendar.getCurrentMonth();
            this.nextMonth = dutyCalendar.getNextMonth();
            this.dutyStorage = dutyCalendar.getDutyStorage();
        } else {
            setDutyCalendar(dutyCalendar);
        }
    }

    /**
     * Schedules the cuties for next Month
     */
    public void scheduleDutyForNextMonth(List<Person> persons,
                                         DutySettings dutySettings, DutyStorage dutyStorage) {
        this.dummyNextMonth = new DutyMonth(nextMonth, false);
        dummyNextMonth.schedule(persons, dutySettings, dutyStorage);
    }

    /**
     * Confirms Schedule
     */
    public void confirm() {
        this.nextMonth = this.dummyNextMonth;
        this.nextMonth.confirm();
    }

    /**
     * Unconfirms schedule
     */
    public void unconfirm() {
        int yearOfNextMonth = currentMonthIndex == 11 ? currentYear + 1 : currentYear;
        this.nextMonth = new DutyMonth(yearOfNextMonth, this.currentMonthIndex + 1 ,
                CalendarUtil.dayOfFirstDayOfMonth(yearOfNextMonth, this.currentMonthIndex + 1));
    }

    /**
     * Replace currentMonth with nextMonth and create a new nextMonth class
     * @param dutyCalendar the dutyCalendar from the storage
     */
    private void rollover(DutyCalendar dutyCalendar) {
        this.currentMonth = dutyCalendar.getNextMonth();
        int yearOfNextMonth = currentMonthIndex == 11 ? currentYear + 1 : currentYear;
        this.nextMonth = new DutyMonth(yearOfNextMonth, this.currentMonthIndex + 1 ,
                CalendarUtil.dayOfFirstDayOfMonth(yearOfNextMonth, this.currentMonthIndex + 1));
    }
}
