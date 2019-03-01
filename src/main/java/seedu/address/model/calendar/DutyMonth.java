package seedu.address.model.calendar;

/**
 * Represents a month in DutyCalendar containing duty details of each day
 */
public class DutyMonth {

    private int month;
    private int firstDayIndex;
    private int numOfDays;

    public DutyMonth(int month, int firstDayIndex) {
        this.month = month;
        this.firstDayIndex = firstDayIndex;
        setNumOfDays(month);
    }

    public int getWeekOfDay(int day) {
        return (this.firstDayIndex + day - 1) % 7;
    }

    private void setNumOfDays(int month) {
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            this.numOfDays = 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            this.numOfDays = 30;
            break;
        case 2:
            this.numOfDays = 28;
            break;
        default:
            System.out.println("Invalid month.");
            this.numOfDays = 31;
            break;
        }
    }

}
