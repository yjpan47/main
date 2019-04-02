package seedu.address.model.duty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Person;

/**
 * Duty class
 */
public class Duty {

    //  Year
    private int year;

    //  Represent month (i.e. 1, 2, 3, ... , 31)
    private int monthIndex;

    //  Represent the day of the month (1, 2, 3 ... 31)
    private int dayIndex;

    //  Represent the day of the week (1, 2, 3 ... 7)
    private int dayOfWeekIndex;

    //  List of persons assigned to this duty
    private List<Person> persons;

    // Total number of persons needed for this duty
    private int capacity;

    // Number of people still needed
    private int vacancies;

    // Points awarded for doing this duty
    private int points;

    public Duty(int year, int monthIndex, int dayIndex, int dayOfWeekIndex, int capacity, int points) {
        if (DateUtil.isValidDate(year, monthIndex, dayIndex) && DateUtil.isValidDayOfWeek(dayOfWeekIndex)) {
            this.year = year;
            this.monthIndex = monthIndex;
            this.dayIndex = dayIndex;
            this.dayOfWeekIndex = dayOfWeekIndex;
            this.capacity = capacity;
            this.points = points;
            this.persons = new ArrayList<>();
        } else {
            throw new InvalidParameterException("Invalid Date");
        }
    }

    public void addPerson(Person person) {
        if (this.persons.contains(person)) {
            throw new InvalidParameterException(person + " is already assigned " + this);
        } else if (this.isFilled()) {
            throw new InvalidParameterException(this + " is already filled ");
        } else {
            this.persons.add(person);
        }
    }

    public boolean isFilled() {
        return this.persons.size() == this.capacity;
    }

    public int getMonthIndex() {
        return this.monthIndex;
    }

    public String getMonthString() {
        return DateUtil.getMonth(this.monthIndex);
    }

    public int getDayIndex() {
        return this.dayIndex;
    }

    public int getWeekIndex() {
        return this.dayOfWeekIndex;
    }

    public String getdayOfWeek() {
        return DateUtil.getDayOfWeek(this.dayOfWeekIndex);
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getVacancies() {
        return this.vacancies;
    }

    public int getPoints() {
        return this.points;
    }


}

