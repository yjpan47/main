package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.*;

/**
 * API of the Storage component
 */
public interface Storage extends PersonnelDatabaseStorage, UserPrefsStorage, RequestManagerStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPersonnelDatabaseFilePath();

    @Override
    Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase() throws DataConversionException, IOException;

    @Override
    void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase) throws IOException;

    @Override
    Optional<ReadOnlyRequestManager> readRequestManager() throws DataConversionException, IOException;

    @Override
    void saveRequestManager(ReadOnlyRequestManager requestManager) throws IOException;
}
