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
     * Constucts a {@code JsonAdaptedHashMapUnit} with the given hashmap details.
     */
    public JsonAdaptedDutyStorage(DutyStorage source) {
        Set<Person> persons = new HashSet<>();
        persons.addAll(source.getDutyPoints().keySet());
        persons.addAll(source.getDutyRecords().keySet());
        for (Person person : persons) {
            String nric = person.getNric().toString();
            int dutyPoints = source.getDutyPoints().getOrDefault(person, 0);
            List<String> dutyRecords = source.getDutyRecords().getOrDefault(person, new ArrayList<>());
            JsonAdaptedDutyStoragePerson jsonAdaptedDutyStoragePerson = new JsonAdaptedDutyStoragePerson(nric, dutyPoints, dutyRecords);
            this.dutyStorageList.add(jsonAdaptedDutyStoragePerson);
            System.out.println(dutyStorageList.size());
        }
    }

    /**
     * Converts this Jackson-friendly adapted dutyMonth object into the model's {@code DutyMonth} object.
     */
    public DutyStorage toModelType (ObservableList<Person> personList) {
        HashMap<Person, Integer> dutyPoints = new HashMap<>();
        HashMap<Person, List<String>> dutyRecords = new HashMap<>();
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
            }
        }
        return new DutyStorage(dutyPoints, dutyRecords);
    }
}
