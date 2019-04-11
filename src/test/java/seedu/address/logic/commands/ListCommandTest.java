package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.ListCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getPersonnelDatabase(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.PEOPLE_LIST);
        assertCommandSuccess(new ListCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.PEOPLE_LIST);
        assertCommandSuccess(new ListCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeGeneralListIsNotFilteredShowsSameList() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.PEOPLE_LIST);
        assertCommandSuccessGeneral(new ListCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeGeneralListIsFilteredShowsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.PEOPLE_LIST);
        assertCommandSuccessGeneral(new ListCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
