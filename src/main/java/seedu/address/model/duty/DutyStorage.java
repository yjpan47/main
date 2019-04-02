package seedu.address.model.duty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.person.Person;

public class DutyStorage {

    static HashMap<Person, Integer> dutyPoints = new HashMap<>();
    static HashMap<Person, List<Duty>> dutyRecords = new HashMap<>();

    static void update(List<Duty> duties) {
        for (Duty duty : duties) {
            for (Person person : duty.getPersons()) {
                dutyPoints.putIfAbsent(person, 0);
                dutyPoints.replace(person, dutyPoints.get(person) + duty.getPoints());

                dutyRecords.putIfAbsent(person, new ArrayList<>());
                dutyRecords.get(person).add(duty);
            }
        }
    }

    static Comparator<Person> comparebyPoints() {
        return (p1, p2) -> {
            if (!dutyPoints.containsKey(p1) && !dutyPoints.containsKey(p2)) {
                return 0;
            } else if (!dutyPoints.containsKey(p1)) {
                return 1;
            } else if (!dutyPoints.containsKey(p2)) {
                return -1;
            } else {
                return dutyPoints.get(p1) - dutyPoints.get(p2);
            }
        };
    }
}

