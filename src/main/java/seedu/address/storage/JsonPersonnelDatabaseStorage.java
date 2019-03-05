package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyPersonnelDatabase;

/**
 * A class to access PersonnelDatabase data stored as a json file on the hard disk.
 */
public class JsonPersonnelDatabaseStorage implements PersonnelDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPersonnelDatabaseStorage.class);

    private Path filePath;

    public JsonPersonnelDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPersonnelDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase() throws DataConversionException {
        return readPersonnelDatabase(filePath);
    }

    /**
     * Similar to {@link #readPersonnelDatabase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePersonnelDatabase> jsonPersonnelDatabase = JsonUtil.readJsonFile(
                filePath, JsonSerializablePersonnelDatabase.class);
        if (!jsonPersonnelDatabase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPersonnelDatabase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase) throws IOException {
        savePersonnelDatabase(personnelDatabase, filePath);
    }

    /**
     * Similar to {@link #savePersonnelDatabase(ReadOnlyPersonnelDatabase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase, Path filePath) throws IOException {
        requireNonNull(personnelDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePersonnelDatabase(personnelDatabase), filePath);
    }

}
