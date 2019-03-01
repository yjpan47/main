package seedu.address.model.Duty;
import seedu.address.model.person.Person;

import java.time.LocalDate;

public class Duty {
    public LocalDate date;
    public Type type;
    public Person person;

    public Duty(LocalDate date, String type) {
        this.date = date;
        this.type = Type.valueOf(type.toUpperCase());
        this.person = null;
    }

    public boolean isTaken() {
        return (this.person != null);
    }

    @Override
    public String toString() {
        return "Duty on " + this.date.toString() + " (" + this.type.toString() + ")";
    }

    public static void main(String[] args) {
        Duty d = new Duty(LocalDate.now(), "am");
        System.out.println(d);
    }

}

enum Type {
    AM,
    PM
}

