package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Command to switch calendar view to next Month
 */
public class ViewNextCommand extends Command {

    public static final String COMMAND_WORD = "viewNext";

    public static final String MESSAGE_SUCCESS = "Listed Next Month Duty";
    /**
     * Executes the ViewNextCommand
     */
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiCommandInteraction.CALENDAR_NEXT);
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
