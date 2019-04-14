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

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : "
            + "View and update manpower needed and points awarded for duties on given day of week\n"
            + "Manpower can range from 1 to 10. Points can range from 1 to 1000.\n"
            + "Example: " + COMMAND_WORD + " d/Sun m/3 p/4";

    public static final String MESSAGE_VIEW_SETTINGS = "Duty Settings for each day of the week:\n\n%s";
    public static final String MESSAGE_CHANGE_SETTINGS_SUCCESS = "Settings successfully changed!"
            + "Note that changes will only affect the next time a schedule is confirmed.\n"
            + "Current confirmed schedules will not be affected.\n\n%s";

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
    public CommandResult executeAdmin(Model model, CommandHistory history) throws CommandException {
        DutySettings dutySettings = model.getDutySettings();
        if (isView) {
            return new CommandResult(String.format(MESSAGE_VIEW_SETTINGS,
                    dutySettings.printDayOfWeek()));
        } else {
            dutySettings.setCapacity(this.dayOfWeek, this.capacity);
            dutySettings.setPoints(this.dayOfWeek, this.points);
            return new CommandResult(String.format(MESSAGE_CHANGE_SETTINGS_SUCCESS,
                    dutySettings.printDayOfWeek()));
        }
    }

    @Override
    public CommandResult executeGeneral(Model model, CommandHistory commandHistory) throws CommandException {
        throw new CommandException(Messages.MESSAGE_NO_AUTHORITY);
    }
}
