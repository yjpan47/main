package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's NRIC in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNRIC(String)}
 */
public class NRIC {

    public static final String MESSAGE_CONSTRAINTS = "NRIC is the IC number of the soldier";



    public static final String VALIDATION_REGEX = "[\\s\\d{7}\\s]";


    public final String value;

    /**
     * Constructs an {@code NRIC}.
     *
     * @param nric A valid nric.
     */
    public NRIC(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNRIC(nric), MESSAGE_CONSTRAINTS);
        value = nric;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidNRIC(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NRIC // instanceof handles nulls
                && value.equals(((NRIC) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}