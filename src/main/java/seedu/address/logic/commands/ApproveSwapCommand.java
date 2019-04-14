package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;
import seedu.address.model.request.Request;




/**
 * Approves a swap of duty dates (for admin users only)
 */
public class ApproveSwapCommand extends Command {

    public static final String COMMAND_WORD = "approveSwap";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Approves swap of duty from request list that can be viewed using viewSwaps. \n"
            + "Parameters: "
            + "INDEX \n"
            + "Example: " + COMMAND_WORD + " "
            + "2";

    public static final String MESSAGE_SUCCESS = "Approved swap. Schedule and points have been updated.";
    public static final String MESSAGE_INVALID_INDEX = "There is no swap request of this index.";

    private final Index index;

    /**
     * Creates an ApproveSwapCommand to approve the specified swap request
     */
    public ApproveSwapCommand(Index index) {
        this.index = index;
    }

    /**
     * Executes the ApproveSwapCommand.
     * @param model
     * @param history
     * @return the command result
     * @throws CommandException
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Request> requests = model.getPersonnelDatabase().getRequestList();
        List<Request> filteredRequests = requests.stream().filter(Request::isAccepterValid)
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
        Person requester = model.findPerson(targetRequest.getRequesterNric());
        Person accepter = model.findPerson(targetRequest.getAccepterNric());
        nextMonth.swap(requester, accepter, allocatedDateDay, requestedDateDay,
                model.getDutyCalendar().getDutyStorage());

        model.commitPersonnelDatabase();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ApproveSwapCommand // instanceof handles nulls
                && index.equals(((ApproveSwapCommand) other).index);
    }
}
