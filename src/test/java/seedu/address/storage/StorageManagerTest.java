package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.ReadOnlyPersonnelDatabase;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonPersonnelDatabaseStorage personnelDatabaseStorage = new JsonPersonnelDatabaseStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(personnelDatabaseStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void personnelDatabaseReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPersonnelDatabaseStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPersonnelDatabaseStorageTest} class.
         */
        PersonnelDatabase original = getTypicalPersonnelDatabase();
        storageManager.savePersonnelDatabase(original);
        ReadOnlyPersonnelDatabase retrieved = storageManager.readPersonnelDatabase().get();
        assertEquals(original, new PersonnelDatabase(retrieved));
    }

    @Test
    public void getPersonnelDatabaseFilePath() {
        assertNotNull(storageManager.getPersonnelDatabaseFilePath());
    }

}
