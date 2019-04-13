package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.Map;
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
    private final String needsRollover;
    private final int year;
    private final int monthIndex;
    private final int firstDayWeekIndex;
    private final List<JsonAdaptedDuty> duties = new ArrayList<>();
    private final JsonAdaptedBlockHashMap blockedDays;

    /**
     * Constructs a {@code JsonAdaptedDutyMonth} with the given duty month details.
     */
    @JsonCreator

    public JsonAdaptedDutyMonth(@JsonProperty("year") int year, @JsonProperty("monthIndex") int monthIndex,
                           @JsonProperty("firstDayWeekIndex") int firstDayWeekIndex,
                           @JsonProperty("confirmed") String confirmed,
                           @JsonProperty("needsRollover") String needsRollover,
                           @JsonProperty("duties") List<JsonAdaptedDuty> duties,
                           @JsonProperty("blockedDays") JsonAdaptedBlockHashMap blockedDays) {
        this.year = year;
        this.monthIndex = monthIndex;
        this.firstDayWeekIndex = firstDayWeekIndex;
        this.confirmed = confirmed;
        this.needsRollover = needsRollover;
        if (duties != null) {
            this.duties.addAll(duties);
        }
        this.blockedDays = blockedDays;
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
        needsRollover = "true";
        year = source.getYear();
        monthIndex = source.getMonthIndex();
        firstDayWeekIndex = source.getFirstDayOfWeekIndex();

        if (source.getScheduledDuties() != null) {
            duties.addAll(source.getScheduledDuties().stream()
                    .map(JsonAdaptedDuty::new)
                    .collect(Collectors.toList()));
        }

        blockedDays = new JsonAdaptedBlockHashMap(source.getBlockedDates());
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
        boolean modelRollover = true;
        if (this.needsRollover.equals("true")) {
            modelRollover = true;
        } else if (!this.needsRollover.equals("false")) {
            modelRollover = false;
        }
        final List<Duty> monthDuties = new ArrayList<>();
        for (JsonAdaptedDuty duty : duties) {
            monthDuties.add(duty.toModelType(personList));
        }
        final HashMap<Person, List<Integer>> modelBlockedDays = blockedDays.toModelType(personList);

        return new DutyMonth(modelConfirmed, modelRollover, year, monthIndex, firstDayWeekIndex, monthDuties,
                modelBlockedDays);
    }

}
