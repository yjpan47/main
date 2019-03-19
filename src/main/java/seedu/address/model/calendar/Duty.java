package seedu.address.model.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Duty class
 */
public class Duty {
    private LocalDate date;
    private Meridiem meridiem;
    private List<Person> dutyMen;
    private int numOfMen;
    private Person dutySergeant;
    private Person dutyOfficer;

    /**
     * Constructs a duty
     */
    public Duty(LocalDate date, Meridiem meridiem) {
        this.date = date;
        this.meridiem = meridiem;
        this.dutyMen = new ArrayList<>();
        this.numOfMen = 3;
        this.dutySergeant = null;
        this.dutyOfficer = null;
    }

    /**
     * Check whether this duty has been taken by a person
     */
    public boolean isReady() {
        return dutyMen.size() == this.numOfMen
                && this.dutySergeant != null
                && this.dutyOfficer != null;
    }

    /*
    Temporary method.
     */
    public void addDutyMen(List<Person> dutyMen) {
        this.dutyMen = dutyMen;
    }

    @Override
    public String toString() {
        return "Duty on " + this.date.toString() + " (" + this.meridiem.toString() + ")";
    }
}
