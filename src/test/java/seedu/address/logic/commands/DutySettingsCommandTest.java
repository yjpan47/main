package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureGeneral;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DutySettingsCommand.MESSAGE_VIEW_SETTINGS;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DutySettingsCommandTest {

    private final Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandSuccess(new DutySettingsCommand(), model, new CommandHistory(),
                new CommandResult(String.format(MESSAGE_VIEW_SETTINGS,
                        model.getDutySettings().printDayOfWeek())), model);
        assertCommandFailureGeneral(new DutySettingsCommand(), model, new CommandHistory(),
                Messages.MESSAGE_NO_AUTHORITY);
    }
}
