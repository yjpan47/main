package seedu.address.model.calendar;

import java.util.List;

import seedu.address.model.person.Person;

/**
 * Duty Type C - Monday to Thursday
 */
public class DutyTypeC extends Duty {

    /**
     * Constructs a calendar of Type C
     */
    public DutyTypeC(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.setNumOfVacancies(2);
        this.setPointsAwards(2);
    }

    public DutyTypeC(int monthIndex, int dayIndex, int weekIndex, List<Person> personList) {
        super(monthIndex, dayIndex, weekIndex, personList);
        this.setNumOfVacancies(2);
        this.setPointsAwards(2);
    }
}
