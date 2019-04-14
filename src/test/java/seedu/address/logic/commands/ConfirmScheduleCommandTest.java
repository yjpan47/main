package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ConfirmScheduleCommand.MESSAGE_NO_SCHEDULE_YET;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.Assert;

public class ConfirmScheduleCommandTest {

    private static final String MESSAGE_SCHEDULE_CONFIRMED = "Schedule for %s %s already confirmed!";

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void executeTest() throws CommandException {
        ScheduleCommand scheduleCommand = new ScheduleCommand();
        ConfirmScheduleCommand confirmScheduleCommand = new ConfirmScheduleCommand();
        Assert.assertThrows(CommandException.class, ()
            -> confirmScheduleCommand.executeGeneral(model, commandHistory));
        assertEquals(confirmScheduleCommand.executeAdmin(model, commandHistory),
                new CommandResult(MESSAGE_NO_SCHEDULE_YET));
        scheduleCommand.executeAdmin(model, commandHistory);
        assertEquals(model.getNextDutyMonth().getScheduledDuties().size(), 0);
        assertFalse(model.getNextDutyMonth().isConfirmed());
        confirmScheduleCommand.executeAdmin(model, commandHistory);
        assertEquals(model.getNextDutyMonth().getScheduledDuties().size(),
                model.getNextDutyMonth().getNumOfDays());
        assertTrue(model.getNextDutyMonth().isConfirmed());
        assertEquals(confirmScheduleCommand.executeAdmin(model, commandHistory)
                .getFeedbackToUser().split("\n")[0], String.format(MESSAGE_SCHEDULE_CONFIRMED,
                DateUtil.getMonth(model.getDutyCalendar().getNextMonth().getMonthIndex()),
                model.getDutyCalendar().getNextMonth().getYear()));
    }
}
