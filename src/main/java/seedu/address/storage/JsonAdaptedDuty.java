package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.duty.Duty;
import seedu.address.model.duty.DutyTypeA;
import seedu.address.model.duty.DutyTypeB;
import seedu.address.model.duty.DutyTypeC;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Duty}
 */
public class JsonAdaptedDuty {

    public static final String WRONG_TYPE_MESSAGE_FORMAT = "Duty has a wrong type!";

    private final int monthIndex;
    private final int dayIndex;
    private final int weekIndex;

    private List<String> persons = new ArrayList<>();

    private final String type;

    /**
     * Constucts a {@code JsonAdaptedDuty} with the given duty details.
     */
    @JsonCreator

    public JsonAdaptedDuty(@JsonProperty("monthIndex") int monthIndex, @JsonProperty("dayIndex") int dayIndex,
                           @JsonProperty("weekIndex") int weekIndex, @JsonProperty("type") String type,
                           @JsonProperty("persons") List<String> persons) {
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
            this.persons.add(person.getNric().toString());
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
    public Duty toModelType(ObservableList<Person> personList) throws IllegalValueException {
        final int modelMonthIndex = monthIndex;
        final int modelDayIndex = dayIndex;
        final int modelWeekIndex = weekIndex;

        final List<Person> modelPersonList = new ArrayList<>();
        for (String nric : persons) {
            for (Person person : personList) {
                if (person.getNric().toString().equals(nric)) {
                    modelPersonList.add(person);
                }
            }
        }

        if (type.equals("A")) {
            return new DutyTypeA(modelMonthIndex, modelDayIndex, modelWeekIndex, modelPersonList);
        } else if (type.equals("B")) {
            return new DutyTypeB(modelMonthIndex, modelDayIndex, modelWeekIndex, modelPersonList);
        } else if (type.equals("C")) {
            return new DutyTypeC(modelMonthIndex, modelDayIndex, modelWeekIndex, modelPersonList);
        } else {
            throw new IllegalValueException(String.format(WRONG_TYPE_MESSAGE_FORMAT));
        }
    }

}
