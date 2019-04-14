package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.request.Request;


/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final List<Request> requests;

    @FXML
    private TextArea textDisplay;

    public BrowserPanel(List<Request> requests) {
        super(FXML);
        this.requests = requests;
        String initText = requestsToStringForDisplay();
        setPanelText(initText);
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void setPanelText(String text) {
        requireNonNull(text);
        textDisplay.setText(text);
    }

    /**
     * Returns a string representing the list of requests to be displayed.
     */
    public String requestsToStringForDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Open Swap Requests:\n");
        int counter = 0;
        for (Request request : requests) {
            if (!request.isAccepterValid()) {
                counter++;
                sb.append(counter).append(". ").append(request.toString()).append(".\n");
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
