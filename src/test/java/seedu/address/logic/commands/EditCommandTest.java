package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.UserType;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeAdminAllFieldsSpecifiedUnfilteredListSuccess() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor, UserType.DEFAULT_ADMIN_USERNAME);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeGeneralAllFieldsSpecifiedUnfilteredListSuccess() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        Person toChange = model.getFilteredPersonList().get(0);
        EditCommand editCommand = new EditCommand(descriptor, toChange.getNric().value);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(toChange, editedPerson);
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccessGeneral(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeAdminSomeFieldsSpecifiedUnfilteredListSuccess() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor, UserType.DEFAULT_ADMIN_USERNAME);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeGeneralSomeFieldsSpecifiedUnfilteredListSuccess() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(descriptor, lastPerson.getNric().value);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccessGeneral(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeAdminNoFieldSpecifiedUnfilteredListSuccess() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor(), UserType.DEFAULT_ADMIN_USERNAME);
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeGeneralNoFieldSpecifiedSuccess() {
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(new EditPersonDescriptor(), editedPerson.getNric().value);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccessGeneral(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeAdminFilteredListSuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build(), UserType.DEFAULT_ADMIN_USERNAME);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeAdminDuplicatePersonUnfilteredListFailure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor, UserType.DEFAULT_ADMIN_USERNAME);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void executeAdminDuplicatePersonFilteredListFailure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getPersonnelDatabase().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build(), UserType.DEFAULT_ADMIN_USERNAME);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void executeAdminInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor, UserType.DEFAULT_ADMIN_USERNAME);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeAdminInvalidPersonIndexFilteredListFailure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPersonnelDatabase().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build(), UserType.DEFAULT_ADMIN_USERNAME);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeAdminUndoRedoValidIndexUnfilteredListSuccess() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor, UserType.DEFAULT_ADMIN_USERNAME);
        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.commitPersonnelDatabase();

        // edit -> first person edited
        editCommand.executeAdmin(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoPersonnelDatabase();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoPersonnelDatabase();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeGeneralUndoRedoSuccess() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(descriptor, personToEdit.getNric().value);
        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.commitPersonnelDatabase();

        // edit -> user edited
        editCommand.executeGeneral(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoPersonnelDatabase();
        assertCommandSuccessGeneral(new UndoCommand(),
                model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> user edited again
        expectedModel.redoPersonnelDatabase();
        assertCommandSuccessGeneral(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeAdminUndoRedoInvalidIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor, UserType.DEFAULT_ADMIN_USERNAME);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */

    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor, UserType.DEFAULT_ADMIN_USERNAME);
        Model expectedModel = new ModelManager(new PersonnelDatabase(model.getPersonnelDatabase()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.commitPersonnelDatabase();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.executeAdmin(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoPersonnelDatabase();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoPersonnelDatabase();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY, UserType.DEFAULT_ADMIN_USERNAME);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor, UserType.DEFAULT_ADMIN_USERNAME);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY,
                UserType.DEFAULT_ADMIN_USERNAME)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB,
                UserType.DEFAULT_ADMIN_USERNAME)));

        // No index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB,
                UserType.DEFAULT_ADMIN_USERNAME)));

        // different nric -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB,
                "TestingUser")));

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String personNric = personToEdit.getNric().value;
        final EditCommand standardCommandGeneral = new EditCommand(DESC_AMY, personNric);
        // same values -> returns true
        EditCommand commandWithSameValuesGeneral = new EditCommand(copyDescriptor, personNric);
        assertTrue(standardCommandGeneral.equals(commandWithSameValuesGeneral));
        // same object -> returns true
        assertTrue(standardCommandGeneral.equals(standardCommandGeneral));

        // different types -> returns false
        assertFalse(standardCommandGeneral.equals(new ClearCommand()));

        // with Index ->returns false
        assertFalse(standardCommandGeneral.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY, personNric)));

        // different descriptor -> returns false
        assertFalse(standardCommandGeneral.equals(new EditCommand(INDEX_FIRST_PERSON,
                DESC_BOB, personNric)));

        //different nric -> returns false
        assertFalse(standardCommandGeneral.equals(new EditCommand(INDEX_FIRST_PERSON,
                DESC_BOB, UserType.DEFAULT_ADMIN_USERNAME)));
    }

}
