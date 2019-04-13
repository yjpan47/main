package seedu.address.logic;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.ViewCurrentCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;
import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewCurrentCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandSuccess(new ViewCurrentCommand(), model, new CommandHistory(), new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.CALENDAR_CURRENT), model);
    }
}
