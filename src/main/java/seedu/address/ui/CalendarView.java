package seedu.address.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

public class CalendarView extends UiPart<Region>{
    private static final String FXML = "CalendarView.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarView.class);

    @FXML
    private ChoiceBox<String> monthSelection;

    @FXML
    private ChoiceBox<Integer> yearSelection;

    @FXML
    private GridPane gridPaneDates;

    @FXML
    private GridPane gridPaneDays;

    @FXML
    private VBox VBoxCalendar;

    public CalendarView() {
        super(FXML);
        initializeChoices();
        gridPaneDates.prefWidthProperty().bind(VBoxCalendar.widthProperty().subtract(10));
        gridPaneDates.prefHeightProperty().bind(VBoxCalendar.heightProperty().subtract(20));
    }

    public int getCurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return Integer.valueOf(dateFormat.format(date));
    }

    public int getCurrentYear() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.valueOf(dateFormat.format(date));
    }

    public void initializeChoices() {
        monthSelection.getSelectionModel().select(getCurrentMonth() - 1);
        int currentYear = getCurrentYear();
        ObservableList<Integer> yearChoices = FXCollections.observableArrayList();
        for (int i = -4; i <= 6; i++) {
            yearChoices.add(currentYear + i);
        }
        yearSelection.setItems(yearChoices);
        yearSelection.getSelectionModel().select(4);
    }
}
