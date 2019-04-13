package seedu.address.logic;

import static seedu.address.testutil.TypicalPersons.ADMIN_TAN_PASSWORD;
import static seedu.address.testutil.TypicalPersons.ADMIN_TAN_USERNAME;
import static seedu.address.testutil.TypicalPersons.GENERAL_DAN_PASSWORD;
import static seedu.address.testutil.TypicalPersons.GENERAL_DAN_USERNAME;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.UserType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginTest {
    public static final String WRONG_USERNAME = "General";
    public static final String WRONG_PASSWORD = "General";
    private Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
    private Logic logic = new LogicManager(model, null);


    @Test
    public void findFalseUserPassFailure() {
        Assert.assertNotEquals(UserType.GENERAL, logic.findAccount(WRONG_USERNAME, WRONG_PASSWORD));
        Assert.assertNotEquals(UserType.ADMIN, logic.findAccount(WRONG_USERNAME, WRONG_PASSWORD));
        Assert.assertEquals(null, logic.findAccount(WRONG_USERNAME, WRONG_PASSWORD));
    }

    @Test
    public void findUserPassSuccess() {
        Assert.assertEquals(UserType.GENERAL, logic.findAccount(GENERAL_DAN_USERNAME, GENERAL_DAN_PASSWORD));
        Assert.assertEquals(UserType.ADMIN, logic.findAccount(ADMIN_TAN_USERNAME, ADMIN_TAN_PASSWORD));
    }

    @Test
    public void findFalseUserPassCombinationFailure() {
        Assert.assertEquals(null, logic.findAccount(ADMIN_TAN_USERNAME, GENERAL_DAN_PASSWORD));
        Assert.assertEquals(null, logic.findAccount(GENERAL_DAN_USERNAME, ADMIN_TAN_PASSWORD));
    }


}
