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
            + "Confirms previously generated schedule\n";

    public static final String SCHEDULE_SUCCESS = "Schedule for %s %s confirmed! See below for details\n\n%s\n\n%s\n\n";
    public static final String SCHEDULE_ALREADY_CONFIRMED = "Schedule for %s %s already confirmed! "
            + "See below for details\n\n%s\n\n%s\n\n";
    public static final String NO_SCHEDULE_YET = "No schedules found! Type <schedule> to make a schedule!";
    public static final String NOT_ENOUGH_DUTIES = "Not enough duties are filled to be confirmed!";

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
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
            return new CommandResult(NO_SCHEDULE_YET);
        } else if (!dummyMonth.allDutiesAreFiled()) {
            return new CommandResult(NOT_ENOUGH_DUTIES);
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
