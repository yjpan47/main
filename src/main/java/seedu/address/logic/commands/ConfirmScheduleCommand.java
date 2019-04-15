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
 * Set the schedule generated into stone by confirming it
 */
public class ConfirmScheduleCommand extends Command {

    public static final String COMMAND_WORD = "confirm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Confirms the schedule that was previously generated\n";

    public static final String SCHEDULE_SUCCESS = "Schedule for %s %s confirmed!\n"
            + "Open calendar and type <viewNext> to view next month's schedule!\n"
            + "Scroll below for details\n\n%s\n%s\n";
    public static final String SCHEDULE_ALREADY_CONFIRMED = "Schedule for %s %s already confirmed!\n"
            + "Open calendar and type <viewNext> to view next month's schedule!\n"
            + "Scroll below for details\n\n"
            + "%s\n%s\n";
    public static final String MESSAGE_NO_SCHEDULE_YET = "No schedules found! Tye <schedule> to make a schedule!";
    public static final String MESSAGE_SCHEDULE_NOT_FILLED = "Not all dates in recent schedule are filled!\n"
            + "Use <settings> command to change manpower requirements or <add> command to add more persons.";

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) {
        requireNonNull(model);
        DutyMonth nextMonth = model.getDutyCalendar().getNextMonth();
        DutyStorage dutyStorage = model.getDutyStorage();

        if (nextMonth.isConfirmed()) {
            return new CommandResult(String.format(SCHEDULE_ALREADY_CONFIRMED,
                    DateUtil.getMonth(nextMonth.getMonthIndex()),
                    nextMonth.getYear(),
                    nextMonth.printDuties(),
                    dutyStorage.printPoints()));
        }

        DutyMonth dummyMonth = model.getDutyCalendar().getDummyNextMonth();

        if (dummyMonth == null || dummyMonth.getScheduledDuties() == null) {
            return new CommandResult(MESSAGE_NO_SCHEDULE_YET);
        }

        if (dummyMonth.getUnfilledDuties().size() > 0) {
            return new CommandResult(MESSAGE_SCHEDULE_NOT_FILLED);
        }

        dutyStorage.update(dummyMonth.getScheduledDuties());
        model.getDutyCalendar().confirm();
        model.commitPersonnelDatabase();
        return new CommandResult(String.format(SCHEDULE_SUCCESS,
                DateUtil.getMonth(dummyMonth.getMonthIndex()),
                dummyMonth.getYear(),
                dummyMonth.printDuties(),
                dutyStorage.printPoints()));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
