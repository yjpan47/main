package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.BlockDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class BlockDateCommandParser {

    public BlockDateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockDateCommand.MESSAGE_USAGE));
        }

        int[] blockedDates = new int[args.length()];

        for(int i = 0; i < args.length(); i++){
            blockedDates[i] = Integer.parseInt(Character.toString(args.charAt(i)));
        }

        return new BlockDateCommand(new BlockDateCommand(blockedDates));
}
