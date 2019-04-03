package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.UserType;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DutySettingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.duty.DutySettings;

public class DutySettingsCommandParser implements Parser<DutySettingsCommand> {

    private static final Prefix PREFIX_DAY_OF_WEEK = new Prefix("w/");
    private static final Prefix PREFIX_POINTS = new Prefix("p/");
    private static final Prefix PREFIX_CAPACITY = new Prefix("m/");

    @Override
    public DutySettingsCommand parse(String args, UserType userType, String userName) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY_OF_WEEK, PREFIX_CAPACITY, PREFIX_POINTS);

        if (!argMultimap.getValue(PREFIX_DAY_OF_WEEK).isPresent()
                && !argMultimap.getValue(PREFIX_CAPACITY).isPresent()
                && !argMultimap.getValue(PREFIX_POINTS).isPresent()) {
            return new DutySettingsCommand();
        }

        try {
            int dayOfWeek = DateUtil.getDayOfWeekIndex(argMultimap.getValue(PREFIX_DAY_OF_WEEK).get());
            int capacity = Integer.parseInt(argMultimap.getValue(PREFIX_CAPACITY).get());
            int points = Integer.parseInt(argMultimap.getValue(PREFIX_POINTS).get());
            return new DutySettingsCommand(dayOfWeek,capacity, points);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DutySettingsCommand.MESSAGE_USAGE));
        }
    }

}
