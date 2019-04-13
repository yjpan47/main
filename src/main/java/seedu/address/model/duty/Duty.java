package seedu.address.model.duty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Person;

/**
 * Duty class has indexes for current month day and duty class methods
 */
public class Duty {

    //  Year
    private int year;

    //  Represent month (i.e. 1, 2, 3, ... , 12)
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

    /**
     * Constructor for reconstruction of Duty object from json storage
     * @param year this year
     * @param monthIndex this monthIndex
     * @param dayIndex this dayIndex
     * @param dayOfWeekIndex this dayOfWeekIndex
     * @param capacity this capacity
     * @param vacancies this vacancies
     * @param points this points
     * @param personList this personList
     */
    public Duty(int year, int monthIndex, int dayIndex, int dayOfWeekIndex, int capacity,
                int vacancies, int points, List<Person> personList) {
        if (DateUtil.isValidDate(year, monthIndex, dayIndex) && DateUtil.isValidDayOfWeek(dayOfWeekIndex)) {
            this.year = year;
            this.monthIndex = monthIndex;
            this.dayIndex = dayIndex;
            this.dayOfWeekIndex = dayOfWeekIndex;
            this.capacity = capacity;
            this.vacancies = vacancies;
            this.points = points;
            this.persons = new ArrayList<>(personList);
        } else {
            throw new InvalidParameterException("Invalid Date");
        }
    }
    /**
     * Adds the input person into the duty
     */
    public void addPerson(Person person) {
        if (this.persons.contains(person)) {
            throw new InvalidParameterException(person + " is already assigned " + this);
        } else if (this.isFilled()) {
            throw new InvalidParameterException(this + " is already filled ");
        } else {
            this.persons.add(person);
        }
    }

    public boolean contains(Person person) {
        return this.persons.contains(person);
    }

    public void removePerson(Person remove) {
        Person target = null;
        for (Person person : this.getPersons()) {
            if (remove.getNric().toString().equals(person.getNric().toString())) {
                target = person;
            }
        }

        if (target != null) {
            this.persons.remove(target);
        }
    }

    public void replacePerson(Person remove, Person replace) {
        Person target = null;
        for (Person person : this.getPersons()) {
            if (remove.getNric().toString().equals(person.getNric().toString())) {
                target = person;
            }
        }
        if (target != null) {
            this.persons.remove(target);
            this.persons.add(replace);
        }
    }

    public boolean isFilled() {
        return this.persons.size() == this.capacity;
    }

    public int getYear() {
        return this.year;
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

    public int getDayOfWeekIndex() {
        return this.dayOfWeekIndex;
    }

    public String getdayOfWeek() {
        return DateUtil.getDayOfWeek(this.dayOfWeekIndex);
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    /**
     * Returns the string of all the persons in the duty minus the current person.
     * @param nric
     * @return
     */
    public String getPersonsString(String nric) {
        String personString = "";
        for (Person person : this.persons) {
            if (!person.getNric().toString().equals(nric)) {
                personString = personString + " " + person.getName().toString();
            }
        }
        return personString;
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

    @Override
    public String toString() {
        return String.format("Duty : %d %s %d | ", this.getDayIndex(), this.getMonthString(), this.getYear()) +
                String.format("%s | ", this.getdayOfWeek()) +
                String.format("%d points\n", this.getPoints());
    }

}

