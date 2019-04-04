package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.BlockDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class BlockDateCommandParser implements Parser<BlockDateCommand>{

    public BlockDateCommand parse(String args, UserType userType, String userName) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockDateCommand.MESSAGE_USAGE));
        }
        String[] dates = trimmedArgs.split(" ");
        List<Integer> blockedDatesDuplicates = new ArrayList<>();

        for (String date : dates) {
            Integer dateAdded = Integer.parseInt(date);
            if (dateAdded > 31) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }
        Set<Integer> set = new HashSet<>(blockedDatesDuplicates);
        List<Integer> blockedDates = new ArrayList<>(set);

        return new BlockDateCommand(blockedDates, userName);
    }
}
