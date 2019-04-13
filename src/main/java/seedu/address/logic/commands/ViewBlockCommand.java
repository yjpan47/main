package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;
/**
 * Command to view blocked Dates for the next month
 */
public class ViewBlockCommand extends Command {

    public static final String COMMAND_WORD = "viewblock";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " View blocked Dates for next month";
    public static final String MESSAGE_BLOCKED_DATES = "Below are the blocked dates for next month";
    private final String userName;

    public ViewBlockCommand(String userName) {
        this.userName = userName;
    }
    /**
     * Executes the command
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Person person = model.findPerson(userName);
        DutyMonth nextMonth = model.getDutyCalendar().getNextMonth();
        HashMap<Person, List<Integer>> blockedDays = nextMonth.getBlockedDates();
        List<Integer> personsBlockedDays = blockedDays.get(person);

        return new CommandResult(String.format(MESSAGE_BLOCKED_DATES + "\n" + personsBlockedDays));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);

    }

}

