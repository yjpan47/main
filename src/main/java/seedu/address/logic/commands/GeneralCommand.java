package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * General command interface
 */
public interface GeneralCommand {
    /**
     * Executes the command for a General user and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException;
}
