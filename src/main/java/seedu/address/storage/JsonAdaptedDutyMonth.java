package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.calendar.Duty;
import seedu.address.model.calendar.DutyMonth;
import seedu.address.model.person.UniquePersonList;

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
    public JsonAdaptedDuty toModelType(UniquePersonList personList) {
        final List<Duty> monthDuties = new ArrayList<>();
        for (JsonAdaptedDuty duty : duties) {
            monthDuties.add(duty.toModelType());
        }

        final int modelMonthIndex = monthIndex;
        final int modelFirstDayWeekIndex = firstDayWeekIndex;

        final List<Duty> duties = new ArrayList<>(monthDuties);
        return new DutyMonth(monthIndex, firstDayWeekIndex);
    }

}
