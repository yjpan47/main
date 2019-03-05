package seedu.address.model.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a day with duty details
 */
public class DutyDate {

    private LocalDate date;
    private Day day;
    private List<Duty> duties;
    private int numOfDuties;

    public DutyDate(LocalDate date, Day day) {
        this.date = date;
        this.day = day;
        this.duties = new ArrayList<>();
        this.numOfDuties = 2;
        setDuties();
    }

    private void setDuties() {
        this.duties.add(new Duty(this.date, Meridiem.AM));
        this.duties.add(new Duty(this.date, Meridiem.AM));
    }
}
