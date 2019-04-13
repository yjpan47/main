package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RANK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SECTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SECTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECTION_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY_TO_ADD;
import static seedu.address.testutil.TypicalPersons.BOB_TO_ADD;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UserType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.ui.NricUserPair;

public class AddCommandSystemTest extends PersonnelDatabaseSystemTest {

    @Test
    public void add() {
        setUp();
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Person toAdd = AMY_TO_ADD;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NRIC_DESC_AMY + " " + COMPANY_DESC_AMY + " "
                + SECTION_DESC_AMY + " " + RANK_DESC_AMY + " " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY
                + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except nric -> added */
        toAdd = new PersonBuilder(AMY_TO_ADD).withNric(VALID_NRIC_BOB).buildReduced();
        command = AddCommand.COMMAND_WORD + NRIC_DESC_BOB + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB_TO_ADD;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + NAME_DESC_BOB + COMPANY_DESC_BOB
                + NRIC_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different phone -> rejected */
        toAdd = new PersonBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different company -> rejected */
        toAdd = new PersonBuilder(HOON).withCompany(VALID_COMPANY_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different rank -> rejected */
        toAdd = new PersonBuilder(HOON).withRank(VALID_RANK_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different name -> rejected */
        toAdd = new PersonBuilder(HOON).withName(VALID_NAME_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);


        /* Case: add a duplicate person except with different section -> rejected */
        toAdd = new PersonBuilder(HOON).withSection(VALID_SECTION_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + PHONE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + NAME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + INVALID_NAME_DESC + PHONE_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + NAME_DESC_AMY + INVALID_PHONE_DESC;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        //To be added: invalid section, rank, NRIC

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);

        /* Case: invalid NRIC -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NRIC_DESC + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY;
        assertCommandFailure(command, Nric.MESSAGE_CONSTRAINTS);

        /* Case: invalid Rank -> rejected */
        command = AddCommand.COMMAND_WORD + NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + INVALID_RANK_DESC
                + NAME_DESC_AMY + PHONE_DESC_AMY;
        assertCommandFailure(command, Rank.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void addNonAdmin() {
        NricUserPair generalAccount = new NricUserPair(UserType.GENERAL, UserType.DEFAULT_ADMIN_USERNAME);
        setUp(generalAccount);
        /*Case: General User -> rejected */
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NRIC_DESC_AMY + " " + COMPANY_DESC_AMY + " "
                + SECTION_DESC_AMY + " " + RANK_DESC_AMY + " " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY
                + "   " + TAG_DESC_FRIEND + " ";
        assertCommandFailure(command, Messages.MESSAGE_NO_AUTHORITY);
        tearDown();
        NricUserPair nullAccount = new NricUserPair(null, UserType.DEFAULT_ADMIN_USERNAME);
        setUp(nullAccount);
        /*Case: General User -> rejected */
        command = "   " + AddCommand.COMMAND_WORD + "  " + NRIC_DESC_AMY + " " + COMPANY_DESC_AMY + " "
                + SECTION_DESC_AMY + " " + RANK_DESC_AMY + " " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY
                + "   " + TAG_DESC_FRIEND + " ";
        assertCommandFailure(command, Messages.MESSAGE_NO_USER);

    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PersonnelDatabaseSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PersonnelDatabaseSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PersonnelDatabaseSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PersonnelDatabaseSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
