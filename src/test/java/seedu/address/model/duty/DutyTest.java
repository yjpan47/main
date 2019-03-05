package seedu.address.model.duty;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import seedu.address.model.calendar.Duty;

public class DutyTest {

    @Test
    public void constructor() {
        Duty duty = new Duty(LocalDate.now(), "AM");
        Assertions.assertEquals("Duty on " + LocalDate.now() + " (AM)", duty.toString());
    }
}
