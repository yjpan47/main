package seedu.address.logic.commands;

//import static seedu.address.logic.commands.BlockDateCommand.MESSAGE_DUTY_CONFIRMED;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureGeneral;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.ConfirmScheduleCommand.NO_SCHEDULE_YET;
//import static seedu.address.logic.commands.ConfirmScheduleCommand.SCHEDULE_ALREADY_CONFIRMED;
//import static seedu.address.logic.commands.ConfirmScheduleCommand.SCHEDULE_SUCCESS;
//import static seedu.address.testutil.TypicalPersons.GENERAL_DAN_USERNAME;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;

import seedu.address.commons.core.Messages;
//import seedu.address.commons.util.DateUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



public class ConfirmScheduleCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());

    @Test
    public void execute() {

        assertCommandFailureGeneral(new ScheduleCommand(), model, new CommandHistory(), Messages.MESSAGE_NO_AUTHORITY);

    }
}
