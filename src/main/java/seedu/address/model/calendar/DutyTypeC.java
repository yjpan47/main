package seedu.address.model.calendar;

/**
 * Duty Type C - Monday to Thursday
 */
class DutyTypeC extends Duty {

    /**
     * Constructs a duty of Type C
     */
    DutyTypeC(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.setNumOfVacancies(2);
        this.setPointsAwards(2);
    }
}
