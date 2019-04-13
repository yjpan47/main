package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of Hashmap of block dates
 */
public class JsonAdaptedBlockHashMap {

    private final List<String> personNricList = new ArrayList<>();
    private final List<String> dateList = new ArrayList<>();

    /**
     * Constructs a Hashmap of block dates with the given hashmap details.
     */
    @JsonCreator

    public JsonAdaptedBlockHashMap(@JsonProperty("personList") List<String> personNricList,
                                   @JsonProperty("dateList") List<String> dateList) {
        if (personNricList != null) {
            this.personNricList.addAll(personNricList);
        }
        if (dateList != null) {
            this.dateList.addAll(dateList);
        }
    }

    /**
     * Converts a given Hashmap of block dates into this class for Jackson use.
     */
    public JsonAdaptedBlockHashMap(HashMap<Person, List<Integer>> source) {
        for (Person key : source.keySet()) {
            this.personNricList.add(key.getNric().toString());
        }
        for (List<Integer> blockedDays : source.values()) {
            String days = "";
            for (int day : blockedDays) {
                days = days + day + " ";
            }
            this.dateList.add(days);
        }
    }

    /**
     * Converts this Jackson-friendly adapted Hashmap object into the model's Hashmap object.
     */
    public HashMap<Person, List<Integer>> toModelType (ObservableList<Person> personList) {
        HashMap<Person, List<Integer>> modelHashMap = new HashMap<>();

        if (!personNricList.isEmpty() && !dateList.isEmpty()) {
            for (int i = 0; i < personNricList.size(); i++) {
                Person modelPerson = null;
                for (Person person : personList) {
                    if (person.getNric().toString().equals(personNricList.get(i))) {
                        modelPerson = person;
                    }
                }
                String[] dateListSplit = dateList.get(i).split(" ");
                List<Integer> modelDateList = new ArrayList<>();
                for (int j = 0; j < dateListSplit.length; j++) {
                    modelDateList.add(Integer.parseInt(dateListSplit[j]));
                }
                if (modelPerson != null && !modelDateList.isEmpty()) {
                    modelHashMap.put(modelPerson, modelDateList);
                }
            }
        }

        return modelHashMap;
    }

}
