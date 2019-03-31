package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's password in the calendar planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {
    public static final String MESSAGE_CONSTRAINTS = "Password should contain at least one non-space character";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_CONSTRAINTS);
        value = password;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
