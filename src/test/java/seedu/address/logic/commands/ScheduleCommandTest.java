package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.Assert;

public class ScheduleCommandTest {

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
        Assert.assertThrows(CommandException.class, () -> scheduleCommand.executeGeneral(model, commandHistory));
        assertEquals(model.getNextDutyMonth().getScheduledDuties().size(), 0);
        assertNull(model.getDummyNextMonth());
        scheduleCommand.executeAdmin(model, commandHistory);
        assertEquals(model.getNextDutyMonth().getScheduledDuties().size(), 0);
        assertEquals(model.getDummyNextMonth().getScheduledDuties().size(), model.getNextDutyMonth().getNumOfDays());
    }
}
