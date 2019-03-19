package seedu.address.model.calendar;

class DutyTypeA extends Duty {

    /**
     * Constructs a duty of Type A
     */
    DutyTypeA(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.numOfVacancies = 3;
        this.pointsAwards = 4;
    }
}
