package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.duty.Duty;
import seedu.address.model.duty.DutyMonth;
import seedu.address.model.person.Person;

/**
 * The Calendar. Allows user to see dates with duties.
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";
    private static final String[] dayArray = {"SUN", "MON ", "TUE", "WED", "THU", "FRI", "SAT"};
    private static final String[] monthArray =
        {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private Label[] dateLabels;
    private final DutyMonth dutyMonth;
    //private final Logger logger = LogsCenter.getLogger(CalendarView.class);

    @FXML
    private Label monthLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private GridPane gridPaneBottom;

    @FXML
    private VBox vBoxCalendar;

    @FXML
    private ScrollPane scrollPane;


    public CalendarView(DutyMonth dutyMonth) {
        super(FXML);
        this.dutyMonth = dutyMonth;
        initdateLabels();
        gridPaneBottom.prefHeightProperty().bind(vBoxCalendar.heightProperty().subtract(20));
        scrollPane.setFitToWidth(true);
        try {
            fillDutyCalendar();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }

    /**
     * Creates labels for each date.
     */
    private void initdateLabels() {
        dateLabels = new Label[7];
        for (int i = 0; i <= 6; i++) {
            dateLabels[i] = new Label(dayArray[i]);
        }
        for (int i = 0; i <= 6; i++) {
            gridPaneBottom.add(dateLabels[i], i, 0);
            dateLabels[i].maxWidthProperty().bind(gridPaneBottom.widthProperty().divide(7));
            dateLabels[i].setWrapText(true);
        }
        int monthIndex = dutyMonth.getMonthIndex();
        int year = dutyMonth.getYear();
        monthLabel.setText(monthArray[monthIndex - 1]);
        yearLabel.setText(Integer.toString(year));
    }

    /**
     * Fills up duty calendar
     */
    private void fillDutyCalendar() {
        int firstIndex = dutyMonth.getFirstDayOfWeekIndex();
        int numberDays = dutyMonth.getNumOfDays();
        for (int i = 1; i <= numberDays; i++) {
            int row = 1 + (firstIndex + i - 1) / 7;
            int column = (firstIndex + i - 1) % 7;
            Duty duty = dutyMonth.getScheduledDuties().get(i - 1);
            ObservableList<Person> obsListDuty = FXCollections.observableArrayList(duty.getPersons());
            ListView tempDuty = new ListView(obsListDuty);
            gridPaneBottom.add(tempDuty, column, row);
        }
    }
}

