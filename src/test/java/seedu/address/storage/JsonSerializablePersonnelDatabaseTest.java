package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PersonnelDatabase;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializablePersonnelDatabaseTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializablePersonnelDatabaseTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsPersonnelDatabase.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonPersonnelDatabase.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonPersonnelDatabase.json");
    private static final Path TYPICAL_PERSON_FILE_WITH_DUTY = TEST_DATA_FOLDER
            .resolve("typicalPersonsWithDutyPersonnelDatabase.json");
    private static final Path TYPICAL_PERSON_FILE_WITH_BLOCKED_DATES = TEST_DATA_FOLDER
            .resolve("typicalPersonsWithBlockedDatesPersonnelDatabase.json");
    private static final Path INVALID_DUTYMONTH_INDEX_FILE = TEST_DATA_FOLDER
            .resolve("invalidDutyMonthIndexPersonnelDatabase.json");
    private static final Path INVALID_DUTYMONTH_YEAR_INDEX_FILE = TEST_DATA_FOLDER
            .resolve("invalidDutyYearIndexPersonnelDatabase.json");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializablePersonnelDatabase.class).get();
        PersonnelDatabase personnelDatabaseFromFile = dataFromFile.toModelType();
        PersonnelDatabase typicalPersonsPersonnelDatabase = TypicalPersons.getTypicalPersonnelDatabase();
        assertEquals(personnelDatabaseFromFile, typicalPersonsPersonnelDatabase);
    }

    @Test
    public void toModelType_typicalPersonsWithDutyFile_success() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSON_FILE_WITH_DUTY,
                JsonSerializablePersonnelDatabase.class).get();
        PersonnelDatabase personnelDatabaseFromFile = dataFromFile.toModelType();
        PersonnelDatabase typicalPersonsPersonnelDatabase = TypicalPersons.getTypicalPersonnelDatabase();
        assertEquals(personnelDatabaseFromFile, typicalPersonsPersonnelDatabase);
    }

    @Test
    public void toModelType_typicalPersonsWithBlockedDatesFile_success() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSON_FILE_WITH_BLOCKED_DATES,
                JsonSerializablePersonnelDatabase.class).get();
        PersonnelDatabase personnelDatabaseFromFile = dataFromFile.toModelType();
        PersonnelDatabase typicalPersonsPersonnelDatabase = TypicalPersons.getTypicalPersonnelDatabase();
        assertEquals(personnelDatabaseFromFile, typicalPersonsPersonnelDatabase);
    }

    @Test
    public void toModelType_invalidDutyMonthIndexFile_success() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(INVALID_DUTYMONTH_INDEX_FILE,
                JsonSerializablePersonnelDatabase.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidDutyYearIndexFile_success() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(INVALID_DUTYMONTH_YEAR_INDEX_FILE,
                JsonSerializablePersonnelDatabase.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializablePersonnelDatabase.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializablePersonnelDatabase dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializablePersonnelDatabase.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializablePersonnelDatabase.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
