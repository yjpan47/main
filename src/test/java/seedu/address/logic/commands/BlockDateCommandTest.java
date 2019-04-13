package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.BlockDateCommand.MESSAGE_TOO_MANY_BLOCKED_DATES;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import java.util.ArrayList;
import java.util.Arrays;
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
        Integer[] arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        List<Integer> blockedDates = new ArrayList<>();
        Collections.addAll(blockedDates,arr);
        assertCommandFailure(new BlockDateCommand(blockedDates,"Admin"), model, new CommandHistory(),Messages.MESSAGE_NO_AUTHORITY_PARSE);
    }
}