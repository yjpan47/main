package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RANK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SECTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SECTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SECTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERTYPE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.GENERAL_USER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Company;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Section;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.TypicalPersons;

public class EditCommandParserGeneralTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_GENERAL);

    private EditCommandParser parser = new EditCommandParser();

    private String nricOfUser = TypicalPersons.getTypicalPersonNric(0);

    @Test
    public void parse_missingExtraParts_failure() {
        //  extra index specified for general command
        assertParseFailure(parser, "1" + VALID_PHONE_BOB, MESSAGE_INVALID_FORMAT,
                UserType.GENERAL, GENERAL_USER);

        // no field specified
        assertParseFailure(parser, " ", EditCommand.MESSAGE_NOT_EDITED, UserType.GENERAL,
                GENERAL_USER);

        // extra index and no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT, UserType.GENERAL,
                GENERAL_USER);

        // general accounts cannot edit username
        assertParseFailure(parser, VALID_NRIC_BOB, MESSAGE_INVALID_FORMAT, UserType.GENERAL, GENERAL_USER);

        // general accounts cannot edit userType
        assertParseFailure(parser, VALID_USERTYPE_BOB, MESSAGE_INVALID_FORMAT, UserType.GENERAL, GENERAL_USER);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_COMPANY_DESC, Company.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid company
        assertParseFailure(parser, INVALID_SECTION_DESC, Section.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid section
        assertParseFailure(parser, INVALID_RANK_DESC, Rank.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid rank
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid name
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid phone
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid tag
        assertParseFailure(parser, INVALID_PASSWORD_DESC, Password.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser); // invalid password

        // invalid name followed by valid phone
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser);
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser);
        assertParseFailure(parser, TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS,
                UserType.GENERAL, nricOfUser);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC,
                Name.MESSAGE_CONSTRAINTS, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = COMPANY_DESC_BOB + SECTION_DESC_BOB
                + RANK_DESC_BOB + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + NAME_DESC_AMY + TAG_DESC_FRIEND + PASSWORD_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
                .withSection(VALID_SECTION_BOB).withRank(VALID_RANK_BOB)
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withPassword(VALID_PASSWORD_BOB).build();
        EditCommand expectedCommand = new EditCommand(descriptor, nricOfUser);

        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB + NAME_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withName(VALID_NAME_BOB).build();
        EditCommand expectedCommand = new EditCommand(descriptor, nricOfUser);

        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        String userInput;
        EditPersonDescriptor descriptor;
        EditCommand expectedCommand;
        nricOfUser = TypicalPersons.getTypicalPersonNric(4);

        // company
        userInput = COMPANY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withCompany(VALID_COMPANY_AMY).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        //section
        userInput = SECTION_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withSection(VALID_SECTION_AMY).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        // rank
        userInput = RANK_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withRank(VALID_RANK_AMY).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        // name
        userInput = NAME_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        // phone
        userInput = PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        // tags
        userInput = TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        // password
        userInput = PASSWORD_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPassword(VALID_PASSWORD_AMY).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = PHONE_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(descriptor, nricOfUser);

        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);

        // other valid values specified
        userInput = SECTION_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withSection(VALID_SECTION_BOB)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(descriptor, nricOfUser);
        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_failure() {
        // no other valid values specified
        String userInput = PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Phone.MESSAGE_CONSTRAINTS, UserType.GENERAL, nricOfUser);

        // other valid values specified
        userInput = SECTION_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Phone.MESSAGE_CONSTRAINTS, UserType.GENERAL, nricOfUser);
    }

    @Test
    public void parse_resetTags_success() {
        nricOfUser = TypicalPersons.getTypicalPersonNric(2);
        String userInput = TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(descriptor, nricOfUser);

        assertParseSuccess(parser, userInput, expectedCommand, UserType.GENERAL, nricOfUser);
    }
}
