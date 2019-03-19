package seedu.address.model.calendar;

class DutyTypeC extends Duty {

    /**
     * Constructs a duty of Type C
     */
    DutyTypeC(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.numOfVacancies = 2;
        this.pointsAwards = 2;
    }
}
