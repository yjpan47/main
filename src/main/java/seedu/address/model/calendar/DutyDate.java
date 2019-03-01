package seedu.address.model.calendar;

import seedu.address.model.person.Person;

import java.util.ArrayList;
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
