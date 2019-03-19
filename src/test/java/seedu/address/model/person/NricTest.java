package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("^")); // only non-alphanumeric characters
        assertFalse(Nric.isValidNric("peter*")); // contains non-alphanumeric characters
        assertFalse(Nric.isValidNric("P0000000I")); // begins with disallowed letter
        assertFalse(Nric.isValidNric("p0000000i")); // begins or ends with a lower case letter
        assertFalse(Nric.isValidNric("S000000P")); // incorrect number of digits

        // valid nric
        assertTrue(Nric.isValidNric("S0123456A")); // starts with 'S'
        assertTrue(Nric.isValidNric("G0123456B")); // starts with 'G'
        assertTrue(Nric.isValidNric("T0123456C")); // starts with 'T'
        assertTrue(Nric.isValidNric("F0123456D")); // starts with 'F'
    }
}
