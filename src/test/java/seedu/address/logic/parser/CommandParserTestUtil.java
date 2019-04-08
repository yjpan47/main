package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    public static final String GENERAL_USER = "General";
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     * Uses UserType.Admin as useerType and "Admin" as username.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        assertParseSuccess(parser, userInput, expectedCommand, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand,
                                              UserType userType, String userName) {
        try {
            Command command = parser.parse(userInput, userType, userName);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     * Uses UserType.ADMIN as userType and "Admin" as username.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        assertParseFailure(parser, userInput, expectedMessage, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage,
                                              UserType userType, String userName) {
        try {
            parser.parse(userInput, userType, userName);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
