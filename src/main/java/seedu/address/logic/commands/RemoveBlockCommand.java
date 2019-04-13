package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;
/**
 * Removes all blocked Dates for next month
 */
public class RemoveBlockCommand extends Command {

    public static final String COMMAND_WORD = "removeblock";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Undo blocked Dates for next month";
    public static final String MESSAGE_REMOVED_BLOCKED_DATES = "Undo Block Dates successful!";
    private final String userName;

    public RemoveBlockCommand(String userName) {
        this.userName = userName;
    }
    /**
     * Executes the command
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Person person = model.findPerson(userName);
        DutyMonth nextMonth = model.getDutyCalendar().getNextMonth();
        nextMonth.removeBlockedDays(person);
        model.commitPersonnelDatabase();
        return new CommandResult(String.format(MESSAGE_REMOVED_BLOCKED_DATES));
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
