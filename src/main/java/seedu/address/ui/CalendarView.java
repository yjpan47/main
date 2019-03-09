package seedu.address.ui;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import seedu.address.commons.core.LogsCenter;

import javax.swing.*;

/**
 * The Calendar. Allows user to see dates with duties.
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarView.class);
    private Label[] dateLabels;
    private static final String[] dayArray = {"SUN", "MON ", "TUE", "WED", "THU", "FRI", "SAT"};
    private static final String[] monthArray =
            {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private static Date date;

    @FXML
    private ChoiceBox<String> monthSelection;

    @FXML
    private ChoiceBox<Integer> yearSelection;

    @FXML
    private GridPane gridPaneBottom;

    @FXML
    private VBox vBoxCalendar;

    @FXML
    private ScrollPane scrollPane;

    public CalendarView() {
        super(FXML);
        date = new Date();
        initializeChoices();
        initdateLabels();
        initializeDateDisplay();
        gridPaneBottom.prefHeightProperty().bind(vBoxCalendar.heightProperty().subtract(20));
        scrollPane.setFitToWidth(true);
    }

    /**
     * Outputs int array of {dd, mm, yyyy}.
     */
    public int[] getCurrentDayMonthYear() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        int month = Integer.valueOf(dateFormat.format(date));
        dateFormat = new SimpleDateFormat("dd");
        int day = Integer.valueOf(dateFormat.format(date));
        dateFormat = new SimpleDateFormat("yyyy");
        int year = Integer.valueOf(dateFormat.format(date));
        int[] result = {day, month, year};
        return result;
    }

    private void initdateLabels() {
        dateLabels = new Label[7];
        for (int i = 0; i <= 6; i++) {
            dateLabels[i] = new Label(dayArray[i]);
        }
        for (int i = 0; i <=6; i++) {
            gridPaneBottom.add(dateLabels[i], i, 0);
            dateLabels[i].maxWidthProperty().bind(gridPaneBottom.widthProperty().divide(7));
            dateLabels[i].setWrapText(true);
        }
    }

    /**
     * Fills up all choiceboxes with months and years based on the date application was run.
     */
    private void initializeChoices() {
        int[] dayMonthYear = getCurrentDayMonthYear();
        ObservableList<String> monthChoices = FXCollections.observableArrayList();
        for (int i = 0; i <= 6; i++) {
            monthChoices.add(monthArray[i]);
        }
        monthSelection.setItems(monthChoices);
        monthSelection.getSelectionModel().select(dayMonthYear[1] - 1);
        ObservableList<Integer> yearChoices = FXCollections.observableArrayList();
        for (int i = -4; i <= 6; i++) {
            yearChoices.add(dayMonthYear[2] + i);
        }
        yearSelection.setItems(yearChoices);
        yearSelection.getSelectionModel().select(4);
    }

    /**
     * Fills up calendar dates
     */
    private void initializeDateDisplay() {
        int[] currentDate = getCurrentDayMonthYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Label toAppend = dateLabels[dayOfWeek - 1];
        toAppend.setText(currentDate[0] + " " + monthArray[currentDate[1]] + "\n" + toAppend.getText());
    }
}

