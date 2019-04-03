package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.Duty;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing %1$s's duties!\n";
    public static final String MESSAGE_NOSUCHPERSON = "This person does not exist in the personnel database";

    private final String userName;

    public ViewCommand(String userName) {
        this.userName = userName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String DUTY_LIST = MESSAGE_SUCCESS;

        if (!model.hasPerson(userName)) {
            throw new CommandException(MESSAGE_NOSUCHPERSON);
        }

        DutyMonth currentMonth = model.getDutyCalendar().getCurrentMonth();
        int dutyCounter = 0;

        for (Duty duty : currentMonth.getScheduledDuties()) {
            for (Person person : duty.getPersons()) {
                if (person.getNric().toString().equals(userName)) {
                    dutyCounter++;
                    DUTY_LIST = DUTY_LIST + "Duty " + dutyCounter + ": Month: " + duty.getMonthString() + ", Day: " +
                            duty.getDayIndex() + " with" + duty.getPersonsString(userName) + " \n";
                }
            }
        }

        return new CommandResult(String.format(DUTY_LIST, userName));
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory history) throws CommandException {
        return execute(model, history);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && userName.equals(((ViewCommand) other).userName));
    }
}
