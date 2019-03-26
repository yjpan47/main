package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.ReadOnlyPersonnelDatabase;

public class JsonPersonnelDatabaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonPersonnelDatabaseStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readPersonnelDatabase_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readPersonnelDatabase(null);
    }

    private java.util.Optional<ReadOnlyPersonnelDatabase> readPersonnelDatabase(String filePath) throws Exception {
        return new JsonPersonnelDatabaseStorage(Paths.get(filePath))
                .readPersonnelDatabase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPersonnelDatabase("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readPersonnelDatabase("notJsonFormatPersonnelDatabase.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readPersonnelDatabase_invalidPersonPersonnelDatabase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPersonnelDatabase("invalidPersonPersonnelDatabase.json");
    }

    @Test
    public void
        readPersonnelDatabase_invalidAndValidPersonPersonnelDatabase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPersonnelDatabase("invalidAndValidPersonPersonnelDatabase.json");
    }

    @Test
    public void readAndSavePersonnelDatabase_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempPersonnelDatabase.json");
        PersonnelDatabase original = getTypicalPersonnelDatabase();
        JsonPersonnelDatabaseStorage jsonPersonnelDatabaseStorage = new JsonPersonnelDatabaseStorage(filePath);

        // Save in new file and read back
        jsonPersonnelDatabaseStorage.savePersonnelDatabase(original, filePath);
        ReadOnlyPersonnelDatabase readBack = jsonPersonnelDatabaseStorage.readPersonnelDatabase(filePath).get();
        assertEquals(original, new PersonnelDatabase(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonPersonnelDatabaseStorage.savePersonnelDatabase(original, filePath);
        readBack = jsonPersonnelDatabaseStorage.readPersonnelDatabase(filePath).get();
        assertEquals(original, new PersonnelDatabase(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonPersonnelDatabaseStorage.savePersonnelDatabase(original); // file path not specified
        readBack = jsonPersonnelDatabaseStorage.readPersonnelDatabase().get(); // file path not specified
        assertEquals(original, new PersonnelDatabase(readBack));

    }

    @Test
    public void savePersonnelDatabase_nullPersonnelDatabase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonnelDatabase(null, "SomeFile.json");
    }

    /**
     * Saves {@code personnelDatabase} at the specified {@code filePath}.
     */
    private void savePersonnelDatabase(ReadOnlyPersonnelDatabase personnelDatabase, String filePath) {
        try {
            new JsonPersonnelDatabaseStorage(Paths.get(filePath))
                    .savePersonnelDatabase(personnelDatabase, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePersonnelDatabase_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonnelDatabase(new PersonnelDatabase(), null);
    }
}
