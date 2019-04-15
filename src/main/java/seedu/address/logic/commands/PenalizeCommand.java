package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutyStorage;
import seedu.address.model.person.Person;

/**
 * Deduct duty points from persons
 */
public class PenalizeCommand extends Command {

    public static final String COMMAND_WORD = "penalize";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": penalize duty points for selected persons\n"
            + "Example: penalize i/1 3 4 p/20";

    public static final String MESSAGE_PENALIZE_SUCCESS =
            "Successfully penalized %d points for the following people: \n%s\n\n%s";

    public static final String MESSAGE_INVALID_INDEX = "The person index %d is invalid";

    private final int points;
    private final Set<Index> indexes;

    public PenalizeCommand(int points, Set<Index> indexes) {
        this.points = points;
        this.indexes = indexes;
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personTargeted = new ArrayList<>();
        DutyStorage dutyStorage = model.getDutyStorage();
        for (Index index : this.indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getZeroBased() + 1));
            }
            Person target = lastShownList.get(index.getZeroBased());
            personTargeted.add(target);
            dutyStorage.penalize(target, this.points);
        }
        model.commitPersonnelDatabase();
        return new CommandResult(String.format(MESSAGE_PENALIZE_SUCCESS,
                this.points, personTargeted, dutyStorage.printPoints()));
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
