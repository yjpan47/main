package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_AUTHORITY_PARSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLOCATED_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUESTED_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwapCommand object
 */
public class SwapCommandParser implements Parser<SwapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwapCommand
     * and returns an SwapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwapCommand parse(String args, UserType userType, String userName) throws ParseException {
        if (!userName.equals(UserType.DEFAULT_ADMIN_USERNAME)) {
            return parseForPersonnelUser(args, userName);
        } else {
            throw new ParseException(MESSAGE_NO_AUTHORITY_PARSE);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SwapCommand for accounts associated with
     * a personnel and returns an SwapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwapCommand parseForPersonnelUser(String args, String userName) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALLOCATED_DATE, PREFIX_REQUESTED_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALLOCATED_DATE, PREFIX_REQUESTED_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwapCommand.MESSAGE_USAGE));
        }

        LocalDate currentDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ALLOCATED_DATE).get());
        LocalDate requestedDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_REQUESTED_DATE).get());

        return new SwapCommand(currentDate, requestedDate, userName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
