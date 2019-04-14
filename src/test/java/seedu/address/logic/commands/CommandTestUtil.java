package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERTYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NRIC_AMY = "S1234567A";
    public static final String VALID_NRIC_BOB = "S1234567B";
    public static final String VALID_COMPANY_AMY = "Alpha";
    public static final String VALID_COMPANY_BOB = "Bravo";
    public static final String VALID_SECTION_AMY = "1";
    public static final String VALID_SECTION_BOB = "2";
    public static final String VALID_RANK_AMY = "PTE";
    public static final String VALID_RANK_BOB = "LCP";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PASSWORD_AMY = "password";
    public static final String VALID_PASSWORD_BOB = "pass";
    public static final String VALID_USERTYPE_AMY = "A";
    public static final String VALID_USERTYPE_BOB = "G";

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String SECTION_DESC_AMY = " " + PREFIX_SECTION + VALID_SECTION_AMY;
    public static final String SECTION_DESC_BOB = " " + PREFIX_SECTION + VALID_SECTION_BOB;
    public static final String RANK_DESC_AMY = " " + PREFIX_RANK + VALID_RANK_AMY;
    public static final String RANK_DESC_BOB = " " + PREFIX_RANK + VALID_RANK_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String PASSWORD_DESC_AMY = " " + PREFIX_PASSWORD + VALID_PASSWORD_AMY;
    public static final String PASSWORD_DESC_BOB = " " + PREFIX_PASSWORD + VALID_PASSWORD_BOB;
    public static final String USERTYPE_DESC_AMY = " " + PREFIX_USERTYPE + VALID_USERTYPE_AMY;
    public static final String USERTYPE_DESC_BOB = " " + PREFIX_USERTYPE + VALID_USERTYPE_BOB;

    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "1238675A"; // missing first letter
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + " "; //must have non-space character
    public static final String INVALID_SECTION_DESC = " " + PREFIX_SECTION + " "; // must have non-space character
    public static final String INVALID_RANK_DESC = " " + PREFIX_RANK + "PTTT"; // only 3 letters allowed in rank
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + " "; //must have non-space character
    public static final String INVALID_USERTYPE_DESC = " " + PREFIX_USERTYPE + "H"; //Only have A or G

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_AMY)
                .withCompany(VALID_COMPANY_AMY).withSection(VALID_SECTION_AMY)
                .withRank(VALID_RANK_AMY).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withTags(VALID_TAG_FRIEND).withPassword(VALID_PASSWORD_AMY).withUserType(VALID_USERTYPE_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_BOB)
                .withCompany(VALID_COMPANY_BOB).withSection(VALID_SECTION_BOB)
                .withRank(VALID_RANK_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withPassword(VALID_PASSWORD_BOB)
                .withUserType(VALID_USERTYPE_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.executeAdmin(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     * This is for admin commands
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes (admin) the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the personnel database, filtered person list and selected person in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PersonnelDatabase expectedPersonnelDatabase = new PersonnelDatabase(actualModel.getPersonnelDatabase());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        Person expectedSelectedPerson = actualModel.getSelectedPerson();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.executeAdmin(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedPersonnelDatabase, actualModel.getPersonnelDatabase());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedSelectedPerson, actualModel.getSelectedPerson());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Executes (admin) the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccessGeneral(Command command, Model actualModel,
                                                   CommandHistory actualCommandHistory,
                                                   CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.executeGeneral(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     * This is for general commands
     */
    public static void assertCommandSuccessGeneral(Command command, Model actualModel,
                                                   CommandHistory actualCommandHistory, String expectedMessage,
                                                   Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccessGeneral(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes (admin) the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the personnel database, filtered person list and selected person in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailureGeneral(Command command, Model actualModel,
                                                   CommandHistory actualCommandHistory, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PersonnelDatabase expectedPersonnelDatabase = new PersonnelDatabase(actualModel.getPersonnelDatabase());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        Person expectedSelectedPerson = actualModel.getSelectedPerson();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.executeGeneral(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedPersonnelDatabase, actualModel.getPersonnelDatabase());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedSelectedPerson, actualModel.getSelectedPerson());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s personnel database.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s personnel database.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitPersonnelDatabase();
    }
}
