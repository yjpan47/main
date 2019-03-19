package seedu.address.logic.commands;
/**
 * Admin command interface
 */
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public interface AdminCommand {
    public CommandResult executeAdmin(Model model, CommandHistory commandHistory) throws CommandException;
}
