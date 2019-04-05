package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.duty.DutySettings;
/**
 * Allows admin to modify settings of the duties to allow different points and number of personnel for duties
 */
public class DutySettingsCommand extends Command {

    public static final String COMMAND_WORD = "settings";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Update manpower needed and points awarded for duties "
            + "Example: " + COMMAND_WORD + "w/Sunday m/3 p/4";

    public static final String VIEW_SETTINGS = "Duty Settings for each day of the week:\n\n%s";
    public static final String CHANGE_SETTINGS_SUCCESS = "Settings successfully changed!\n\n%s";

    private boolean isView;
    private int dayOfWeek;
    private int capacity;
    private int points;

    public DutySettingsCommand() {
        this.isView = true;
    }

    public DutySettingsCommand(int dayOfweek, int capacity, int points) {
        this.dayOfWeek = dayOfweek;
        this.capacity = capacity;
        this.points = points;
        this.isView = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        DutySettings dutySettings = model.getDutySettings();
        if (isView) {
            return new CommandResult(String.format(VIEW_SETTINGS,
                    dutySettings.printDayOfWeek()));
        } else {
            dutySettings.setCapacity(this.dayOfWeek, this.capacity);
            dutySettings.setPoints(this.dayOfWeek, this.points);
            return new CommandResult(String.format(CHANGE_SETTINGS_SUCCESS,
                    dutySettings.printDayOfWeek()));
        }
    }

    @Override
    public CommandResult executeAdmin(Model model, CommandHistory commandHistory) throws CommandException {
        return execute(model, commandHistory);
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
