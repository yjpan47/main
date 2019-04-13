package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.request.Request;

/**
 * Accepts a swap of duty dates (for users who are personnel only)
 */
public class AcceptSwapCommand extends Command {

    public static final String COMMAND_WORD = "acceptSwap";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accepts swap of duty from request list in browser panel. "
            + "Parameters: "
            + "INDEX"
            + "Example: " + COMMAND_WORD + " "
            + "2";

    public static final String MESSAGE_SUCCESS = "Accepted swap. Pending admin approval.";
    public static final String MESSAGE_INVALID_INDEX = "There is no swap request of this index.";
    public static final String MESSAGE_SAME_PERSON = "You were the one who requested this swap.";
    public static final String MESSAGE_INVALID_DAY_ALLOCATED =
            "You already have duty on the requester's allocated date.";
    public static final String MESSAGE_INVALID_DAY_REQUESTED =
            "You do not have duty on the requester's requested date.";

    private final String userName;
    private final Index index;

    /**
     * Creates an AcceptSwapCommand to accept the specified request.
     */
    public AcceptSwapCommand(String userName, Index index) {
        this.userName = userName;
        this.index = index;
    }

    /**
     * Executes the command
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Request> requests = model.getPersonnelDatabase().getRequestList();
        List<Request> filteredRequests = requests.stream().filter(req -> !req.isAccepterValid())
                .collect(Collectors.toList());
        if (index.getZeroBased() >= filteredRequests.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }
        Request targetRequest = filteredRequests.get(index.getZeroBased());
        LocalDate allocatedDate = targetRequest.getAllocatedDate();
        LocalDate requestedDate = targetRequest.getRequestedDate();
        DutyMonth nextMonth = model.getDutyCalendar().getNextMonth();
        int allocatedDateDay = allocatedDate.getDayOfMonth();
        int requestedDateDay = requestedDate.getDayOfMonth();


        if (userName.equals(targetRequest.getRequesterNric())) {
            throw new CommandException(MESSAGE_SAME_PERSON);
        }
        if (nextMonth.isAssignedToDuty(userName, allocatedDateDay)) {
            throw new CommandException(MESSAGE_INVALID_DAY_ALLOCATED);
        }
        if (!nextMonth.isAssignedToDuty(userName, requestedDateDay)) {
            throw new CommandException(MESSAGE_INVALID_DAY_REQUESTED);
        }

        targetRequest.setAccepter(model.findPerson(userName));
        model.commitPersonnelDatabase();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
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
                || other instanceof AcceptSwapCommand // instanceof handles nulls
                && userName.equals(((AcceptSwapCommand) other).userName)
                && index.equals(((AcceptSwapCommand) other).index);
    }
}
