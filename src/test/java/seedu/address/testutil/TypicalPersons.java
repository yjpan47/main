package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.UserType;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.duty.DutySettings;
import seedu.address.model.person.Person;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final String GENERAL_DAN_USERNAME = "S9687543K";
    public static final String GENERAL_DAN_PASSWORD = "Pass";
    public static final String ADMIN_TAN_USERNAME = "S9876543P";
    public static final String ADMIN_TAN_PASSWORD = "password";

    public static final String ALICE_NRIC = "S9673566K";

    public static final Person ALICE = new PersonBuilder().withNric("S9673566K").withCompany("Leopard")
            .withSection("1").withRank("CFC").withName("Alice Pauline")
            .withPhone("94351253").withTags("outstanding").withPassword("S9673566K")
            .withUserType(UserType.GENERAL).build();
    public static final Person BENSON = new PersonBuilder().withNric("S9478974B").withCompany("Hawk")
            .withSection("2").withRank("PTE").withName("Benson Meier")
            .withPhone("98765432").withTags("injured").withPassword("S9478974B")
            .withUserType(UserType.GENERAL).build();
    public static final Person CARL = new PersonBuilder().withNric("S9324325I").withCompany("Cougar")
            .withSection("3").withRank("REC").withName("Carl Kurz")
            .withPhone("95352563").withPassword("S9324325I").withUserType(UserType.GENERAL).build();
    public static final Person DANIEL = new PersonBuilder().withNric("T0034567L").withCompany("Falcon")
            .withSection("4").withRank("LCP").withName("Daniel Meier")
            .withPhone("87652533").withTags("friends").withPassword("T0034567L")
            .withUserType(UserType.GENERAL).build();
    public static final Person ELLE = new PersonBuilder().withNric("S9876567I").withCompany("Ninja")
            .withSection("2").withRank("REC").withName("Elle Meyer")
            .withPhone("9482224").withPassword("S9876567I").withUserType(UserType.GENERAL).build();
    public static final Person FIONA = new PersonBuilder().withNric("S9357825I").withCompany("2nd")
            .withSection("1").withRank("PTE").withName("Fiona Kunz")
            .withPhone("9482427").withPassword("S9357825I").withUserType(UserType.GENERAL).build();
    public static final Person GEORGE = new PersonBuilder().withNric("T0065782J").withCompany("1st")
            .withSection("2").withRank("CFC").withName("George Best")
            .withPhone("+942442").withPassword("T0065782J").withUserType(UserType.GENERAL).build();
    public static final Person GENERAL_DAN = new PersonBuilder().withNric(GENERAL_DAN_USERNAME).withCompany("3rd")
            .withSection("5").withRank("LTC").withName("Dan").withPhone("98765432")
            .withPassword(GENERAL_DAN_PASSWORD).withUserType(UserType.GENERAL).build();
    public static final Person ADMIN_TAN = new PersonBuilder().withNric(ADMIN_TAN_USERNAME).withCompany("7th")
            .withSection("4").withRank("CFC").withName("Tan").withPhone("12345678")
            .withPassword(ADMIN_TAN_PASSWORD).withUserType(UserType.ADMIN).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withNric("S1234567H").withCompany("Jaguar")
            .withSection("3").withRank("PTE").withName("Hoon Meier")
            .withPhone("8482424").buildReduced();
    public static final Person IDA = new PersonBuilder().withNric("S1234567I").withCompany("Mohawk")
            .withSection("3").withRank("LCP").withName("Ida Mueller")
            .withPhone("8482131").buildReduced();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withNric(VALID_NRIC_AMY).withCompany(VALID_COMPANY_AMY)
            .withSection(VALID_SECTION_AMY).withRank(VALID_RANK_AMY).withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_FRIEND).withPassword(VALID_PASSWORD_AMY)
            .withUserType(UserType.ADMIN).build();
    public static final Person BOB = new PersonBuilder().withNric(VALID_NRIC_BOB).withCompany(VALID_COMPANY_BOB)
            .withSection(VALID_SECTION_BOB).withRank(VALID_RANK_BOB).withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withPassword(VALID_PASSWORD_BOB).withUserType(UserType.GENERAL).build();

    // Since added in add command, password = nric, usertype = General

    public static final Person AMY_TO_ADD = new PersonBuilder().withNric(VALID_NRIC_AMY).withCompany(VALID_COMPANY_AMY)
            .withSection(VALID_SECTION_AMY).withRank(VALID_RANK_AMY).withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_FRIEND).withPassword(VALID_PASSWORD_AMY)
            .withUserType(UserType.ADMIN).buildReduced();

    public static final Person BOB_TO_ADD = new PersonBuilder().withNric(VALID_NRIC_BOB).withCompany(VALID_COMPANY_BOB)
            .withSection(VALID_SECTION_BOB).withRank(VALID_RANK_BOB).withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withPassword(VALID_PASSWORD_BOB).withUserType(UserType.GENERAL).buildReduced();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code PersonnelDatabase} with all the typical persons.
     */
    public static PersonnelDatabase getTypicalPersonnelDatabase() {
        PersonnelDatabase ab = new PersonnelDatabase();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code PersonnelDatabase} with all the typical persons and planned duties.
     */
    public static PersonnelDatabase getTypicalPersonnelDatabaseWithDuties() {
        PersonnelDatabase ab = new PersonnelDatabase();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        ab.getDutyCalendar().getNextMonth().schedule(ab.getPersonList(), new DutySettings(),
                ab.getDutyCalendar().getDutyStorage());
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                GENERAL_DAN, ADMIN_TAN));
    }

    public static String getTypicalPersonNric(int i) {
        Person targetedPerson = getTypicalPersons().get(i);
        return targetedPerson.getNric().value;
    }
}
