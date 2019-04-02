package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import seedu.address.logic.commands.BlockDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class BlockDateCommandParser implements Parser<BlockDateCommand>{

    public BlockDateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockDateCommand.MESSAGE_USAGE));
        }

        List<Integer> blockedDates = new ArrayList<>();

        for (int i = 0; i < args.length(); i++) {
            blockedDates.add(Integer.parseInt(Character.toString(args.charAt(i))));
        }


        return new BlockDateCommand(blockedDates);
    }
}
