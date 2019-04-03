package seedu.address.logic.parser;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALLOCATED_DATE, PREFIX_REQUESTED_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALLOCATED_DATE, PREFIX_REQUESTED_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwapCommand.MESSAGE_USAGE));
        }

        LocalDate currentDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ALLOCATED_DATE).get());
        LocalDate requestedDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_REQUESTED_DATE).get());

        return new SwapCommand(currentDate, requestedDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
