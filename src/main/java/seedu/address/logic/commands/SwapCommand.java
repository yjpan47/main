package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLOCATED_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUESTED_DATE;

import java.time.LocalDate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Requests a swap of duty dates for next month (for users who are personnel only)
 */
public class SwapCommand extends Command {

    public static final String COMMAND_WORD = "swap";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Requests for swap of duty on allocated date for another date in next month. "
            + "Parameters: "
            + PREFIX_ALLOCATED_DATE + "DATE (ddmmyy) "
            + PREFIX_REQUESTED_DATE + "DATE (ddmmyy) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALLOCATED_DATE + "010119 "
            + PREFIX_REQUESTED_DATE + "050119 ";

    public static final String MESSAGE_SUCCESS = "Request submitted.";
    public static final String MESSAGE_UNMATCHING_MONTH = "Allocated month and requested month don't match.";
    public static final String MESSAGE_INVALID_YEAR = "Invalid year input.";
    public static final String MESSAGE_INVALID_MONTH = "Invalid month input.";
    public static final String MESSAGE_INVALID_DAY_ALLOCATED = "You do not have duty on the input allocated date.";
    public static final String MESSAGE_INVALID_DAY_REQUESTED = "You already have duty on the input requested date.";
    public static final String MESSAGE_REQUEST_ALREADY_EXISTS = "You already have made this request.";

    private final LocalDate allocatedDate;
    private final LocalDate requestedDate;
    private final String userName;

    /**
     * Creates a SwapCommand to request to swap duties.
     */
    public SwapCommand(LocalDate allocatedDate, LocalDate requestedDate, String userName) {
        requireNonNull(allocatedDate);
        requireNonNull(requestedDate);
        this.allocatedDate = allocatedDate;
        this.requestedDate = requestedDate;
        this.userName = userName;
    }

    /**
     * Executes the SwapCommand.
     * @param model
     * @param history
     * @return the command result
     * @throws CommandException
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        int currentMonthIndex = model.getDutyCalendar().getCurrentMonth().getMonthIndex();
        int nextMonthIndex = model.getDutyCalendar().getNextMonth().getMonthIndex();
        int currentYear = model.getDutyCalendar().getCurrentYear();
        int allocatedDateDay = allocatedDate.getDayOfMonth();
        int requestedDateDay = requestedDate.getDayOfMonth();
        int allocatedDateMonth = allocatedDate.getMonthValue() - 1;
        int requestedDateMonth = requestedDate.getMonthValue() - 1;
        int allocatedDateYear = allocatedDate.getYear();
        int requestedDateYear = requestedDate.getYear();

        if (allocatedDateMonth != requestedDateMonth || allocatedDateYear != requestedDateYear) {
            throw new CommandException(MESSAGE_UNMATCHING_MONTH);
        }
        if (currentMonthIndex == 11 && currentYear != requestedDateYear - 1
                || currentMonthIndex < 11 && currentYear != requestedDateYear) {
            throw new CommandException(MESSAGE_INVALID_YEAR);
        }

        if (allocatedDateMonth != nextMonthIndex) {
            throw new CommandException(MESSAGE_INVALID_MONTH);
        }

        if (!model.getDutyCalendar().getNextMonth().isAssignedToDuty(userName, allocatedDateDay)) {
            throw new CommandException(MESSAGE_INVALID_DAY_ALLOCATED);
        }
        if (model.getDutyCalendar().getNextMonth().isAssignedToDuty(userName, requestedDateDay)) {
            throw new CommandException(MESSAGE_INVALID_DAY_REQUESTED);
        }
        if (model.checkSwapRequestExists(userName, allocatedDate, requestedDate)) {
            throw new CommandException(MESSAGE_REQUEST_ALREADY_EXISTS);
        }

        model.addSwapRequest(userName, allocatedDate, requestedDate);
        model.commitPersonnelDatabase();
        return new CommandResult(MESSAGE_SUCCESS);
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
        return other == this // short circuit if same object
                || other instanceof SwapCommand // instanceof handles nulls
                && userName.equals(((SwapCommand) other).userName)
                && allocatedDate.equals(((SwapCommand) other).allocatedDate)
                && requestedDate.equals(((SwapCommand) other).requestedDate);
    }
}
