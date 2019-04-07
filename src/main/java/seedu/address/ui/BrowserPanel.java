package seedu.address.ui;

import static java.util.Objects.requireNonNull;

//import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
//import java.util.stream.Collectors;

//import javafx.application.Platform;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
//import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
//import javafx.scene.web.WebView;
//import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.request.Request;


/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    //public static final URL DEFAULT_PAGE =
    //        requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    //public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final List<Request> requests;

    //@FXML
    //private WebView browser;

    @FXML
    private TextArea textDisplay;

    // Originally ObservableValue<Person> selectedPerson as argument
    public BrowserPanel(List<Request> requests) {
        super(FXML);
        this.requests = requests;
        String initText = requestsToStringForDisplay();
        /*<String> listOfStrings = requests.stream().map(req -> req.getRequesterNric() + " requests a swap of their duty from "
                + req.getAllocatedDate().toString() + " to " + req.getRequestedDate().toString() + ".")
                .collect(Collectors.toList());
        ObservableList<String> observableStrings = FXCollections.observableArrayList(listOfStrings);
        requestStrings = new ListView<>();
        requestStrings.setItems(observableStrings);*/

        // To prevent triggering events for typing inside the loaded Web page.
        //getRoot().setOnKeyPressed(Event::consume);

        // Load person page when selected person changes.
        //selectedPerson.addListener((observable, oldValue, newValue) -> {
        //if (newValue == null) {
        //    loadDefaultPage();
        //        return;
        //    }
        //loadPersonPage(newValue);
        //});

        //loadDefaultPage();
        setPanelText(initText);
    }

    //private void loadPersonPage(Person person) {
    //    loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    //}

    //public void loadPage(String url) {
    //    Platform.runLater(() -> browser.getEngine().load(url));
    //}

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    //private void loadDefaultPage() {
    //    loadPage(DEFAULT_PAGE.toExternalForm());
    //}
    private void setPanelText(String text) {
        requireNonNull(text);
        textDisplay.setText(text);
    }

    /**
     * Returns a string representing the list of requests to be displayed.
     */
    private String requestsToStringForDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Open Swap Requests:\n");
        int counter = 0;
        for (Request request : requests) {
            if (!request.isAccepterValid()) {
                counter++;
                sb.append(counter).append(". ").append(request.getRequesterNric()).append(" requests a swap from ")
                        .append(request.getAllocatedDate().toString()).append(" to ")
                        .append(request.getRequestedDate().toString() + ".\n");
            }
        }
        return sb.toString();
    }

    /**
     * Refreshes the text displayed to reflect current request list.
     */
    public void refreshRequestListDisplay() {
        setPanelText(requestsToStringForDisplay());
    }
}
