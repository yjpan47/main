package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE_NRIC;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabaseWithDuties;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonnelDatabaseWithDuties(), new UserPrefs());
        expectedModel = new ModelManager(model.getPersonnelDatabase(), new UserPrefs());
    }

    @Test
    public void execute_viewShowsAllDutiesAPersonHas() throws CommandException {
        ViewCommand viewCommand = new ViewCommand(ALICE_NRIC);
        CommandResult expectedCommandResult = viewCommand.execute(model, commandHistory);
        assertCommandSuccess(viewCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewForNonExistentPerson_throwsCommandException() throws CommandException {
        ViewCommand viewCommand = new ViewCommand(" ");
        assertCommandFailure(viewCommand, model, commandHistory, Messages.MESSAGE_NO_USER);
    }

}
