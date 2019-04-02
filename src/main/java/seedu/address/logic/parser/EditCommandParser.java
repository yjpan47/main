package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_AUTHORITY_PARSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERTYPE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.UserType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, UserType userType, String userName) throws ParseException {
        if (userType == UserType.ADMIN) {
            return adminParse(args, userName);
        } else if (userType == UserType.GENERAL) {
            return generalParse(args, userName);
        } else {
            throw new ParseException(MESSAGE_NO_AUTHORITY_PARSE);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand (General)
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand generalParse(String args, String userName) throws ParseException {
        ArgumentMultimap argMultimap = initMultimapGeneral(args);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_GENERAL));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            editPersonDescriptor.setCompany(ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get()));
        }
        if (argMultimap.getValue(PREFIX_SECTION).isPresent()) {
            editPersonDescriptor.setSection(ParserUtil.parseSection(argMultimap.getValue(PREFIX_SECTION).get()));
        }
        if (argMultimap.getValue(PREFIX_RANK).isPresent()) {
            editPersonDescriptor.setRank(ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        if (argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            editPersonDescriptor.setPassword(ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get()));
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(editPersonDescriptor, userName);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand (Admin)
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand adminParse(String args, String userName) throws ParseException {
        ArgumentMultimap argMultimap = initMultimapAdmin(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE_ADMIN), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPersonDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            editPersonDescriptor.setCompany(ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get()));
        }
        if (argMultimap.getValue(PREFIX_SECTION).isPresent()) {
            editPersonDescriptor.setSection(ParserUtil.parseSection(argMultimap.getValue(PREFIX_SECTION).get()));
        }
        if (argMultimap.getValue(PREFIX_RANK).isPresent()) {
            editPersonDescriptor.setRank(ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        if (argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            editPersonDescriptor.setPassword(ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get()));
        }
        if (argMultimap.getValue(PREFIX_USERTYPE).isPresent()) {
            editPersonDescriptor.setUserType(ParserUtil.parseUserType(argMultimap.getValue(PREFIX_USERTYPE).get()));
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor, userName);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand (General)
     * and returns an ArgumentMultimap object.
     */
    private ArgumentMultimap initMultimapGeneral(String args) {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_SECTION, PREFIX_RANK,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG, PREFIX_PASSWORD);
        return argMultimap;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand (Admin)
     * and returns an ArgumentMultimap object.
     */
    private ArgumentMultimap initMultimapAdmin(String args) {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_COMPANY, PREFIX_SECTION, PREFIX_RANK,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG, PREFIX_PASSWORD, PREFIX_USERTYPE);
        return argMultimap;
    }

}
