package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.UserType;
import seedu.address.logic.ViewCurrentCommand;
import seedu.address.logic.ViewNextCommand;
import seedu.address.logic.commands.AcceptSwapCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BlockDateCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConfirmScheduleCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DutySettingsCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PointsCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.commands.UnconfirmCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PersonnelDatabaseParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, UserType userType, String userName) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AcceptSwapCommand.COMMAND_WORD:
            return new AcceptSwapCommandParser().parse(arguments, userType, userName);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, userType, userName);

        case BlockDateCommand.COMMAND_WORD:
            return new BlockDateCommandParser().parse(arguments, userType, userName);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, userType, userName);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, userType, userName);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments, userType, userName);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommand();

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments, userType, userName);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        case ConfirmScheduleCommand.COMMAND_WORD:
            return new ConfirmScheduleCommand();

        case SwapCommand.COMMAND_WORD:
            return new SwapCommandParser().parse(arguments, userType, userName);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments, userType, userName);

        case UnconfirmCommand.COMMAND_WORD:
                return new UnconfirmCommand();

        case DutySettingsCommand.COMMAND_WORD:
            return new DutySettingsCommandParser().parse(arguments, userType, userName);

        case PointsCommand.COMMAND_WORD:
            return new PointsCommandParser().parse(arguments, userType, userName);

        case ViewCurrentCommand.COMMAND_WORD:
            return new ViewCurrentCommand();

        case ViewNextCommand.COMMAND_WORD:
                return new ViewNextCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
