package seedu.address.model.calendar;

import java.util.List;

import seedu.address.model.person.Person;

/**
 * Duty Type A - Saturday or Sunday
 */
public class DutyTypeA extends Duty {

    /**
     * Constructs a calendar of Type A
     */
    public DutyTypeA(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.setNumOfVacancies(3);
        this.setPointsAwards(4);
    }

    public DutyTypeA(int monthIndex, int dayIndex, int weekIndex, List<Person> personList) {
        super(monthIndex, dayIndex, weekIndex, personList);
        this.setNumOfVacancies(3);
        this.setPointsAwards(4);
    }
}
