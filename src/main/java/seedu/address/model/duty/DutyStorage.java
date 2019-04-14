package seedu.address.model.duty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Person;
/**
 * DutyStorage class to see the points of each person and update Duties
 */
public class DutyStorage {

    private static final String MESSAGE_RECORD_REWARDED = "Reward: %d points added";
    private static final String MESSAGE_RECORD_PENALIZED = "Penalty: %d points deducted";

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

    public DutyStorage(DutyStorage dutyStorage) {
        this.dutyPoints = new HashMap<>(dutyStorage.getDutyPoints());
        this.dutyRecords = new HashMap<>();
        for (Person person : dutyStorage.getDutyRecords().keySet()) {
            this.dutyRecords.put(person, new ArrayList<>(dutyStorage.getDutyRecords().get(person)));
        }
        this.prevDutyPoints = new HashMap<>(dutyStorage.getPrevDutyPoints());
        this.prevDutyRecords = new HashMap<>();
        for (Person person : dutyStorage.getPrevDutyRecords().keySet()) {
            this.prevDutyRecords.put(person, new ArrayList<>(dutyStorage.getPrevDutyRecords().get(person)));
        }
    }

    /**
     * Adds a person into dutyStorage
     * @param person the person to be added
     */
    public void addPerson(Person person) {
        this.dutyPoints.put(person, 0);
        this.dutyRecords.put(person, new ArrayList<>());
        this.prevDutyPoints.put(person, 0);
        this.prevDutyRecords.put(person, new ArrayList<>());
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
    /**
     * Undoes the storage for dutyPoints
     */
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
        sb.append("--- POINTS ACCUMULATED ----\n");
        for (Person person : this.dutyPoints.keySet()) {
            int points = this.dutyPoints.get(person);
            sb.append(String.format("%s | Points: %d\n",
                    person, points));
        }
        return sb.toString();
    }

    /**
     * Prints the duties for that month
     */
    public String printDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(person).append("\n");
        sb.append(String.format("Points : %d\n", this.dutyPoints.getOrDefault(person, 0)));
        sb.append("--- RECORDS ---\n");
        for (String dutyDetails : this.dutyRecords.getOrDefault(person, new ArrayList<>())) {
            sb.append(dutyDetails).append("\n");
        }
        return sb.toString();
    }

    /**
     * Removes Person for Duty Storage
     */
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
    /**
     * Replaces person for duty storage
     */
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

    /**
     * Rewards points to a person
     * @param target the person to be rewarded
     * @param points the number of points to be rewarded
     */
    public void reward(Person target, int points) {
        for (Person person : this.dutyPoints.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.dutyPoints.replace(person, this.dutyPoints.get(person) + points);
            }
        }
        for (Person person : this.dutyRecords.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.dutyRecords.get(person).add(String.format(MESSAGE_RECORD_REWARDED, points));
            }
        }

        for (Person person : this.prevDutyPoints.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.prevDutyPoints.replace(person, this.prevDutyPoints.get(person) + points);
            }
        }
        for (Person person : this.prevDutyRecords.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.prevDutyRecords.get(person).add(String.format(MESSAGE_RECORD_REWARDED, points));
            }
        }
    }

    /**
     * Penalize points to a person
     * @param target the person to be penalized
     * @param points the number of points to be penalized
     */
    public void penalize(Person target, int points) {
        for (Person person : this.dutyPoints.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.dutyPoints.replace(person, this.dutyPoints.get(person) - points);
            }
        }

        for (Person person : this.dutyRecords.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.dutyRecords.get(person).add(String.format(MESSAGE_RECORD_PENALIZED, points));
            }
        }

        for (Person person : this.prevDutyPoints.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.prevDutyPoints.replace(person, this.prevDutyPoints.get(person) - points);
                return;
            }
        }
        for (Person person : this.prevDutyRecords.keySet()) {
            if (person.getNric().toString().equals(target.getNric().toString())) {
                this.prevDutyRecords.get(person).add(String.format(MESSAGE_RECORD_PENALIZED, points));
            }
        }
    }
}

