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

    private HashMap<Person, Integer> dutyPoints = new HashMap<>();
    private HashMap<Person, List<Duty>> dutyRecords = new HashMap<>();

    /**
     * Updates points for each duty done by person
     */
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

    public HashMap<Person, Integer> getDutyPoints() {
        return dutyPoints;
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

    public void removePerson(Person person) {
        if (this.dutyPoints.containsKey(person) && this.dutyRecords.containsKey(person)) {
            this.dutyPoints.remove(person);
            this.dutyRecords.remove(person);
        }
    }

    public void replaceperson(Person personToEdit, Person editedPerson) {
        if (this.dutyPoints.containsKey(personToEdit) && this.dutyRecords.containsKey(personToEdit)) {
            this.dutyPoints.put(editedPerson, this.dutyPoints.get(personToEdit));
            this.dutyRecords.put(editedPerson, this.dutyRecords.get(personToEdit));

            this.dutyPoints.remove(personToEdit);
            this.dutyRecords.remove(personToEdit);
        }
    }
}

