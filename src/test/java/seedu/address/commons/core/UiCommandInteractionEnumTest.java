package seedu.address.commons.core;

import org.junit.Assert;
import org.junit.Test;
public class UiCommandInteractionEnumTest {
    @Test
    public void testMonthString() {
        Assert.assertTrue(UiCommandInteraction.PEOPLE_LIST == UiCommandInteraction.PEOPLE_LIST);
        Assert.assertTrue(UiCommandInteraction.EXIT == UiCommandInteraction.EXIT);
        Assert.assertTrue(UiCommandInteraction.HELP == UiCommandInteraction.HELP);
        Assert.assertTrue(UiCommandInteraction.CALENDAR_CURRENT == UiCommandInteraction.CALENDAR_CURRENT);
        Assert.assertTrue(UiCommandInteraction.CALENDAR_NEXT == UiCommandInteraction.CALENDAR_NEXT);
        Assert.assertNotEquals(UiCommandInteraction.PEOPLE_LIST, null);
        Assert.assertNotEquals(UiCommandInteraction.HELP, null);
        Assert.assertNotEquals(UiCommandInteraction.EXIT, null);
        Assert.assertNotEquals(UiCommandInteraction.CALENDAR_CURRENT, null);
        Assert.assertNotEquals(UiCommandInteraction.CALENDAR_NEXT, null);
        Assert.assertNotEquals(UiCommandInteraction.HELP, UiCommandInteraction.CALENDAR_CURRENT);
        Assert.assertNotEquals(UiCommandInteraction.EXIT, UiCommandInteraction.HELP);
        Assert.assertNotEquals(UiCommandInteraction.HELP, UiCommandInteraction.PEOPLE_LIST);
    }
}
