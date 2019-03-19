package seedu.address.model.calendar;

/**
 * Duty Type A - Saturday or Sunday
 */
class DutyTypeA extends Duty {

    /**
     * Constructs a duty of Type A
     */
    DutyTypeA(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.setNumOfVacancies(3);
        this.setPointsAwards(4);
    }
}
