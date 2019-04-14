package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserType;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutySettings;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Duty settings.
     */
    DutySettings getDutySettings();

    /**
     * Sets the user prefs' Duty settings.
     */
    void setDutySettings(DutySettings dutySettings);

    /**
     * Returns the user prefs' personnel database file path.
     */
    Path getPersonnelDatabaseFilePath();

    /**
     * Sets the user prefs' personnel database file path.
     */
    void setPersonnelDatabaseFilePath(Path personnelDatabaseFilePath);

    /**
     * Replaces personnel database data with the data in {@code personnelDatabase}.
     */
    void setPersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase);

    /**
     * Returns the PersonnelDatabase
     */
    ReadOnlyPersonnelDatabase getPersonnelDatabase();

    /**
     * Returns Duty Storage
     */
    DutyStorage getDutyStorage();

    /**
     * Sorts the PersonnelDatabase by name
     */
    void sortPersonnelDatabase();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the personnel database.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the personnel database,
     * using the NRIC value
     */
    boolean hasPerson(String nric);

    /**
     * Deletes the given person.
     * The person must exist in the personnel database.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the personnel database.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the personnel database.
     * The person identity of {@code editedPerson} must not be the same as another existing person
     * in the personnel database.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the duty for dates */
    //ObservableList<Person> getDutyForDates();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */

    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous personnel database states to restore.
     */
    boolean canUndoPersonnelDatabase();

    /**
     * Returns true if the model has undone personnel database states to restore.
     */
    boolean canRedoPersonnelDatabase();

    /**
     * Restores the model's personnel database to its previous state.
     */
    void undoPersonnelDatabase();

    /**
     * Restores the model's personnel database to its previously undone state.
     */
    void redoPersonnelDatabase();

    /**
     * Saves the current personnel database state for undo/redo.
     */
    void commitPersonnelDatabase();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    /**
     * Returns UserType of account if found, null otherwise.
     */
    UserType findAccount(String userName, String password);

    /**
     * Returns Person of account if found, null otherwise.
     */
    Person findPerson(String userName);

    /**
     * Returns the duty calendar of current personnel database.
     */
    DutyCalendar getDutyCalendar();

    /**
     * Add a swap request to the model
     */
    void addSwapRequest(String nric, LocalDate allocatedDate, LocalDate requestedDate);
    /**
     * Checks a swap request to the model
     */
    boolean checkSwapRequestExists(String nric, LocalDate allocatedDate, LocalDate requestedDate);

    /**
     * Delete a person from requests containing that person.
     */
    void deleteRequestsWithPerson(Person personToDelete);

    /**
     * Schedule the next duty month in the model.
     */
    void scheduleDutyForNextMonth();
    /**
     * Returns DutyMonth for next month
     */
    DutyMonth getNextDutyMonth();

    /**
     * Returns DutyMonth for current month
     */
    DutyMonth getCurrentDutyMonth();

    /**
     * Returns dummy DutyMonth
     */
    DutyMonth getDummyNextMonth();
}
