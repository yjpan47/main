package seedu.address.model.calendar;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Duty class
 */
public abstract class Duty {

    private int monthIndex;
    private int dayIndex;
    private int weekIndex;

    private List<Person> persons;

    private int numOfVacancies;
    private int pointsAwards;

    /**
     * Constructs a duty
     */
    public Duty(int monthIndex, int dayIndex, int weekIndex) {
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
        this.weekIndex = weekIndex;
        this.persons = new ArrayList<>();
    }

    public Duty(int monthIndex, int dayIndex, int weekIndex, List<Person> personList) {
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
        this.weekIndex = weekIndex;
        this.persons = new ArrayList<>(personList);
    }

    /**
     * Add a person to be assigned to this duty
     */
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

    /**
     * check whether this duty is already filled
     */
    boolean isFilled() {
        return this.numOfVacancies == 0;
    }

    void setNumOfVacancies(int numOfVacancies) {
        this.numOfVacancies = numOfVacancies;
    }

    void setPointsAwards(int pointsAwards) {
        this.pointsAwards = pointsAwards;
    }

    /**
     * Returns the index corresponding to the month
     */
    public int getMonthIndex() {
        return monthIndex;
    }

    /**
     * Returns the day in the month of the duty
     */
    public int getDayIndex() {
        return dayIndex;
    }

    /**
     * Get the index corresponding to which day of the week is this duty
     */
    public int getWeekIndex() {
        return weekIndex;
    }

    /**
     * Returns the list of Persons assigned to this duty
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Returns the number of people still needed for this duty
     */
    public int getNumOfVacancies() {
        return numOfVacancies;
    }

    /**
     * Points awarded to persons for  completing this duty
     */
    public int getPointsAwards() {
        return pointsAwards;
    }
}
