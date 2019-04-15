package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.UserType;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.DutySettingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new DutySettingsCommand object
 */
public class DutySettingsCommandParser implements Parser<DutySettingsCommand> {

    private static final int MAX_CAPACITY = 10;
    private static final int MAX_POINTS = 100;
    private static final Prefix PREFIX_DAY_OF_WEEK = new Prefix("d/");
    private static final Prefix PREFIX_POINTS = new Prefix("p/");
    private static final Prefix PREFIX_CAPACITY = new Prefix("m/");

    /**
     * Parses the given {@code String} of arguments in the context of the DutySettingsCommand
     * and returns an DutySettingsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DutySettingsCommand parse(String args, UserType userType, String userName) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DAY_OF_WEEK, PREFIX_CAPACITY, PREFIX_POINTS);

        if (!argMultimap.getValue(PREFIX_DAY_OF_WEEK).isPresent()
                && !argMultimap.getValue(PREFIX_CAPACITY).isPresent()
                && !argMultimap.getValue(PREFIX_POINTS).isPresent()) {
            return new DutySettingsCommand();
        }

        try {
            int dayOfWeek = DateUtil.getDayOfWeekIndex(argMultimap.getValue(PREFIX_DAY_OF_WEEK).get());
            int capacity = Integer.parseInt(argMultimap.getValue(PREFIX_CAPACITY).get());
            int points = Integer.parseInt(argMultimap.getValue(PREFIX_POINTS).get());

            if (capacity <= 0 || capacity > MAX_CAPACITY || points <= 0 || points > MAX_POINTS) {
                throw new IllegalArgumentException();
            }

            return new DutySettingsCommand(dayOfWeek, capacity, points);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DutySettingsCommand.MESSAGE_USAGE));
        }
    }

}
