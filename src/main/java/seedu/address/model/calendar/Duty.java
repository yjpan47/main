package seedu.address.model.calendar;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Duty class
 */
public abstract class Duty {

    int monthIndex;
    int dayIndex;
    int weekIndex;

    List<Person> persons;
    int numOfVacancies;
    int pointsAwards;

    /**
     * Constructs a duty
     */
    public Duty(int monthIndex, int dayIndex, int weekIndex) {
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
        this.weekIndex = weekIndex;
        this.weekIndex = weekIndex;
        this.persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        if (this.persons.contains(person)) {
            throw new InvalidParameterException(person + " is already assigned " + this);
        } else if (this.isFilled()) {
            throw new InvalidParameterException(this + " is already filled ");
        } else {
            this.persons.add(person);
            this.numOfVacancies--;
        }
    }

    public boolean isFilled() {
        return this.numOfVacancies == 0;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public int getNumOfVacancies() {
        return numOfVacancies;
    }

    public int getPointsAwards() {
        return pointsAwards;
    }
}
