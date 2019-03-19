package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PersonnelDatabase;
import seedu.address.model.person.Person;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPhone("94351253").withCompany("Alpha").withSection("1SIR").withRank("CPL")
            .withNric("S1234567A").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432").withCompany("Bravo").withSection("2SIR").withRank("3SG")
            .withNric("S1234567B").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withCompany("Echo").withSection("3SIR").withRank("2SG").withNric("S1234567C").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withCompany("Charlie").withSection("4SIR").withRank("3SG").withNric("S1234567D")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withCompany("Charlie").withSection("5SIR").withRank("3WO").withNric("S1234567E")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withCompany("Bravo").withSection("6SIR").withRank("LTA").withNric("S1234567F")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("+942442")
            .withCompany("Bravo").withSection("7SIR").withRank("2LT").withNric("S1234567G")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withCompany("Alpha").withSection("8SIR").withRank("LCP").withNric("S1234567H")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withCompany("Echo").withSection("9SIR").withRank("CPT").withNric("S1234567I")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withCompany(VALID_COMPANY_AMY).withSection(VALID_SECTION_AMY).withRank(VALID_RANK_AMY)
            .withNric(VALID_NRIC_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withCompany(VALID_COMPANY_BOB).withSection(VALID_SECTION_BOB).withRank(VALID_RANK_BOB)
            .withNric(VALID_NRIC_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code PersonnelDatabase} with all the typical persons.
     */
    public static PersonnelDatabase getTypicalAddressBook() {
        PersonnelDatabase ab = new PersonnelDatabase();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
