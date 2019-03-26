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

import seedu.address.testutil.PersonnelDatabaseBuilder;

public class VersionedPersonnelDatabaseTest {

    private final ReadOnlyPersonnelDatabase personnelDatabaseWithAmy =
            new PersonnelDatabaseBuilder().withPerson(AMY).build();
    private final ReadOnlyPersonnelDatabase personnelDatabaseWithBob =
            new PersonnelDatabaseBuilder().withPerson(BOB).build();
    private final ReadOnlyPersonnelDatabase personnelDatabaseWithCarl =
            new PersonnelDatabaseBuilder().withPerson(CARL).build();
    private final ReadOnlyPersonnelDatabase emptypersonnelDatabase =
            new PersonnelDatabaseBuilder().build();

    @Test
    public void commit_singlePersonnelDatabase_noStatesRemovedCurrentStateSaved() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(emptypersonnelDatabase);

        versionedPersonnelDatabase.commit();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Collections.singletonList(emptypersonnelDatabase),
                emptypersonnelDatabase,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplePersonnelDatabasePointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);

        versionedPersonnelDatabase.commit();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Arrays.asList(emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob),
                personnelDatabaseWithBob,
                Collections.emptyList());
    }

    @Test
    public void
        commit_multiplePersonnelDatabasePointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 2);

        versionedPersonnelDatabase.commit();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Collections.singletonList(emptypersonnelDatabase),
                emptypersonnelDatabase,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multiplePersonnelDatabasePointerAtEndOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);

        assertTrue(versionedPersonnelDatabase.canUndo());
    }

    @Test
    public void canUndo_multiplePersonnelDatabasePointerAtStartOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 1);

        assertTrue(versionedPersonnelDatabase.canUndo());
    }

    @Test
    public void canUndo_singlePersonnelDatabase_returnsFalse() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(emptypersonnelDatabase);

        assertFalse(versionedPersonnelDatabase.canUndo());
    }

    @Test
    public void canUndo_multiplePersonnelDatabasePointerAtStartOfStateList_returnsFalse() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 2);

        assertFalse(versionedPersonnelDatabase.canUndo());
    }

    @Test
    public void canRedo_multiplePersonnelDatabasePointerNotAtEndOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 1);

        assertTrue(versionedPersonnelDatabase.canRedo());
    }

    @Test
    public void canRedo_multiplePersonnelDatabasePointerAtStartOfStateList_returnsTrue() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 2);

        assertTrue(versionedPersonnelDatabase.canRedo());
    }

    @Test
    public void canRedo_singlePersonnelDatabase_returnsFalse() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(emptypersonnelDatabase);

        assertFalse(versionedPersonnelDatabase.canRedo());
    }

    @Test
    public void canRedo_multiplePersonnelDatabasePointerAtEndOfStateList_returnsFalse() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);

        assertFalse(versionedPersonnelDatabase.canRedo());
    }

    @Test
    public void undo_multiplePersonnelDatabasePointerAtEndOfStateList_success() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);

        versionedPersonnelDatabase.undo();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Collections.singletonList(emptypersonnelDatabase),
                personnelDatabaseWithAmy,
                Collections.singletonList(personnelDatabaseWithBob));
    }

    @Test
    public void undo_multiplePersonnelDatabasePointerNotAtStartOfStateList_success() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 1);

        versionedPersonnelDatabase.undo();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Collections.emptyList(),
                emptypersonnelDatabase,
                Arrays.asList(personnelDatabaseWithAmy, personnelDatabaseWithBob));
    }

    @Test
    public void undo_singlePersonnelDatabase_throwsNoUndoableStateException() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(emptypersonnelDatabase);

        assertThrows(VersionedPersonnelDatabase.NoUndoableStateException.class, versionedPersonnelDatabase::undo);
    }

    @Test
    public void undo_multiplePersonnelDatabasePointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 2);

        assertThrows(VersionedPersonnelDatabase.NoUndoableStateException.class, versionedPersonnelDatabase::undo);
    }

    @Test
    public void redo_multiplePersonnelDatabasePointerNotAtEndOfStateList_success() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 1);

        versionedPersonnelDatabase.redo();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Arrays.asList(emptypersonnelDatabase, personnelDatabaseWithAmy),
                personnelDatabaseWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multiplePersonnelDatabasePointerAtStartOfStateList_success() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 2);

        versionedPersonnelDatabase.redo();
        assertPersonnelDatabaseListStatus(versionedPersonnelDatabase,
                Collections.singletonList(emptypersonnelDatabase),
                personnelDatabaseWithAmy,
                Collections.singletonList(personnelDatabaseWithBob));
    }

    @Test
    public void redo_singlePersonnelDatabase_throwsNoRedoableStateException() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(emptypersonnelDatabase);

        assertThrows(VersionedPersonnelDatabase.NoRedoableStateException.class, versionedPersonnelDatabase::redo);
    }

    @Test
    public void redo_multiplePersonnelDatabasePointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedPersonnelDatabase versionedPersonnelDatabase = preparePersonnelDatabaseList(
                emptypersonnelDatabase, personnelDatabaseWithAmy, personnelDatabaseWithBob);

        assertThrows(VersionedPersonnelDatabase.NoRedoableStateException.class, versionedPersonnelDatabase::redo);
    }

    @Test
    public void equals() {
        VersionedPersonnelDatabase versionedPersonnelDatabase =
                preparePersonnelDatabaseList(personnelDatabaseWithAmy, personnelDatabaseWithBob);

        // same values -> returns true
        VersionedPersonnelDatabase copy =
                preparePersonnelDatabaseList(personnelDatabaseWithAmy, personnelDatabaseWithBob);
        assertTrue(versionedPersonnelDatabase.equals(copy));

        // same object -> returns true
        assertTrue(versionedPersonnelDatabase.equals(versionedPersonnelDatabase));

        // null -> returns false
        assertFalse(versionedPersonnelDatabase.equals(null));

        // different types -> returns false
        assertFalse(versionedPersonnelDatabase.equals(1));

        // different state list -> returns false
        VersionedPersonnelDatabase differentPersonnelDatabaseList =
                preparePersonnelDatabaseList(personnelDatabaseWithBob, personnelDatabaseWithCarl);
        assertFalse(versionedPersonnelDatabase.equals(differentPersonnelDatabaseList));

        // different current pointer index -> returns false
        VersionedPersonnelDatabase differentCurrentStatePointer = preparePersonnelDatabaseList(
                personnelDatabaseWithAmy, personnelDatabaseWithBob);
        shiftCurrentStatePointerLeftwards(versionedPersonnelDatabase, 1);
        assertFalse(versionedPersonnelDatabase.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedPersonnelDatabase} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedPersonnelDatabase#currentStatePointer}
     * is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedPersonnelDatabase#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertPersonnelDatabaseListStatus(VersionedPersonnelDatabase versionedPersonnelDatabase,
                                             List<ReadOnlyPersonnelDatabase> expectedStatesBeforePointer,
                                             ReadOnlyPersonnelDatabase expectedCurrentState,
                                             List<ReadOnlyPersonnelDatabase> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new PersonnelDatabase(versionedPersonnelDatabase), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedPersonnelDatabase.canUndo()) {
            versionedPersonnelDatabase.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyPersonnelDatabase expectedPersonnelDatabase : expectedStatesBeforePointer) {
            assertEquals(expectedPersonnelDatabase, new PersonnelDatabase(versionedPersonnelDatabase));
            versionedPersonnelDatabase.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyPersonnelDatabase expectedPersonnelDatabase : expectedStatesAfterPointer) {
            versionedPersonnelDatabase.redo();
            assertEquals(expectedPersonnelDatabase, new PersonnelDatabase(versionedPersonnelDatabase));
        }

        // check that there are no more states after pointer
        assertFalse(versionedPersonnelDatabase.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedPersonnelDatabase.undo());
    }

    /**
     * Creates and returns a {@code VersionedPersonnelDatabase} with the
     * {@code personnelDatabaseStates} added into it, and the
     * {@code VersionedPersonnelDatabase#currentStatePointer} at the end of list.
     */
    private VersionedPersonnelDatabase preparePersonnelDatabaseList(
            ReadOnlyPersonnelDatabase... personnelDatabaseStates) {
        assertFalse(personnelDatabaseStates.length == 0);

        VersionedPersonnelDatabase versionedPersonnelDatabase =
                new VersionedPersonnelDatabase(personnelDatabaseStates[0]);
        for (int i = 1; i < personnelDatabaseStates.length; i++) {
            versionedPersonnelDatabase.resetData(personnelDatabaseStates[i]);
            versionedPersonnelDatabase.commit();
        }

        return versionedPersonnelDatabase;
    }

    /**
     * Shifts the {@code versionedPersonnelDatabase#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedPersonnelDatabase versionedPersonnelDatabase, int count) {
        for (int i = 0; i < count; i++) {
            versionedPersonnelDatabase.undo();
        }
    }
}
