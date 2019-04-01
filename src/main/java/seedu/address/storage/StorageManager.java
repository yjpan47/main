package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.*;

/**
 * Manages storage of PersonnelDatabase data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PersonnelDatabaseStorage personnelDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;
    private RequestManagerStorage requestManagerStorage;


    public StorageManager(PersonnelDatabaseStorage personnelDatabaseStorage, UserPrefsStorage userPrefsStorage,
                          RequestManagerStorage requestManagerStorage) {
        super();
        this.personnelDatabaseStorage = personnelDatabaseStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.requestManagerStorage = requestManagerStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ UserPrefs methods ==============================
    
    @Override
    public Path getRequestManagerFilePath() {
        return requestManagerStorage.getRequestManagerFilePath();
    }

    @Override
    public Optional<RequestManager> readRequestManager() throws DataConversionException, IOException {
        return requestManagerStorage.readRequestManager();
    }

    @Override
    public void saveRequestManager(ReadOnlyRequestManager requestManager) throws IOException {
        requestManagerStorage.saveRequestManager(requestManager);
    }
    
    // ================ PersonnelDatabase methods ==============================

    @Override
    public Path getPersonnelDatabaseFilePath() {
        return personnelDatabaseStorage.getPersonnelDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase() throws DataConversionException, IOException {
        return readPersonnelDatabase(personnelDatabaseStorage.getPersonnelDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return personnelDatabaseStorage.readPersonnelDatabase(filePath);
    }

    @Override
    public void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase) throws IOException {
        savePersonnelDatabase(personnelDatabase, personnelDatabaseStorage.getPersonnelDatabaseFilePath());
    }

    @Override
    public void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        personnelDatabaseStorage.savePersonnelDatabase(personnelDatabase, filePath);
    }

}
