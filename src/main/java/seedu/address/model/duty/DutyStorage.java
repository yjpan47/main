package seedu.address.model.duty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.person.Person;
/**
 * DutyStorage class to see the points of each person and update Duties
 */
public class DutyStorage {

    private HashMap<Person, Integer> dutyPoints;
    private HashMap<Person, List<String>> dutyRecords;



    public DutyStorage() {
        this.dutyPoints = new HashMap<>();
        this.dutyRecords = new HashMap<>();
    }

    public DutyStorage(HashMap<Person, Integer> dutyPoints, HashMap<Person, List<String>> dutyRecords) {
        this.dutyPoints = dutyPoints;
        this.dutyRecords = dutyRecords;
    }

    /**
     * Updates points for each duty done by person
     */
    public void update(List<Duty> duties) {
        for (Duty duty : duties) {
            for (Person person : duty.getPersons()) {
                this.dutyPoints.putIfAbsent(person, 0);
                this.dutyPoints.replace(person, this.dutyPoints.get(person) + duty.getPoints());

                this.dutyRecords.putIfAbsent(person, new ArrayList<>());
                this.dutyRecords.get(person).add(duty.toString());
            }
        }
    }

    public int getPoints(Person person) {
        return dutyPoints.getOrDefault(person, 0);
    }

    public HashMap<Person, Integer> getDutyPoints() {
        return dutyPoints;
    }

    public HashMap<Person, List<String>> getDutyRecords() {
        return dutyRecords;
    }

    /**
     * Prints the points accumulated by each person
     */

    public String printPoints() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Points Accumulated ----\n");
        for (Person person : this.dutyPoints.keySet()) {
            int points = this.dutyPoints.get(person);
            sb.append(String.format("%3s %-20s %3d\n",
                    person.getRank(), person.getName(),
                    points));
        }
        return sb.toString();
    }
    /**
     * Comparator method to compare person by points
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

