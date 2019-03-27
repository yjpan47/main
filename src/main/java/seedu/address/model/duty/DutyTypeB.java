package seedu.address.model.duty;

import java.util.List;

import seedu.address.model.person.Person;

/**
 * Duty Type B - Friday
 */
public class DutyTypeB extends Duty {

    /**
     * Constructs a duty of Type B
     */
    public DutyTypeB(int monthIndex, int dayIndex, int weekIndex) {
        super(monthIndex, dayIndex, weekIndex);
        this.setNumOfVacancies(2);
        this.setPointsAwards(3);
    }

    public DutyTypeB(int monthIndex, int dayIndex, int weekIndex, List<Person> personList) {
        super(monthIndex, dayIndex, weekIndex, personList);
        this.setNumOfVacancies(2);
        this.setPointsAwards(3);
    }
}
