package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NO_AUTHORITY;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserType;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DutyCalendar;
import seedu.address.model.Model;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.ReadOnlyPersonnelDatabase;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutySettings;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final String CALLED_ERROR = "This method should not be called.";

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().buildReduced();

        CommandResult commandResult = new AddCommand(validPerson).executeAdmin(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().buildReduced();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.executeAdmin(modelStub, commandHistory);
    }

    @Test
    public void executeGeneralPersonAcceptedByModelThrowsCommandException() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().buildReduced();
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NO_AUTHORITY);
        new AddCommand(validPerson).executeGeneral(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").buildReduced();
        Person bob = new PersonBuilder().withName("Bob").buildReduced();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public Path getPersonnelDatabaseFilePath() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void setPersonnelDatabaseFilePath(Path personnelDatabaseFilePath) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void setPersonnelDatabase(ReadOnlyPersonnelDatabase newData) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public ReadOnlyPersonnelDatabase getPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public DutyCalendar getDutyCalendar() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void sortPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public boolean hasPerson(String nric) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public boolean canUndoPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public boolean canRedoPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void undoPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void redoPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void commitPersonnelDatabase() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public UserType findAccount(String userName, String password) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public Person findPerson(String userName) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public DutyMonth getCurrentDutyMonth() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public DutyMonth getNextDutyMonth() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public DutyMonth getDummyNextMonth() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public DutyStorage getDutyStorage() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void addSwapRequest(String nric, LocalDate allocatedDate, LocalDate requestedDate) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void setDutySettings(DutySettings dutySettings) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public DutySettings getDutySettings() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void scheduleDutyForNextMonth() {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public void deleteRequestsWithPerson(Person person) {
            throw new AssertionError(CALLED_ERROR);
        }

        @Override
        public boolean checkSwapRequestExists(String nric, LocalDate allocatedDate, LocalDate requestedDate) {
            throw new AssertionError(CALLED_ERROR);
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public DutyCalendar getDutyCalendar() {
            return new DutyCalendar();
        }

        @Override
        public void commitPersonnelDatabase() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyPersonnelDatabase getPersonnelDatabase() {
            return new PersonnelDatabase();
        }
    }

}
