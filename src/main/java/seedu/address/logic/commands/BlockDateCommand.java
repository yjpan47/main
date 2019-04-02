package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class BlockDateCommand extends Command {

    public static final String COMMAND_WORD = "bd";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Blocks out certain duty dates for the upcoming "
            + "month for the current person using.\n"
            + "Parameters: Dates in numbers for the next month\n"
            + "Example: " + COMMAND_WORD + "2 4 5 6 9 10";

    private final List<Integer> blockedDates;

    public BlockDateCommand(List<Integer> blockedDates) {
        this.blockedDates = blockedDates;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Person person = model.getCurrentUser();
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
}
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BlockDateCommand)) {
            return false;
        }

        // state check
        BlockDateCommand e = (BlockDateCommand) other;
        return blockedDates.equals(e.blockedDates);
    }
