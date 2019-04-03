package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.duty.DutySettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private DutySettings dutySettings = new DutySettings();
    private Path personnelDatabaseFilePath = Paths.get("data" , "personneldatabase.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setDutySettings(newUserPrefs.getDutySettings());
        setPersonnelDatabaseFilePath(newUserPrefs.getPersonnelDatabaseFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public DutySettings getDutySettings() {
        return dutySettings;
    }

    public void setDutySettings(DutySettings dutySettings) {
        requireNonNull(dutySettings);
        this.dutySettings = dutySettings;
    }

    public Path getPersonnelDatabaseFilePath() {
        return personnelDatabaseFilePath;
    }

    public void setPersonnelDatabaseFilePath(Path personnelDatabaseFilePath) {
        requireNonNull(personnelDatabaseFilePath);
        this.personnelDatabaseFilePath = personnelDatabaseFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && personnelDatabaseFilePath.equals(o.personnelDatabaseFilePath)
                && dutySettings.equals(o.dutySettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, personnelDatabaseFilePath, dutySettings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + personnelDatabaseFilePath);
        sb.append("\nDuty : " + dutySettings);
        return sb.toString();
    }

}
