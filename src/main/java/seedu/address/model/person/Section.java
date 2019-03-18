package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's section in the duty planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidSection(String)}
 */
public class Section {

    public static final String MESSAGE_CONSTRAINTS = "Section can take any values, and it should not be blank";

    /*
     * The first character of the section must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Section}.
     *
     * @param section A valid section.
     */
    public Section(String section) {
        requireNonNull(section);
        checkArgument(isValidSection(section), MESSAGE_CONSTRAINTS);
        value = section;
    }

    /**
     * Returns true if a given string is a valid section.
     */
    public static boolean isValidSection(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Section // instanceof handles nulls
                && value.equals(((Section) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
