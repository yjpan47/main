package seedu.address.model.calendar;

/**
 * Duty Type B - Friday
 */
class DutyTypeB extends Duty {

    /**
     * Constructs a duty of Type B
     */
    DutyTypeB(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.setNumOfVacancies(2);
        this.setPointsAwards(3);
    }
}
