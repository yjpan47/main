package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Admin command interface
 */
public interface AdminCommand {
    public CommandResult executeAdmin(Model model, CommandHistory commandHistory) throws CommandException;
}
