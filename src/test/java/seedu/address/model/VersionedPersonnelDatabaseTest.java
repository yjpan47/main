package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;

public class VersionedPersonnelDatabaseTest {

    private final ReadOnlyPersonnelDatabase addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyPersonnelDatabase addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyPersonnelDatabase addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyPersonnelDatabase emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedPersonnelDatabase.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertThrows(VersionedPersonnelDatabase.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedPersonnelDatabase.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedPersonnelDatabase.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void equals() {
        VersionedPersonnelDatabase versionedAddressBook = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedPersonnelDatabase copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedAddressBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // different state list -> returns false
        VersionedPersonnelDatabase differentAddressBookList = prepareAddressBookList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedAddressBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedPersonnelDatabase differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedPersonnelDatabase versionedAddressBook,
                                             List<ReadOnlyPersonnelDatabase> expectedStatesBeforePointer,
                                             ReadOnlyPersonnelDatabase expectedCurrentState,
                                             List<ReadOnlyPersonnelDatabase> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new PersonnelDatabase(versionedAddressBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyPersonnelDatabase expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new PersonnelDatabase(versionedAddressBook));
            versionedAddressBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyPersonnelDatabase expectedAddressBook : expectedStatesAfterPointer) {
            versionedAddressBook.redo();
            assertEquals(expectedAddressBook, new PersonnelDatabase(versionedAddressBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAddressBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAddressBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedPersonnelDatabase} with the {@code addressBookStates} added into it, and the
     * {@code VersionedPersonnelDatabase#currentStatePointer} at the end of list.
     */
    private VersionedPersonnelDatabase prepareAddressBookList(ReadOnlyPersonnelDatabase... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedPersonnelDatabase versionedAddressBook = new VersionedPersonnelDatabase(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedAddressBook.resetData(addressBookStates[i]);
            versionedAddressBook.commit();
        }

        return versionedAddressBook;
    }

    /**
     * Shifts the {@code versionedAddressBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedPersonnelDatabase versionedAddressBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAddressBook.undo();
        }
    }
}
