package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DutyCalendar;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.ReadOnlyPersonnelDatabase;
import seedu.address.model.person.Person;

/**
 * An Immutable PersonnelDatabase that is serializable to JSON format.
 */
@JsonRootName(value = "personneldatabase")
class JsonSerializablePersonnelDatabase {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
//    private final JsonAdaptedDutyMonth currentMonth;
//    private final JsonAdaptedDutyMonth nextMonth;

    /**
     * Constructs a {@code JsonSerializablePersonnelDatabase} with the given persons and duty months.
     */
//    @JsonCreator
//    public JsonSerializablePersonnelDatabase(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
//                                             @JsonProperty("currentMonth") JsonAdaptedDutyMonth currentMonth,
//                                             @JsonProperty("nextMonth") JsonAdaptedDutyMonth nextMonth) {
//        this.persons.addAll(persons);
//        this.currentMonth = currentMonth;
//        this.nextMonth = nextMonth;
//    }

    /**
     * Converts a given {@code ReadOnlyPersonnelDatabase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePersonnelDatabase}.
     */
    public JsonSerializablePersonnelDatabase(ReadOnlyPersonnelDatabase source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
//        this.currentMonth = new JsonAdaptedDutyMonth(source.getDutyCalendar().getCurrentMonth());
//        this.nextMonth = new JsonAdaptedDutyMonth(source.getDutyCalendar().getNextMonth());
    }

    /**
     * Converts this personnel database into the model's {@code PersonnelDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PersonnelDatabase toModelType() throws IllegalValueException {
        PersonnelDatabase personnelDatabase = new PersonnelDatabase();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (personnelDatabase.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            personnelDatabase.addPerson(person);
        }
        ObservableList<Person> personList = personnelDatabase.getPersonList();
//        personnelDatabase.setDutyCalendar(new DutyCalendar(currentMonth.toModelType(personList),
//                nextMonth.toModelType(personList)));

        return personnelDatabase;
    }

}
