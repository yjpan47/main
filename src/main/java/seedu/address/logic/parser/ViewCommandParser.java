package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_MASTERADMIN_ERROR;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args, UserType userType, String userName) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            if (userName.equals(UserType.DEFAULT_ADMIN_USERNAME)) {
                throw new ParseException(MESSAGE_MASTERADMIN_ERROR);
            }
        } else {
            userName = trimmedArgs;
        }

        return new ViewCommand(userName);
    }

}
