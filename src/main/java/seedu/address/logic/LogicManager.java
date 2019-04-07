package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UserType;
import seedu.address.logic.commands.AdminCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GeneralCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonnelDatabaseParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPersonnelDatabase;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.duty.DutySettings;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final PersonnelDatabaseParser personnelDatabaseParser;
    private boolean personnelDatabaseModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        personnelDatabaseParser = new PersonnelDatabaseParser();

        // Set personnelDatabaseModified to true whenever the models' personnel database is modified.
        model.getPersonnelDatabase().addListener(observable -> personnelDatabaseModified = true);
    }

    @Override
    public CommandResult execute(String commandText, UserType user, String userName)
            throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        personnelDatabaseModified = false;

        CommandResult commandResult;
        try {
            if (user == UserType.ADMIN) {
                AdminCommand command = personnelDatabaseParser.parseCommand(commandText, user, userName);
                commandResult = command.executeAdmin(model, history);
            } else if (user == UserType.GENERAL) {
                GeneralCommand command = personnelDatabaseParser.parseCommand(commandText, user, userName);
                commandResult = command.executeGeneral(model, history);
            } else {
                throw new CommandException(Messages.MESSAGE_NO_USER);
            }
        } finally {
            history.add(commandText);
        }

        if (personnelDatabaseModified) {
            logger.info("Personnel database modified, saving to file.");
            try {
                storage.savePersonnelDatabase(model.getPersonnelDatabase());
                storage.saveUserPrefs(model.getUserPrefs());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPersonnelDatabase getPersonnelDatabase() {
        return model.getPersonnelDatabase();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    //@Override
    //public ObservableList<Person> getDutyForDates() {return model.getDutyForDates(); }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getPersonnelDatabaseFilePath() {
        return model.getPersonnelDatabaseFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setDutySettings(DutySettings dutySettings) {
        model.setDutySettings(dutySettings);
    }

    @Override
    public DutySettings getDutySettings() {
        return model.getDutySettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }

    @Override
    public UserType findAccount(String userName, String password) {
        return model.findAccount(userName, password);
    }

    @Override
    public DutyMonth getCurrentDutyMonth() {
        return model.getCurrentDutyMonth();
    }

    @Override
    public DutyMonth getNextDutyMonth() {
        return model.getNextDutyMonth();
    }
}
