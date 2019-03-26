package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.DutyMonth;
import seedu.address.model.person.Person;

/**
 * Schedules the upcoming duties for the month
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules the duties for the upcoming month."
            + "taking into account the block out dates of each guard duty personnel and their extras. "
            + "It will sort by available dates and distribute duties accordingly. \n";

    public static final String SCHEDULE_SUCCESS = "Month of %1$s successfully scheduled!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getFilteredPersonList();
        DutyMonth dutyMonth = model.getDutyCalendar().getCurrentMonth();
        model.getFilteredPersonList();
        dutyMonth.schedule();
        return new CommandResult(String.format(SCHEDULE_SUCCESS, dutyMonth.getMonth()));
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory commandHistory) throws CommandException {
        return execute(model, commandHistory);
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
