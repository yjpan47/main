package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Unconfirm the schedules. For debugging purposes only.
 */
public class UnconfirmCommand extends Command {
    public static final String COMMAND_WORD = "unconfirm";

    public static final String SCHEDULE_SUCCESS = "Unconfirm successful!";

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.getDutyCalendar().unconfirm();
        model.getDutyCalendar().getDutyStorage().undo();
        model.commitPersonnelDatabase();
        return new CommandResult(SCHEDULE_SUCCESS);
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
