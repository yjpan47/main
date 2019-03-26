package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.calendar.Duty;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Duty}
 */
public class JsonAdaptedDuty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "";

    private final int monthIndex;
    private final int dayIndex;
    private final int weekIndex;

    private List<Nric> persons = new ArrayList<>();

    private final String type;

    /**
     * Constucts a {@code JsonAdaptedDuty} with the given duty details.
     */
    @JsonCreator

    public JsonAdaptedDuty(@JsonProperty("monthIndex") int monthIndex, @JsonProperty("dayIndex") int dayIndex,
                           @JsonProperty("weekIndex") int weekIndex, @JsonProperty("type") String type,
                           @JsonProperty("persons") List<Nric> persons) {
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
        this.weekIndex = weekIndex;
        if (persons != null) {
            this.persons.addAll(persons);
        }
        this.type = type;
    }

    /**
     * Converts a given {@code Duty} into this class for Jackson use.
     */
    public JsonAdaptedDuty(Duty source) {
        monthIndex = source.getMonthIndex();
        weekIndex = source.getWeekIndex();
        dayIndex = source.getDayIndex();

        for (Person person : source.getPersons()) {
            this.persons.add(person.getNric());
        }

        switch(source.getPointsAwards()) {
            case 4:
                type = "A";
                break;
            case 3:
                type = "B";
                break;
            case 2:
                type = "C";
                break;
            default:
                type = "Error";
        }
    }

    /**
     * Converts this Jackson-friendly adapted duty object into the model's {@code Duty} object.
     */
    public Duty toModelType() {
        
        
        
    }

}
