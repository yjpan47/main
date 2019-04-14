package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_AUTHORITY_PARSE;

import seedu.address.commons.core.UserType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ApproveSwapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ApproveSwapCommand object
 */
public class ApproveSwapCommandParser implements Parser<ApproveSwapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ApproveSwapCommand
     * and returns an ApproveSwapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApproveSwapCommand parse(String args, UserType userType, String userName) throws ParseException {
        if (userType == UserType.GENERAL) {
            throw new ParseException(MESSAGE_NO_AUTHORITY_PARSE);
        } else {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new ApproveSwapCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApproveSwapCommand.MESSAGE_USAGE), pe);
            }
        }
    }

}
