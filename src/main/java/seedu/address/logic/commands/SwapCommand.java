package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Requests a swap of duty dates (for non-admin users only)
 */
public class SwapCommand extends Command {

    public static final String COMMAND_WORD = "swap";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Requests for swap of duty on allocated date for another date. "
            + "Parameters: "
            + PREFIX_ALLOCATED_DATE + "DATE (ddmmyy) "
            + PREFIX_REQUESTED_DATE + "DATE (ddmmyy) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALLOCATED_DATE + "010119 "
            + PREFIX_REQUESTED_DATE + "050119 ";

    public static final String MESSAGE_SUCCESS = "Request submitted";
    public static final String MESSAGE_INVALID_YEAR = "Invalid year input.";
    public static final String MESSAGE_INVALID_MONTH = "Invalid month input.";
    public static final String MESSAGE_INVALID_DAY = "You do not have duty on the input allocated date";

    private final LocalDate allocatedDate;
    private final LocalDate requestedDate;
    private Optional<String> userName;

    /**
     * Creates a SwapCommand to swap the specified {@code Person}
     */
    public SwapCommand(LocalDate allocatedDate, LocalDate requestedDate) {
        requireNonNull(allocatedDate);
        requireNonNull(requestedDate);
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.userName = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String currentUser = model.getUserName();
        userName = Optional.of(currentUser);
        int currentMonthIndex = model.getDutyCalendar().getCurrentMonth().getMonthIndex();
        int nextMonthIndex = model.getDutyCalendar().getNextMonth().getMonthIndex();
        int currentYear = model.getDutyCalendar().getCurrentYear();
        int allocatedDateDay = allocatedDate.getDayOfMonth();
        int allocatedDateMonth = allocatedDate.getMonthValue();
        int requestedDateMonth = requestedDate.getMonthValue();
        int allocatedDateYear = allocatedDate.getYear();
        int requestedDateYear = requestedDate.getYear();
        if (currentMonthIndex < 12) {
            if (currentYear != allocatedDateYear || currentYear != requestedDateYear) {
                throw new CommandException(MESSAGE_INVALID_YEAR);
            }
        } else {
            if ((allocatedDateMonth == 1 && allocatedDateYear != currentYear + 1)
                    || (allocatedDateMonth == 12 && allocatedDateYear != currentYear)
                    || (requestedDateMonth == 1 && requestedDateYear != currentYear + 1)
                    || (requestedDateMonth == 12 && requestedDateYear != currentYear)) {
                throw new CommandException(MESSAGE_INVALID_YEAR);
            }
        }

        if (allocatedDateMonth != currentMonthIndex && allocatedDateMonth != nextMonthIndex
                || requestedDateMonth != currentMonthIndex
                && requestedDateMonth != nextMonthIndex) {
            throw new CommandException(MESSAGE_INVALID_MONTH);
        }

        if (currentMonthIndex == allocatedDateMonth
                && !model.getDutyCalendar().getCurrentMonth().isAssignedToDuty(currentUser, allocatedDateDay)
                || nextMonthIndex == allocatedDateMonth
                && !model.getDutyCalendar().getNextMonth().isAssignedToDuty(currentUser, allocatedDateDay)) {
            throw new CommandException(MESSAGE_INVALID_DAY);
        }

        model.addSwapRequest(currentUser, allocatedDate, requestedDate);
        model.commitPersonnelDatabase();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SwapCommand // instanceof handles nulls
                && userName.equals(((SwapCommand) other).userName)
                && allocatedDate.equals(((SwapCommand) other).allocatedDate)
                && requestedDate.equals(((SwapCommand) other).requestedDate);
    }
}
