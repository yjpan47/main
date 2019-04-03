package seedu.address.model.duty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

import seedu.address.model.person.Person;

public class DutyStorage {

    public HashMap<Person, Integer> dutyPoints = new HashMap<>();
    public HashMap<Person, List<Duty>> dutyRecords = new HashMap<>();

    public void update(List<Duty> duties) {
        for (Duty duty : duties) {
            for (Person person : duty.getPersons()) {
                this.dutyPoints.putIfAbsent(person, 0);
                this.dutyPoints.replace(person, this.dutyPoints.get(person) + duty.getPoints());

                this.dutyRecords.putIfAbsent(person, new ArrayList<>());
                this.dutyRecords.get(person).add(duty);
            }
        }
    }

    public int getPoints(Person person) {
        return dutyPoints.getOrDefault(person, 0);
    }

    /*
    public List<Duty> getDuties(Person person) {
        return dutyRecords.getOrDefault(person, Lists.emptyList());
    }
    */

    public Comparator<Person> comparebyPoints() {
        return (p1, p2) -> {
            if (!this.dutyPoints.containsKey(p1) && !this.dutyPoints.containsKey(p2)) {
                return 0;
            } else if (!this.dutyPoints.containsKey(p1)) {
                return 1;
            } else if (!this.dutyPoints.containsKey(p2)) {
                return -1;
            } else {
                return this.dutyPoints.get(p1) - this.dutyPoints.get(p2);
            }
        };
    }
}

