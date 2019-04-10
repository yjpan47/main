package seedu.address.model.duty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
/**
 * DutyStorage class to see the points of each person and update Duties
 */
public class DutyStorage {

    private HashMap<Person, Integer> dutyPoints;
    private HashMap<Person, List<String>> dutyRecords;

    private HashMap<Person, Integer> prevDutyPoints;
    private HashMap<Person, List<String>> prevDutyRecords;

    public DutyStorage() {
        this.dutyPoints = new HashMap<>();
        this.dutyRecords = new HashMap<>();
        this.prevDutyPoints = new HashMap<>();
        this.prevDutyRecords = new HashMap<>();
    }

    public DutyStorage(HashMap<Person, Integer> dutyPoints, HashMap<Person, List<String>> dutyRecords,
                       HashMap<Person, Integer> prevDutyPoints, HashMap<Person, List<String>> prevDutyRecords) {
        this.dutyPoints = dutyPoints;
        this.dutyRecords = dutyRecords;

        this.prevDutyPoints = prevDutyPoints;
        this.prevDutyRecords = prevDutyRecords;
    }

    /**
     * Updates points for each duty done by person
     */
    public void update(List<Duty> duties) {

        // Store dutyPoints in a previous pointer
        this.prevDutyPoints.clear();
        for (Person person : this.dutyPoints.keySet()) {
            this.prevDutyPoints.put(person, this.dutyPoints.get(person));
        }

        // Store dutyRecords in a previous pointer
        this.prevDutyRecords.clear();
        for (Person person : this.dutyRecords.keySet()) {
            this.prevDutyRecords.put(person, new ArrayList<>(this.dutyRecords.get(person)));
        }

        for (Duty duty : duties) {
            for (Person person : duty.getPersons()) {
                this.dutyPoints.putIfAbsent(person, 0);
                this.dutyPoints.replace(person, this.dutyPoints.get(person) + duty.getPoints());

                this.dutyRecords.putIfAbsent(person, new ArrayList<>());
                this.dutyRecords.get(person).add(duty.toString());
            }
        }
    }

    public void undo() {
        this.dutyPoints.clear();
        for (Person person : this.prevDutyPoints.keySet()) {
            this.dutyPoints.put(person, this.prevDutyPoints.get(person));
        }

        this.dutyRecords.clear();
        for (Person person : this.prevDutyRecords.keySet()) {
            this.dutyRecords.put(person, new ArrayList<>(this.prevDutyRecords.get(person)));
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

    public HashMap<Person, Integer> getPrevDutyPoints() {
        return prevDutyPoints;
    }

    public HashMap<Person, List<String>> getPrevDutyRecords() {
        return prevDutyRecords;
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

    public void removePerson(Person remove) {
        for (Person p : this.dutyPoints.keySet()) {
            if (remove.getNric().toString().equals(p.getNric().toString())) {
                this.dutyPoints.remove(p);
                break;
            }
        }

        for (Person p : this.prevDutyPoints.keySet()) {
            if (remove.getNric().toString().equals(p.getNric().toString())) {
                this.prevDutyPoints.remove(p);
                break;
            }
        }

        for (Person p : this.dutyRecords.keySet()) {
            if (remove.getNric().toString().equals(p.getNric().toString())) {
                this.dutyRecords.remove(p);
                break;
            }
        }

        for (Person p : this.prevDutyRecords.keySet()) {
            if (remove.getNric().toString().equals(p.getNric().toString())) {
                this.prevDutyRecords.remove(p);
                break;
            }
        }



    }

    public void replacePerson(Person personToEdit, Person editedPerson) {
        Set<Person> persons = new HashSet<>();
        persons.addAll(this.dutyPoints.keySet());
        persons.addAll(this.dutyRecords.keySet());
        persons.addAll(this.prevDutyPoints.keySet());
        persons.addAll(this.prevDutyRecords.keySet());

        Person target = null;
        for (Person p : persons) {
            if (personToEdit.getNric().toString().equals(p.getNric().toString())) {
                target = p;
                break;
            }
        }

        if (target != null) {
            this.dutyPoints.put(editedPerson, this.dutyPoints.get(target));
            this.dutyRecords.put(editedPerson, this.dutyRecords.get(target));
            this.prevDutyPoints.put(editedPerson, this.prevDutyPoints.get(target));
            this.prevDutyRecords.put(editedPerson, this.prevDutyRecords.get(target));

            this.dutyPoints.remove(target);
            this.dutyRecords.remove(target);
            this.prevDutyPoints.remove(target);
            this.prevDutyRecords.remove(target);
        }
    }
}

