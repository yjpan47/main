package seedu.address.logic.commands;

import static seedu.address.logic.ViewNextCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;

import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



public class SortCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandSuccess(new SortCommand(), model, new CommandHistory(),
                new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.PEOPLE_LIST), model);
        assertCommandSuccessGeneral(new SortCommand(), model, new CommandHistory(),
                new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.PEOPLE_LIST), model);
    }
}
