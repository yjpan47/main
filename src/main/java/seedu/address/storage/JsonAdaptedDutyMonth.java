package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.duty.Duty;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link DutyMonth}
 */
public class JsonAdaptedDutyMonth {

    public static final String INVALID_BOOLEAN_VALUE = "Invalid boolean value!";

    private final String confirmed;
    private final int year;
    private final int monthIndex;
    private final int firstDayWeekIndex;
    private final List<JsonAdaptedDuty> duties = new ArrayList<>();
    private final List<JsonAdaptedHashMapUnit> hashMap = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDutyMonth} with the given duty month details.
     */
    @JsonCreator

    public JsonAdaptedDutyMonth(@JsonProperty("year") int year, @JsonProperty("monthIndex") int monthIndex,
                           @JsonProperty("firstDayWeekIndex") int firstDayWeekIndex,
                           @JsonProperty("confirmed") String confirmed,
                           @JsonProperty("duties") List<JsonAdaptedDuty> duties,
                                @JsonProperty("hashMap") List<JsonAdaptedHashMapUnit> hashMap) {
        this.year = year;
        this.monthIndex = monthIndex;
        this.firstDayWeekIndex = firstDayWeekIndex;
        this.confirmed = confirmed;
        if (duties != null) {
            this.duties.addAll(duties);
        }
        if (hashMap != null) {
            this.hashMap.addAll(hashMap);
        }
    }

    /**
     * Converts a given {@code DutyMonth} into this class for Jackson use.
     */
    public JsonAdaptedDutyMonth(DutyMonth source) {
        if (source.isConfirmed()) {
            confirmed = "true";
        } else {
            confirmed = "false";
        }
        year = source.getYear();
        monthIndex = source.getMonthIndex();
        firstDayWeekIndex = source.getFirstDayOfWeekIndex();

        if (source.getScheduledDuties() != null) {
            duties.addAll(source.getScheduledDuties().stream()
                    .map(JsonAdaptedDuty::new)
                    .collect(Collectors.toList()));
        }

        if (source.getBlockedDates() != null) {
            for (Map.Entry<Person, List<Integer>> mapValue : source.getBlockedDates().entrySet()) {
                String nric = mapValue.getKey().getNric().toString();
                List<Integer> dates = mapValue.getValue();
                hashMap.add(new JsonAdaptedHashMapUnit(nric, dates));
            }
        }
    }

    /**
     * Converts this Jackson-friendly adapted dutyMonth object into the model's {@code DutyMonth} object.
     */
    public DutyMonth toModelType (ObservableList<Person> personList) throws IllegalValueException {
        boolean modelConfirmed = false;
        if (this.confirmed.equals("true")) {
            modelConfirmed = true;
        } else if (!this.confirmed.equals("false")) {
            throw new IllegalValueException(INVALID_BOOLEAN_VALUE);
        }
        final List<Duty> monthDuties = new ArrayList<>();
        final HashMap<Person, List<Integer>> modelHashMap = new HashMap<>();
        for (JsonAdaptedDuty duty : duties) {
            monthDuties.add(duty.toModelType(personList));
        }
        for (JsonAdaptedHashMapUnit hashUnit : hashMap) {
            Person key = null;
            for (Person person : personList) {
                if (person.getNric().toString().equals(hashUnit.getPerson())) {
                    key = person;
                }
            }
            List<Integer> modelBlockedList = new ArrayList<>(hashUnit.getBlockedDates());
            if (key != null) {
                modelHashMap.put(key, modelBlockedList);
            }
        }

        return new DutyMonth(modelConfirmed, year, monthIndex, firstDayWeekIndex, monthDuties, modelHashMap);
    }

}
