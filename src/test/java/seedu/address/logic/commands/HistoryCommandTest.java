package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HistoryCommandTest {
    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void executeAdmin() {
        assertCommandSuccess(new HistoryCommand(), model, history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);

        String command1 = "clear";
        history.add(command1);
        assertCommandSuccess(new HistoryCommand(), model, history,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(), model, history, expectedMessage, expectedModel);
    }

    @Test
    public void executeGeneral() {
        assertCommandSuccessGeneral(new HistoryCommand(), model, history, HistoryCommand.MESSAGE_NO_HISTORY,
                expectedModel);

        String command1 = "clear";
        history.add(command1);
        assertCommandSuccessGeneral(new HistoryCommand(), model, history,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommd";
        String command3 = "select 2";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccessGeneral(new HistoryCommand(), model, history, expectedMessage, expectedModel);
    }

}
