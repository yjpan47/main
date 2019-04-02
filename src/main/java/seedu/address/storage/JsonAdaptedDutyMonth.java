package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
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

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "";

    private final int monthIndex;
    private final int firstDayWeekIndex;
    private final List<JsonAdaptedDuty> duties = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDutyMonth} with the given duty month details.
     */
    @JsonCreator

    public JsonAdaptedDutyMonth(@JsonProperty("monthIndex") int monthIndex,
                           @JsonProperty("firstDayWeekIndex") int firstDayWeekIndex,
                           @JsonProperty("numOfDays") int numOfDays,
                           @JsonProperty("duties") List<JsonAdaptedDuty> duties) {
        this.monthIndex = monthIndex;
        this.firstDayWeekIndex = firstDayWeekIndex;
        if (duties != null) {
            this.duties.addAll(duties);
        }
    }

    /**
     * Converts a given {@code DutyMonth} into this class for Jackson use.
     */
    public JsonAdaptedDutyMonth(DutyMonth source) {
        monthIndex = source.getMonthIndex();
        firstDayWeekIndex = source.getFirstDayWeekIndex();

        duties.addAll(source.getDuties().stream()
                .map(JsonAdaptedDuty::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted dutyMonth object into the model's {@code DutyMonth} object.
     */
    public DutyMonth toModelType (ObservableList<Person> personList) throws IllegalValueException {
        final List<Duty> monthDuties = new ArrayList<>();
        for (JsonAdaptedDuty duty : duties) {
            monthDuties.add(duty.toModelType(personList));
        }

        return new DutyMonth(monthIndex, firstDayWeekIndex, monthDuties);
    }

}
