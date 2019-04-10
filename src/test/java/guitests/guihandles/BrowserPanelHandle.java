package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import org.w3c.dom.Text;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<TextArea> {

    public static final String BROWSER_ID = "#textDisplay";

    public BrowserPanelHandle(TextArea browserPanelNode) {
        super(browserPanelNode);
    }

    /**
     * Returns the text in the browser panel.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
