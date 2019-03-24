package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RankTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rank(null));
    }

    @Test
    public void constructor_invalidRank_throwsIllegalArgumentException() {
        String invalidRank = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rank(invalidRank));
    }

    @Test
    public void isValidRank() {
        // null rank
        Assert.assertThrows(NullPointerException.class, () -> Rank.isValidRank(null));

        // invalid rank
        assertFalse(Rank.isValidRank("")); // empty string
        assertFalse(Rank.isValidRank(" ")); // spaces only
        assertFalse(Rank.isValidRank("P")); // one character only
        assertFalse(Rank.isValidRank("3QQQ")); // more than 3 characters
        assertFalse(Rank.isValidRank("pte")); // contains lower-case

        // valid rank
        assertTrue(Rank.isValidRank("PTE"));
        assertTrue(Rank.isValidRank("REC"));
        assertTrue(Rank.isValidRank("3SG"));
        assertTrue(Rank.isValidRank("2LT"));
    }
}
