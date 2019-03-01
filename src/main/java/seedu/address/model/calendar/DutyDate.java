package seedu.address.model.calendar;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Represents a day with duty details
 */
public class DutyDate {

    private ArrayList<Person> dutyForThatDay;
    private String dutyCommander;

    public DutyDate(String dutyCommander) {
        this.dutyForThatDay = new ArrayList<>();
        this.dutyCommander = dutyCommander;
    }

    public void addPersonToDuty(Person person) {
        dutyForThatDay.add(person);
    }

    public void removePersonFromDuty(Person person) {
        dutyForThatDay.remove(person);
    }

}
