package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Test;

import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT,
                UiCommandInteraction.EXIT);
        assertCommandSuccess(new ExitCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeGeneralExitSuccess() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT,
                UiCommandInteraction.EXIT);
        assertCommandSuccessGeneral(new ExitCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
