package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code PersonnelDatabase} that keeps track of its own history.
 */
public class VersionedPersonnelDatabase extends PersonnelDatabase {

    private final List<ReadOnlyPersonnelDatabase> personnelDatabaseStateList;
    private int currentStatePointer;

    public VersionedPersonnelDatabase(ReadOnlyPersonnelDatabase initialState) {
        super(initialState);

        personnelDatabaseStateList = new ArrayList<>();
        personnelDatabaseStateList.add(new PersonnelDatabase(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code PersonnelDatabase} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        personnelDatabaseStateList.add(new PersonnelDatabase(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        personnelDatabaseStateList.subList(currentStatePointer + 1, personnelDatabaseStateList.size()).clear();
    }

    /**
     * Restores the personnel database to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(personnelDatabaseStateList.get(currentStatePointer));
    }

    /**
     * Restores the personnel database to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(personnelDatabaseStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has personnel database states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has personnel database states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < personnelDatabaseStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedPersonnelDatabase)) {
            return false;
        }

        VersionedPersonnelDatabase otherVersionedPersonnelDatabase = (VersionedPersonnelDatabase) other;

        // state check
        return super.equals(otherVersionedPersonnelDatabase)
                && personnelDatabaseStateList.equals(otherVersionedPersonnelDatabase.personnelDatabaseStateList)
                && currentStatePointer == otherVersionedPersonnelDatabase.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of personnelDatabaseState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of personnelDatabaseState list, unable to redo.");
        }
    }
}
