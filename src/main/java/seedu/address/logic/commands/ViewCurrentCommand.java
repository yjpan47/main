package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Command to switch calendar view to current Month
 */
public class ViewCurrentCommand extends Command {

    public static final String COMMAND_WORD = "viewCurrent";

    public static final String MESSAGE_SUCCESS = "Listed Current Month Duty";
    /**
     * Executes the ViewCurrentCommand
     */
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
