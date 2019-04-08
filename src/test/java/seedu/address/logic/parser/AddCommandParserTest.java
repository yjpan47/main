package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RANK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SECTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SECTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SECTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Section;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).buildReduced();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NRIC_DESC_BOB + COMPANY_DESC_BOB
                + SECTION_DESC_BOB + RANK_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, NRIC_DESC_AMY + NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB
                + RANK_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple companies - last company accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + COMPANY_DESC_AMY + COMPANY_DESC_BOB + SECTION_DESC_BOB
                + RANK_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple sections - last section accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_AMY + SECTION_DESC_BOB
                + RANK_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple ranks - last rank accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_AMY
                + RANK_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .buildReduced();
        assertParseSuccess(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().buildReduced();
        assertParseSuccess(parser, NRIC_DESC_AMY + COMPANY_DESC_AMY + SECTION_DESC_AMY + RANK_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing nric prefix
        assertParseFailure(parser, VALID_NRIC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                        + NAME_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NRIC_DESC_BOB + VALID_COMPANY_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                        + NAME_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing section prefix
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + VALID_SECTION_BOB + RANK_DESC_BOB
                        + NAME_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing rank prefix
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + VALID_RANK_BOB
                        + NAME_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing name prefix
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                        + VALID_NAME_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                        + NAME_DESC_BOB + VALID_PHONE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NRIC_BOB + VALID_COMPANY_BOB + VALID_SECTION_BOB + VALID_RANK_BOB
                + VALID_NAME_BOB + VALID_PHONE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid nric
        assertParseFailure(parser, INVALID_NRIC_DESC + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Nric.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_COMPANY_DESC + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Company.MESSAGE_CONSTRAINTS);

        // invalid section
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + INVALID_SECTION_DESC + RANK_DESC_BOB
                + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Section.MESSAGE_CONSTRAINTS);

        // invalid rank
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + INVALID_RANK_DESC
                + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Rank.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + INVALID_NAME_DESC + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_BOB + INVALID_PHONE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid inputs, first invalid input reported
        assertParseFailure(parser, INVALID_NRIC_DESC + COMPANY_DESC_BOB + SECTION_DESC_BOB + RANK_DESC_BOB
                + INVALID_NAME_DESC + PHONE_DESC_BOB, Nric.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NRIC_DESC_BOB + COMPANY_DESC_BOB + SECTION_DESC_BOB
                + RANK_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
