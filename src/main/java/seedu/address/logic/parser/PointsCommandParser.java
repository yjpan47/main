package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.UserType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PointsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new PointsCommnad object
 */
public class PointsCommandParser implements Parser<PointsCommand> {

    @Override
    public PointsCommand parse(String args, UserType userType, String userName) throws ParseException {
        try {
            if (args.equals("")) {
                return new PointsCommand();
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new PointsCommand(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PointsCommand.MESSAGE_USAGE), pe);
        }
    }
}
