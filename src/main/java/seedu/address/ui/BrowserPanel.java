package seedu.address.ui;

//import static java.util.Objects.requireNonNull;

//import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
//import javafx.scene.web.WebView;
//import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
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

    //@FXML
    //private WebView browser;

    @FXML
    private ListView<String> requestStrings;

    // Originally ObservableValue<Person> selectedPerson as argument
    public BrowserPanel(List<Request> requests) {
        super(FXML);
        List<String> listOfStrings = requests.stream().map(req -> req.getNric() + " requests a swap of their duty from "
                + req.getAllocatedDate().toString() + " to " + req.getRequestedDate().toString() + ".")
                .collect(Collectors.toList());
        ObservableList<String> observableStrings = FXCollections.observableArrayList(listOfStrings);
        requestStrings = new ListView<>();
        requestStrings.setItems(observableStrings);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load person page when selected person changes.
        //selectedPerson.addListener((observable, oldValue, newValue) -> {
        //    if (newValue == null) {
                //loadDefaultPage();
        //        return;
        //    }
            //loadPersonPage(newValue);
        //});

        //loadDefaultPage();
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

}
