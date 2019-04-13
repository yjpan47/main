package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link DutyStorage}
 */
public class JsonAdaptedDutyStorage {

    private final List<JsonAdaptedDutyStoragePerson> dutyStorageList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDutyStoragePerson} with the given details.
     */
    @JsonCreator
    public JsonAdaptedDutyStorage(@JsonProperty("dutyStorageList") List<JsonAdaptedDutyStoragePerson> dutyStorageList) {
        this.dutyStorageList.addAll(dutyStorageList);
    }

    /**
     * Constucts a {@code JsonAdaptedDutyStorage} with the given hashmap details.
     */
    public JsonAdaptedDutyStorage(DutyStorage source) {
        Set<Person> persons = new HashSet<>();
        persons.addAll(source.getDutyPoints().keySet());
        persons.addAll(source.getDutyRecords().keySet());
        persons.addAll(source.getPrevDutyPoints().keySet());
        persons.addAll(source.getPrevDutyRecords().keySet());
        for (Person person : persons) {
            String nric = person.getNric().toString();
            int dutyPoints = source.getDutyPoints().getOrDefault(person, 0);
            List<String> dutyRecords = source.getDutyRecords().getOrDefault(person, new ArrayList<>());
            int prevDutyPoints = source.getPrevDutyPoints().getOrDefault(person, 0);
            List<String> prevDutyRecords = source.getPrevDutyRecords().getOrDefault(person, new ArrayList<>());
            JsonAdaptedDutyStoragePerson jsonAdaptedDutyStoragePerson = new JsonAdaptedDutyStoragePerson(nric,
                    dutyPoints, dutyRecords, prevDutyPoints, prevDutyRecords);
            this.dutyStorageList.add(jsonAdaptedDutyStoragePerson);
        }
    }

    /**
     * Converts this Jackson-friendly adapted dutyStorage object into the model's {@code DutyStorage} object.
     */
    public DutyStorage toModelType (ObservableList<Person> personList) {
        HashMap<Person, Integer> dutyPoints = new HashMap<>();
        HashMap<Person, List<String>> dutyRecords = new HashMap<>();
        HashMap<Person, Integer> prevDutyPoints = new HashMap<>();
        HashMap<Person, List<String>> prevDutyRecords = new HashMap<>();
        for (JsonAdaptedDutyStoragePerson jsonAdaptedDutyStoragePerson : this.dutyStorageList) {
            Person person = null;
            for (Person p : personList) {
                if (p.getNric().toString().equals(jsonAdaptedDutyStoragePerson.getNric())) {
                    person = p;
                }
            }
            if (person != null) {
                dutyPoints.put(person, jsonAdaptedDutyStoragePerson.getDutyPoints());
                dutyRecords.put(person, jsonAdaptedDutyStoragePerson.getDutyRecords());
                prevDutyPoints.put(person, jsonAdaptedDutyStoragePerson.getPrevDutyPoints());
                prevDutyRecords.put(person, jsonAdaptedDutyStoragePerson.getPrevDutyRecords());
            }
        }
        return new DutyStorage(dutyPoints, dutyRecords, prevDutyPoints, prevDutyRecords);
    }
}
