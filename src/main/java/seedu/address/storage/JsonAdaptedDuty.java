package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import seedu.address.model.duty.Duty;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Duty}
 */
public class JsonAdaptedDuty {

    //public static final String MISSING_FIELD_MESSAGE_FORMAT = "Duty's %s field is missing!";

    private final int year;
    private final int monthIndex;
    private final int dayIndex;
    private final int dayOfWeekIndex;
    private final int capacity;
    private final int vacancies;
    private final int points;

    private List<String> persons = new ArrayList<>();

    /**
     * Constucts a {@code JsonAdaptedDuty} with the given duty details.
     */
    @JsonCreator

    public JsonAdaptedDuty(@JsonProperty("year") int year, @JsonProperty("monthIndex") int monthIndex,
                           @JsonProperty("dayIndex") int dayIndex, @JsonProperty("dayOfWeekIndex") int dayOfWeekIndex,
                           @JsonProperty("capacity") int capacity, @JsonProperty("vacancies") int vacancies,
                           @JsonProperty("points") int points, @JsonProperty("persons") List<String> persons) {
        this.year = year;
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
        this.dayOfWeekIndex = dayOfWeekIndex;
        this.capacity = capacity;
        this.vacancies = vacancies;
        this.points = points;
        if (persons != null) {
            this.persons.addAll(persons);
        }
    }

    /**
     * Converts a given {@code Duty} into this class for Jackson use.
     */
    public JsonAdaptedDuty(Duty source) {
        year = source.getYear();
        monthIndex = source.getMonthIndex();
        dayIndex = source.getDayIndex();
        dayOfWeekIndex = source.getDayOfWeekIndex();
        capacity = source.getCapacity();
        vacancies = source.getVacancies();
        points = source.getPoints();
        for (Person person : source.getPersons()) {
            this.persons.add(person.getNric().toString());
        }
    }

    /**
     * Converts this Jackson-friendly adapted duty object into the model's {@code Duty} object.
     */
    public Duty toModelType(ObservableList<Person> personList) {

        final int modelYear = year;
        final int modelMonthIndex = monthIndex;
        final int modelDayIndex = dayIndex;
        final int modelDayOfWeekIndex = dayOfWeekIndex;
        final int modelCapacity = capacity;
        final int modelVacancies = vacancies;
        final int modelPoints = points;

        final List<Person> modelPersonList = new ArrayList<>();
        for (String nric : persons) {
            for (Person person : personList) {
                if (person.getNric().toString().equals(nric)) {
                    modelPersonList.add(person);
                }
            }
        }

        return new Duty(modelYear, modelMonthIndex, modelDayIndex, modelDayOfWeekIndex,
                modelCapacity, modelVacancies, modelPoints, modelPersonList);
    }

}
