package seedu.address.model.calendar;

public class DutyTypeC extends Duty {

    /**
     * Constructs a duty
     *
     * @param monthIndex
     * @param dayIndex
     * @param weekIndex
     */
    public DutyTypeC(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.numOfVacancies = 2;
        this.pointsAwards = 2;
    }
}
