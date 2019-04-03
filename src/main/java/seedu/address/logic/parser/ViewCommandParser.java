package seedu.address.logic.parser;

import seedu.address.commons.core.UserType;
import static seedu.address.commons.core.Messages.MESSAGE_MASTERADMIN_ERROR;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewCommandParser implements Parser<ViewCommand> {

    public ViewCommand parse(String args, UserType userType, String userName) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            if (userName.equals("Admin")) {
                throw new ParseException(MESSAGE_MASTERADMIN_ERROR);
            }
        } else {
            userName = trimmedArgs;
        }

        return new ViewCommand(userName);
    }

}
