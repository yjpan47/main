package seedu.address.model.calendar;

public class DutyTypeA extends Duty {

    /**
     * Constructs a duty
     *
     * @param monthIndex
     * @param dayIndex
     * @param weekIndex
     */
    public DutyTypeA(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.numOfVacancies = 3;
        this.pointsAwards = 4;
    }
}
