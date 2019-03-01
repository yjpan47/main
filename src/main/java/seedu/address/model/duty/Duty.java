package seedu.address.model.duty;
import java.time.LocalDate;

import seedu.address.model.person.Person;

/**
 * Duty class
 */
public class Duty {
    private LocalDate date;
    private Type type;
    private Person person;

    /**
     * Constructs a duty
     */
    public Duty(LocalDate date, String type) {
        this.date = date;
        this.type = Type.valueOf(type.toUpperCase());
        this.person = null;
    }

    /**
     * Check whether this duty has been taken by a person
     */
    public boolean isTaken() {
        return (this.person != null);
    }

    @Override
    public String toString() {
        return "Duty on " + this.date.toString() + " (" + this.type.toString() + ")";
    }
}

/**
 * Constant indicating whether a duty is AM or PM
 */
enum Type {
    AM,
    PM
}

