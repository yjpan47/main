package seedu.address.model.calendar;

class DutyTypeB extends Duty {

    /**
     * Constructs a duty of Type B
     */
    DutyTypeB(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.numOfVacancies = 2;
        this.pointsAwards = 3;
    }
}
