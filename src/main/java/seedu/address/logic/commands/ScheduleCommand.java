package seedu.address.logic.commands;
/**
 * Schedules the upcoming duties for the month
 */
public class ScheduleCommand {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules the duties for the upcoming month."
            + "taking into account the block out dates of each guard duty personnel and their extras. "
            + "It will sort by available dates and distribute duties accordingly. "
            + "Parameters: MONTH INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String SCHEDULE_SUCCESS = "Scheduled: %1$s";


}
