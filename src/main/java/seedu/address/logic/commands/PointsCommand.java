package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Person;
/**
 * A command to view the points of the people in the Personnel Database
 */
public class PointsCommand extends Command {

    public static final String COMMAND_WORD = "points";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": displays the duty points accumulated by "
            + "each person.\nRecords (i.e. duties, points rewarded, points penalized) of each person "
            + "can also be viewed.\nExample: points 2";

    private final Index index;

    public PointsCommand() {
        this.index = null;
    }

    public PointsCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        DutyStorage dutyStorage = model.getDutyStorage();

        if (this.index == null) {
            return new CommandResult(dutyStorage.printPoints());
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person = lastShownList.get(index.getZeroBased());
        return new CommandResult(dutyStorage.printDetails(person));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
