package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutyStorage;

/**
 * Schedules the upcoming duties for the month
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedules the duties for the upcoming month.\n";

    public static final String SCHEDULE_SUCCESS =
            "Schedule Success! Note that this schedule has yet been confirmed!\n"
            + "Type <confirm> to confirm this schedule or <schedule> to reassign!\n"
            + "Scroll below for details\n\n%s\n%s\n";

    public static final String SCHEDULE_ALREADY_CONFIRMED = "Schedule for %s %s already confirmed!\n"
            + "Scroll below for details\n\n%s\n%s\n";

    public static final String PERSON_LIST_EMPTY = "You cannot schedule with an empty person list! ";


    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        DutyMonth nextMonth = model.getDutyCalendar().getNextMonth();
        DutyStorage dutyStorage = model.getDutyStorage();
        if (nextMonth.isConfirmed()) {
            return new CommandResult(String.format(SCHEDULE_ALREADY_CONFIRMED,
                    DateUtil.getMonth(nextMonth.getMonthIndex()), nextMonth.getYear(),
                    nextMonth.printDuties(), dutyStorage.printPoints()));
        }

        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(PERSON_LIST_EMPTY);
        }

        model.scheduleDutyForNextMonth();
        DutyMonth dummy = model.getDummyNextMonth();

        return new CommandResult(String.format(SCHEDULE_SUCCESS,
                dummy.printDuties(),
                dummy.printPoints(dutyStorage)));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
