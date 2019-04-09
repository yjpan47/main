package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {
    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructorInvalidPasswordIhrowsIllegalArgumentException() {
        String invalidPassword = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidPassword));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid name
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("  ")); // spaces only

        // valid name
        assertTrue(Password.isValidPassword("peter jack")); // alphabets only
        assertTrue(Password.isValidPassword("12345")); // numbers only
        assertTrue(Password.isValidPassword("peter the 2nd")); // alphanumeric characters
        assertTrue(Password.isValidPassword("Capital Tan")); // with capital letters
        assertTrue(Password.isValidPassword("David Ro!!!ger Jackson $*Ray Jr 2nd")); // long passwords
        assertTrue(Password.isValidPassword("!(*# )(#")); // special characters only
    }
}
