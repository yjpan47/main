package seedu.address.logic.commands;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class BlockDateCommand extends Command {

    public static final String COMMAND_WORD = "bd";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Blocks out certain duty dates for the upcoming "
            + "month for the current person using.\n"
            + "Parameters: Dates in numbers for the next month\n"
            + "Example: " + COMMAND_WORD + "2 4 5 6 9 10";

    private final int[] blockedDates;
    private final int index;

    public BlockDateCommand(int index, int[] blockedDates) {
        this.blockedDates = blockedDates;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("testing");
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
}
