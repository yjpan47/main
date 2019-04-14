package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Command to view the current paired swaps. (For admin users)
 */
public class ViewSwapsCommand extends Command {

    public static final String COMMAND_WORD = "viewSwaps";

    public static final String MESSAGE_SUCCESS = "List of Swap Requests Pending Approval:\n";

    /**
     * Executes the ViewSwapsCommand.
     * @param model
     * @param history
     * @return the command result
     */
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Request> requests = model.getPersonnelDatabase().getRequestList();
        List<Request> filteredRequests = requests.stream().filter(Request::isAccepterValid)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);
        int counter = 0;
        for (Request request : filteredRequests) {
            counter++;
            sb.append(counter).append(". ").append(request.toString()).append("\n");
        }
        sb.append("\nEnter approveSwap INDEX to approve or rejectSwap INDEX to reject.\n");

        return new CommandResult(sb.toString());
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }
}
