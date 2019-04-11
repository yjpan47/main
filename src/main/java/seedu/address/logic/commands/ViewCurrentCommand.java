package seedu.address.logic.commands;

import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ViewCurrentCommand extends Command {

    public static final String COMMAND_WORD = "viewCurrent";

    public static final String MESSAGE_SUCCESS = "Listed Current Month Duty";

    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.CALENDAR_CURRENT);
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) {
        return execute(model, history);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) {
        return execute(model, history);
    }
}
