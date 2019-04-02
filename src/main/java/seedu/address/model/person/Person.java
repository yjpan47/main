package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.UserType;
import seedu.address.model.tag.Tag;


/**
 * Represents a Person in the duty planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Nric nric;

    // Data fields
    private final Company company;
    private final Section section;
    private final Rank rank;
    private final Phone phone;
    private final Set<Tag> tags = new HashSet<>();

    // Account fields
    private final Password password;
    private final UserType userType;

    /**
     * Every field must be present and not null.
     */

    public Person(Nric nric, Company company, Section section, Rank rank, Name name,
                  Phone phone, Set<Tag> tags) {
        requireAllNonNull(nric, company, section, rank, name, phone, tags);
        this.nric = nric;
        this.company = company;
        this.section = section;
        this.rank = rank;
        this.name = name;
        this.phone = phone;
        this.tags.addAll(tags);
        this.password = new Password(nric.value);
        this.userType = UserType.GENERAL;
    }

    /**
     * Every field must be present and not null.
     */

    public Person(Nric nric, Company company, Section section, Rank rank, Name name,
                  Phone phone, Set<Tag> tags, Password password, UserType userType) {
        requireAllNonNull(nric, company, section, rank, name, phone, tags, password, userType);
        this.nric = nric;
        this.company = company;
        this.section = section;
        this.rank = rank;
        this.name = name;
        this.phone = phone;
        this.tags.addAll(tags);
        this.password = password;
        this.userType = userType;
    }

    public Nric getNric() {
        return nric;
    }

    public Company getCompany() {
        return company;
    }

    public Section getSection() {
        return section;
    }

    public Rank getRank() {
        return rank;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Password getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have the same NRIC.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson.equals(this)) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getNric().equals(this.getNric());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getNric().equals(getNric())
                && otherPerson.getCompany().equals(getCompany())
                && otherPerson.getSection().equals(getSection())
                && otherPerson.getRank().equals(getRank())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getPassword().equals(getPassword())
                && otherPerson.getUserType().equals(getUserType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own

        return Objects.hash(nric, company, section, rank, name, phone, tags, password, userType);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" NRIC: ")
                .append(getNric())
                .append(" Company: ")
                .append(getCompany())
                .append(" Section: ")
                .append(getSection())
                .append(" Rank: ")
                .append(getRank())
                .append(" Name: ")
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
