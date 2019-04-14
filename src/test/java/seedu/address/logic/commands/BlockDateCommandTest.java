package seedu.address.logic.commands;

import static seedu.address.logic.commands.BlockDateCommand.MESSAGE_BLOCK_DATES_SUCCESS;
import static seedu.address.logic.commands.BlockDateCommand.MESSAGE_DUTY_CONFIRMED;
import static seedu.address.logic.commands.BlockDateCommand.MESSAGE_TOO_MANY_BLOCKED_DATES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureGeneral;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessGeneral;
import static seedu.address.testutil.TypicalPersons.GENERAL_DAN_USERNAME;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for BlockDateCommand.
 */
public class BlockDateCommandTest {

    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());


    @Test
    public void execute() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        List<Integer> blockedDates = new ArrayList<>();
        Collections.addAll(blockedDates, arr);
        assertCommandFailureGeneral(new BlockDateCommand(blockedDates, GENERAL_DAN_USERNAME), model,
                new CommandHistory(), MESSAGE_TOO_MANY_BLOCKED_DATES);

        assertCommandFailure(new BlockDateCommand(blockedDates, GENERAL_DAN_USERNAME), model,
                new CommandHistory(), Messages.MESSAGE_NO_AUTHORITY_PARSE);

        blockedDates.remove(1);
        blockedDates.remove(1);
        blockedDates.remove(1);
        blockedDates.remove(1);
        blockedDates.remove(1);
        blockedDates.remove(1);
        assertCommandSuccessGeneral(new BlockDateCommand(blockedDates, GENERAL_DAN_USERNAME), model,
                new CommandHistory(), MESSAGE_BLOCK_DATES_SUCCESS, model);
        model.getNextDutyMonth().confirm();
        assertCommandFailureGeneral(new BlockDateCommand(blockedDates, GENERAL_DAN_USERNAME), model,
                new CommandHistory(), MESSAGE_DUTY_CONFIRMED);

    }
}
