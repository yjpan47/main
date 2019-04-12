package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureGeneral;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;

import seedu.address.commons.core.Messages;
// import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
//import seedu.address.model.DutyCalendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeAdminValidIndexUnfilteredListSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccessAdmin(INDEX_FIRST_PERSON);
        assertExecutionSuccessAdmin(INDEX_THIRD_PERSON);
        assertExecutionSuccessAdmin(lastPersonIndex);
    }

    @Test
    public void executeGeneralValidIndexUnfilteredListSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccessGeneral(INDEX_FIRST_PERSON);
        assertExecutionSuccessGeneral(INDEX_THIRD_PERSON);
        assertExecutionSuccessGeneral(lastPersonIndex);
    }

    @Test
    public void executeAdminInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailureAdmin(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeGeneralInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailureGeneral(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeAdminValidIndexFilteredListSuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccessAdmin(INDEX_FIRST_PERSON);
    }

    @Test
    public void executeGeneralValidIndexFilteredListSuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertExecutionSuccessGeneral(INDEX_FIRST_PERSON);
    }

    @Test
    public void executeAdminInvalidIndexFilteredListFailure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getPersonnelDatabase().getPersonList().size());

        assertExecutionFailureAdmin(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeGeneralInvalidIndexFilteredListFailure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getPersonnelDatabase().getPersonList().size());

        assertExecutionFailureGeneral(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes (admin) a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected person is set to the person at {@code index} in the filtered person list.
     */
    private void assertExecutionSuccessAdmin(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());
        expectedModel.setSelectedPerson(model.getFilteredPersonList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes (admin) a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailureAdmin(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Executes (general) a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected person is set to the person at {@code index} in the filtered person list.
     */
    private void assertExecutionSuccessGeneral(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());
        expectedModel.setSelectedPerson(model.getFilteredPersonList().get(index.getZeroBased()));

        assertCommandSuccessGeneral(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes (general) a {@code SelectCommand} with the given {@code index},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailureGeneral(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailureGeneral(selectCommand, model, commandHistory, expectedMessage);
    }
}
