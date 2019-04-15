package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_CONSTRAINTS_DATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BlockDateCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DutySettingsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PenalizeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RewardCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class PersonnelDatabaseParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PersonnelDatabaseParser parser = new PersonnelDatabaseParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person),
                UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON, UserType.DEFAULT_ADMIN_USERNAME), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof HelpCommand);
    }
    @Test
    public void parseCommand_dutySettings() throws Exception {
        Person person = new PersonBuilder().build();
        assertTrue(parser.parseCommand(DutySettingsCommand.COMMAND_WORD, UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof DutySettingsCommand);
        assertTrue(parser.parseCommand(DutySettingsCommand.COMMAND_WORD , UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof DutySettingsCommand);
        try {
            parser.parseCommand(DutySettingsCommand.COMMAND_WORD + " d/Sunday p/-1 m/-1",
                    UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DutySettingsCommand.MESSAGE_USAGE), pe.getMessage());
        }
        try {
            parser.parseCommand(DutySettingsCommand.COMMAND_WORD + " d/1 p/2 m/2",
                    UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DutySettingsCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
    }

    @Test
    public void parseCommand_swap() throws Exception {
        Person person = new PersonBuilder().build();
        assertTrue(parser.parseCommand(SwapCommand.COMMAND_WORD + " ad/120118 rd/130118",
                UserType.GENERAL, person.getNric().toString()) instanceof SwapCommand);
        try {
            parser.parseCommand(SwapCommand.COMMAND_WORD + " a/120118 rd/130118",
                    UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SwapCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
        try {
            parser.parseCommand(SwapCommand.COMMAND_WORD + " ",
                    UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SwapCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
        try {
            parser.parseCommand(SwapCommand.COMMAND_WORD + " ad/460118 rd/130118",
                    UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_CONSTRAINTS_DATE,
                    SwapCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }

    }

    @Test
    public void parseCommand_reward() throws Exception {
        Person person = new PersonBuilder().build();
        try {
            parser.parseCommand(RewardCommand.COMMAND_WORD, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RewardCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
        try {
            parser.parseCommand(RewardCommand.COMMAND_WORD + " i/1 p", UserType.ADMIN,
                    UserType.DEFAULT_ADMIN_USERNAME);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RewardCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
        try {
            parser.parseCommand(RewardCommand.COMMAND_WORD, UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RewardCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
    }

    @Test
    public void parseCommand_penalize() throws Exception {
        Person person = new PersonBuilder().build();
        try {
            parser.parseCommand(PenalizeCommand.COMMAND_WORD, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PenalizeCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
        try {
            parser.parseCommand(PenalizeCommand.COMMAND_WORD + " i/1 p", UserType.ADMIN,
                    UserType.DEFAULT_ADMIN_USERNAME);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PenalizeCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
        try {
            parser.parseCommand(PenalizeCommand.COMMAND_WORD, UserType.GENERAL, person.getNric().toString());
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PenalizeCommand.MESSAGE_USAGE)).getMessage(), pe.getMessage());
        }
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3", UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, UserType.ADMIN,
                UserType.DEFAULT_ADMIN_USERNAME) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        assertTrue(parser.parseCommand(ScheduleCommand.COMMAND_WORD, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof ScheduleCommand);
        assertTrue(parser.parseCommand("schedule", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof ScheduleCommand);
    }
    @Test
    public void parseCommand_blockDate() throws Exception {
        BlockDateCommand command = (BlockDateCommand) parser.parseCommand(BlockDateCommand.COMMAND_WORD
                        + " 2 4 5 6 9 10", UserType.ADMIN, "S9000000A");
        BlockDateCommand newCommand = new BlockDateCommand(List.of(2, 4, 5, 6, 9, 10), "S9000000A");
        assertEquals(command, newCommand);
    }
    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof UndoCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand("view S9673582K", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME)
                instanceof ViewCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", UserType.ADMIN, UserType.DEFAULT_ADMIN_USERNAME);
    }

}
