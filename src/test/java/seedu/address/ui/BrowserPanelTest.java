package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.scene.layout.Region;
import seedu.address.testutil.TypicalRequests;

public class BrowserPanelTest extends GuiUnitTest {
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before

    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(TypicalRequests.getTypicalRequest()));
        uiPartRule.setUiPart(browserPanel);
        Region region = browserPanel.getRoot();

        browserPanelHandle = new BrowserPanelHandle(getChildNode(region,
                BrowserPanelHandle.BROWSER_ID));
    }

    @Test
    public void display() throws Exception {
        guiRobot.pauseForHuman();
        assertEquals(browserPanel.requestsToStringForDisplay(), browserPanelHandle.getText());
    }
}
