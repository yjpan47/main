package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UiCommandInteraction;
import seedu.address.commons.core.UserType;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    //Clearance of the user
    private final UserType user;
    private final String userName;
    private Stage primaryStage;
    private Logic logic;
    //Whether the window is of current month or not
    private boolean isCurrentMonth;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private CalendarView calendarView;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox calendarViewPlaceholder;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane contactList;

    @FXML
    private TitledPane calendarPane;

    public MainWindow(Stage primaryStage, Logic logic, UserType user, String userName) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.user = user;
        this.userName = userName;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel(logic.getPersonnelDatabase().getRequestList());
        // Originally logic.selectedPersonProperty()
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.selectedPersonProperty(),
                logic::setSelectedPerson);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getPersonnelDatabaseFilePath(),
                logic.getPersonnelDatabase());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CalendarView calendarView = new CalendarView(logic.getCurrentDutyMonth());
        calendarViewPlaceholder.getChildren().add(calendarView.getRoot());
        isCurrentMonth = true;

        CommandBox commandBox = new CommandBox(this::executeCommand, user, userName, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Displays the personnel list accordion
     */
    @FXML
    public void handleList() {
        accordion.setExpandedPane(contactList);
    }

    /**
     * Displays the calendarView accordion
     */
    @FXML
    public void handleCalendar() {
        accordion.setExpandedPane(calendarPane);
    }
    /**
     * Refreshes current calendar view
     */
    public void refreshCalendarCurrent() {
        CalendarView calendarView = new CalendarView(logic.getCurrentDutyMonth());
        calendarViewPlaceholder.getChildren().clear();
        calendarViewPlaceholder.getChildren().add(calendarView.getRoot());
        isCurrentMonth = true;
    }
    /**
     * Refreshes next month calendar view
     */
    public void refreshCalendarNext() {
        CalendarView calendarView = new CalendarView(logic.getNextDutyMonth());
        calendarViewPlaceholder.getChildren().clear();
        calendarViewPlaceholder.getChildren().add(calendarView.getRoot());
        isCurrentMonth = false;
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String, UserType, String)
     */
    private CommandResult executeCommand(String commandText, UserType user, String userName)
            throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText, user, userName);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            UiCommandInteraction uiCommand = commandResult.getUiCommandInteraction();
            refreshCalendar(uiCommand);
            if (uiCommand != null) {
                handleUiCommand(uiCommand);
            }

            browserPanel.refreshRequestListDisplay();
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
    /**
     * Handles command inputs
     */
    public void handleUiCommand(UiCommandInteraction uiCommand) {
        switch (uiCommand) {
        case EXIT:
            handleExit();
            break;
        case HELP:
            handleHelp();
            break;
        case PEOPLE_LIST:
            handleList();
            break;
        case CALENDAR_CURRENT:
            handleCalendar();
            refreshCalendarCurrent();
            break;
        case CALENDAR_NEXT:
            handleCalendar();
            refreshCalendarNext();
            break;
        default: //do nothing
            break;
        }
    }
    /**
     * Refreshes calendar
     */
    public void refreshCalendar(UiCommandInteraction uiCommandInteraction) {
        if (uiCommandInteraction == UiCommandInteraction.CALENDAR_NEXT) {
            return;
        } else if (uiCommandInteraction == UiCommandInteraction.CALENDAR_CURRENT) {
            return;
        }
        if (isCurrentMonth) {
            refreshCalendarCurrent();
        } else {
            refreshCalendarNext();
        }
    }

}
