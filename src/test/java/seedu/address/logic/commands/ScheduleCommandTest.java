package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ScheduleCommandTest {

    private final Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    private final Model expectedModel = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    private final CommandHistory commandHistory = new CommandHistory();

    void executeAdmin() { }

    void setUp() { }
}
