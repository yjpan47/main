package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.DutyMonth;

public class ScheduleCommandTest {

    public static final String COMMAND_WORD = "schedule" ;
    private final Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    void execute() { }

    void setUp() { }
}
