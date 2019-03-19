package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.ReadOnlyPersonnelDatabase;

/**
 * Represents a storage for {@link PersonnelDatabase}.
 */
public interface PersonnelDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPersonnelDatabaseFilePath();

    /**
     * Returns PersonnelDatabase data as a {@link ReadOnlyPersonnelDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase() throws DataConversionException, IOException;

    /**
     * @see #getPersonnelDatabaseFilePath()
     */
    Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase(Path filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyPersonnelDatabase} to the storage.
     * @param personnelDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase) throws IOException;

    /**
     * @see #savePersonnelDatabase(ReadOnlyPersonnelDatabase)
     */
    void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase, Path filePath) throws IOException;

}
