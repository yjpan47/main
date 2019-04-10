package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;
/**
 * A command to block dates for the next month of duties.
 */
public class BlockDateCommand extends Command {

    public static final String COMMAND_WORD = "bd";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Blocks out certain duty dates for the upcoming "
            + "month for the current person using.\n"
            + "Parameters: Dates in numbers for the next month\n"
            + "Example: " + COMMAND_WORD + " 2 4 5 6 9 10";
    public static final String MESSAGE_BLOCK_DATES_SUCCESS = "Dates for next month have been successfully blocked!";
    public static final String MESSAGE_TOO_MANY_BLOCKED_DATES = "Too many dates to block, number of blocked days "
            + "should be less than 15";

    private final List<Integer> blockedDates;
    private final String userName;

    public BlockDateCommand(List<Integer> blockedDates, String userName) {
        this.blockedDates = blockedDates;
        this.userName = userName;
    }

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        Person person = model.findPerson(userName);
        DutyMonth nextMonth = model.getDutyCalendar().getNextMonth();
        if (blockedDates.size() > 15) {
            throw new CommandException(MESSAGE_TOO_MANY_BLOCKED_DATES);
        }
        for (Integer blockedDay : blockedDates) {
            nextMonth.addBlockedDay(person, blockedDay);
        }

        model.commitPersonnelDatabase();

        return new CommandResult(String.format(MESSAGE_BLOCK_DATES_SUCCESS));
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
        return blockedDates.equals(e.blockedDates) && userName.equals(e.userName);
    }
}
