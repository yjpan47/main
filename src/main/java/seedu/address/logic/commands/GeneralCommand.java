package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public interface GeneralCommand {
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException;
}
