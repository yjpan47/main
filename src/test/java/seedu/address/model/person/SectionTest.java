package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SectionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Section(null));
    }

    @Test
    public void constructor_invalidSection_throwsIllegalArgumentException() {
        String invalidSection = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Section(invalidSection));
    }

    @Test
    public void isValidSection() {
        // null section
        Assert.assertThrows(NullPointerException.class, () -> Section.isValidSection(null));

        // invalid section
        assertFalse(Section.isValidSection("")); // empty string
        assertFalse(Section.isValidSection(" ")); // spaces only
        // valid section
        assertTrue(Section.isValidSection("1"));
        assertTrue(Section.isValidSection("23"));
        assertTrue(Section.isValidSection("B"));
        assertTrue(Section.isValidSection("Alpha"));
    }
}
