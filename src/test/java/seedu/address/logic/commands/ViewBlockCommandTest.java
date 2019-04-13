package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.ViewBlockCommand.MESSAGE_TOO_MANY_BLOCKED_DATES;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewBlockCommand.
 */
public class ViewBlockCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new ViewBlockCommand("Admin"), model, new CommandHistory(),
                Messages.MESSAGE_NO_AUTHORITY);
    }
}
